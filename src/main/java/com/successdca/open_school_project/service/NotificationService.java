package com.successdca.open_school_project.service;

import com.successdca.open_school_project.aspect.annotation.LogAfterThrowing;
import com.successdca.open_school_project.model.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String email;

    @LogAfterThrowing
    public void sendNotification(TaskDTO taskDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(email);
        message.setSubject("Task Status Update");
        message.setText("Task with ID: " + taskDTO.getUser_id() + " has been updated. New status: " + taskDTO.getStatus());

        try {
            emailSender.send(message);
            log.info("Email send successfully for task ID: {}", taskDTO.getUser_id());
        } catch (Exception e) {
            log.error("Failed to send email for task ID: {}. Error: {}", taskDTO.getUser_id(), e.getMessage(), e);
        }
    }
}
