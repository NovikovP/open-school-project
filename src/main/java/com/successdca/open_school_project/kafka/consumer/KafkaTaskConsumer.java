package com.successdca.open_school_project.kafka.consumer;

import com.successdca.open_school_project.aspect.annotation.LogAfterThrowing;
import com.successdca.open_school_project.model.dto.TaskDTO;
import com.successdca.open_school_project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTaskConsumer {

    private final NotificationService notificationService;

    @LogAfterThrowing
    @KafkaListener(topics = "task-updates", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listenTaskStatusUpdate(List<TaskDTO> tasksDTO) {
        if (tasksDTO.isEmpty()) {
            return;
        }

        for (TaskDTO temp : tasksDTO) {
            log.info("Received task update for task ID: {}", temp.getId());
            try {
                notificationService.sendNotification(temp);
                log.info("Successfully consume task and send notification for task ID: {}", temp.getId());
            } catch (Exception ex) {
                log.error("Failed to send in kafka for task ID: {}. Error: {}", temp.getUser_id(), ex.getMessage(), ex);
            }
        }
    }
}