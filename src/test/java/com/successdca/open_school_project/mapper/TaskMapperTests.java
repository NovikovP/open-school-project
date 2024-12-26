package com.successdca.open_school_project.mapper;

import com.successdca.open_school_project.enums.TaskStatus;
import com.successdca.open_school_project.model.dto.TaskDTO;
import com.successdca.open_school_project.model.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for TaskMapper")
public class TaskMapperTests {

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

    private final TaskMapper taskMapper = new TaskMapperImpl();

    @Test
    @DisplayName("Test taskDtoToTask")
    void testTaskDtoToTask() {
        Task result = taskMapper.taskDTOToTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getId(), result.getId());
        assertEquals(taskDto.getTitle(), result.getTitle());
        assertEquals(taskDto.getStatus(), result.getStatus());
    }

    @Test
    @DisplayName("Test taskToTaskDto")
    void testTaskToTaskDto() {
        TaskDTO result = taskMapper.taskToTaskDTO(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getStatus(), result.getStatus());
    }
}