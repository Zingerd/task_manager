package com.example.task_manager.conf.KafkaProducerConfig;

import com.example.task_manager.dto.TaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    MessageProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
    public void sendMessage(String topic, Object task) {
        try {
            String message = objectMapper.writeValueAsString(task);
            kafkaTemplate.send(topic, message);
        } catch (JsonProcessingException e) {
            // Обробити помилку серіалізації
            e.printStackTrace();
        }
    }

}