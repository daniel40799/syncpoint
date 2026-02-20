package com.project.syncpoint.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuditConsumer {

    @KafkaListener(topics = "inventory-audit-log", groupId = "syncpoint-audit-group")
    public void consumeAuditLog(String message) {
        // In a real app, you would save this to an 'audit_logs' table here
        log.info("Audit Consumer received message: {}", message);
    }
}