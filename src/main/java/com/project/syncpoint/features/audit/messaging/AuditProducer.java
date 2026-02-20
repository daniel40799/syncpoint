package com.project.syncpoint.features.audit.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditProducer {

    // We use a String for simplicity here, but in production, we'd use a JSON DTO
    private static final String TOPIC = "inventory-audit-log";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendAuditLog(String message) {
        log.info("Publishing audit event to Kafka: {}", message);
        // This is non-blocking! The API keeps running while Kafka handles the delivery.
        this.kafkaTemplate.send(TOPIC, message);
    }
}
