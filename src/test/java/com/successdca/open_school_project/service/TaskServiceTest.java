package com.successdca.open_school_project.service;

import com.successdca.open_school_project.enums.TaskStatus;
import com.successdca.open_school_project.mapper.TaskMapper;
import com.successdca.open_school_project.model.dto.TaskDTO;
import com.successdca.open_school_project.model.entity.Task;
import com.successdca.open_school_project.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for TaskService")
class TaskServiceTest {

    private final TaskDTO taskDto = TaskDTO.builder()
            .id(1L)
            .title("Title task done")
            .status(TaskStatus.COMPLETED)
            .build();

    private final Task task = Task.builder()
            .id(1L)
            .title("Title task done")
            .status(TaskStatus.COMPLETED)
            .build();

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    @DisplayName("Test with result TaskDto for getTaskById")
    void getTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.taskToTaskDTO(task)).thenReturn(taskDto);

        TaskDTO taskDto = taskService.getTaskById(1L);

        assertNotNull(taskDto);
        assertEquals("Title task done", taskDto.getTitle());
        assertEquals(TaskStatus.COMPLETED, taskDto.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).taskToTaskDTO(any());
    }

    @Test
    @DisplayName("Test throw exception while try to get task by id")
    void getTaskByIdException() {
        assertThrows(RuntimeException.class,
                () -> taskService.getTaskById(1L));
    }

    @Test
    @DisplayName("Test to save task")
    void saveTask() {
        when(taskMapper.taskDTOToTask(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskDTO(task)).thenReturn(taskDto);

        TaskDTO result = taskService.createTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        assertEquals(taskDto.getStatus(), result.getStatus());
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).taskDTOToTask(taskDto);
        verify(taskMapper, times(1)).taskToTaskDTO(task);
    }

    @Test
    @DisplayName("Test update task when task exist")
    void updateTask() {
        TaskDTO updateTaskDto = TaskDTO.builder()
                .title("Title updated task done")
                .status(TaskStatus.COMPLETED)
                .build();
        Task updateTask = Task.builder()
                .title("Title updated task done")
                .build();

        when(taskMapper.taskDTOToTask(updateTaskDto)).thenReturn(updateTask);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        task.setTitle("Title updated task done");
        taskDto.setTitle("Title updated task done");
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskDTO(task)).thenReturn(taskDto);

        TaskDTO result = taskService.updateTask(1L, updateTaskDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(updateTaskDto.getTitle(), result.getTitle());
        verify(taskMapper, times(1)).taskDTOToTask(updateTaskDto);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).taskToTaskDTO(task);
    }

    @Test
    @DisplayName("Test not found task for update")
    void updateTaskException() {
        assertThrows(RuntimeException.class,
                () -> taskService.updateTask(1L, taskDto));
    }

    @Test
    @DisplayName("Test deleteTask")
    void deleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test not found task for update")
    void deleteTaskException() {
        assertThrows(RuntimeException.class,
                () -> taskService.deleteTask(1L));
    }

}