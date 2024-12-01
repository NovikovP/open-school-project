package com.successdca.open_school_project.service;

import com.successdca.open_school_project.aspect.annotation.CalculatingTheExecutionTimeOfMethod;
import com.successdca.open_school_project.aspect.annotation.LoggingAddedTaskInService;
import com.successdca.open_school_project.aspect.annotation.LoggingDeletedTask;
import com.successdca.open_school_project.model.Task;
import com.successdca.open_school_project.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @LoggingAddedTaskInService
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @CalculatingTheExecutionTimeOfMethod
    public Task updateTask(Long id, Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @LoggingDeletedTask
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}