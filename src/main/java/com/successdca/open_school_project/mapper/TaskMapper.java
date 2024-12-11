package com.successdca.open_school_project.mapper;

import com.successdca.open_school_project.model.dto.TaskDTO;
import com.successdca.open_school_project.model.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOToTask(TaskDTO taskDTO);
}
