package com.successdca.open_school_project.model.dto;

import com.successdca.open_school_project.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Long user_id;
    private TaskStatus status;
}
