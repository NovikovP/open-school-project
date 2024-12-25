package com.successdca.open_school_project.kafka.producer;

import com.successdca.open_school_project.model.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class KafkaTaskProducer {

    private final KafkaTemplate<String, TaskDTO> kafkaTemplate;

    @Value("${spring.kafka.topic.task-updates}")
    private String taskStatusTopic;

    public void sendTaskStatusUpdate(TaskDTO taskDTO) {
        try {
            kafkaTemplate.send(taskStatusTopic, taskDTO);
            log.info("Send in kafka successfully for task ID: {}", taskDTO.getUser_id());
        } catch (Exception ex) {
            log.error("Failed to send in kafka for task ID: {}. Error: {}", taskDTO.getUser_id(), ex.getMessage(), ex);
        }
    }
}
