package com.successdca.open_school_project.model.entity;

import com.successdca.open_school_project.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
}
