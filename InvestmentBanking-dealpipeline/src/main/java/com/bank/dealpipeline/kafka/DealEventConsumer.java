package com.bank.dealpipeline.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DealEventConsumer {

    @KafkaListener(topics = "deal-events", groupId = "deal-consumer-group")
    public void consume(String message) {
        System.out.println("Kafka Event Received: " + message);
    }
}
