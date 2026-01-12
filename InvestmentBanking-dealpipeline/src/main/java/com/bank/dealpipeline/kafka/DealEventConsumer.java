package com.bank.dealpipeline.kafka;

import com.bank.dealpipeline.model.AuditEvent;
import com.bank.dealpipeline.repository.AuditEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DealEventConsumer {

    private final AuditEventRepository auditEventRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DealEventConsumer(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @KafkaListener(topics = "deal-events", groupId = "deal-consumer-group")
    public void consume(String message) {

        try {
            Map<String, String> eventMap =
                    objectMapper.readValue(message, Map.class);

            AuditEvent auditEvent = new AuditEvent(
                    eventMap.get("eventType"),
                    eventMap.get("dealId"),
                    eventMap.get("stage"),
                    LocalDateTime.parse(eventMap.get("timestamp"))
            );

            auditEventRepository.save(auditEvent);

            System.out.println("Audit event stored for deal: " + auditEvent.getDealId());

        } catch (Exception e) {
            System.out.println("Kafka consume failed: " + e.getMessage());
        }
    }
}
