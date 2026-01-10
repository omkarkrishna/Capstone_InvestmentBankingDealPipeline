package com.bank.dealpipeline.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DealEventProducer {

    private static final String TOPIC = "deal-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DealEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishDealCreatedEvent(String dealId, String stage) {
        publishEvent("DEAL_CREATED", dealId, stage);
    }

    public void publishDealStageUpdatedEvent(String dealId, String stage) {
        publishEvent("DEAL_STAGE_UPDATED", dealId, stage);
    }

    private void publishEvent(String eventType, String dealId, String stage) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("eventType", eventType);
            event.put("dealId", dealId);
            event.put("stage", stage);
            event.put("timestamp", LocalDateTime.now().toString());

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, message);

        } catch (Exception e) {
            System.out.println("Failed to publish Kafka event: " + e.getMessage());
        }
    }
}
