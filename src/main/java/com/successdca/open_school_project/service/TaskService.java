package com.successdca.open_school_project.service;

import com.successdca.open_school_project.aspect.annotation.LogAfterReturning;
import com.successdca.open_school_project.aspect.annotation.LogAfterThrowing;
import com.successdca.open_school_project.aspect.annotation.LogAround;
import com.successdca.open_school_project.aspect.annotation.LogBefore;
import com.successdca.open_school_project.kafka.producer.KafkaTaskProducer;
import com.successdca.open_school_project.mapper.TaskMapper;
import com.successdca.open_school_project.model.dto.TaskDTO;
import com.successdca.open_school_project.model.entity.Task;
import com.successdca.open_school_project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final KafkaTaskProducer kafkaTaskProducer;

    @LogBefore
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }

    @LogAfterReturning
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with " + id + "not found"));
        return taskMapper.taskToTaskDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with ID " + id + " not found"));

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());
        Task updatedTask = taskRepository.save(existingTask);

        kafkaTaskProducer.sendTaskStatusUpdate(taskMapper.taskToTaskDTO(updatedTask));

        return taskMapper.taskToTaskDTO(updatedTask);
    }

    @LogAfterThrowing
    public ResponseEntity<Void> deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with " + id + "not found"));
        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }

    @LogAround
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskToTaskDTO)
                .toList();
    }
}