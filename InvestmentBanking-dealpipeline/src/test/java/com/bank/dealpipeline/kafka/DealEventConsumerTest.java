package com.bank.dealpipeline.kafka;

import com.bank.dealpipeline.repository.AuditEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DealEventConsumerTest {

    @Mock
    private AuditEventRepository repository;

    @InjectMocks
    private DealEventConsumer consumer;

    @Test
    void consume() {
        String msg = """
        {"eventType":"CREATE","dealId":"D1","stage":"PROSPECT","timestamp":"2026-01-01T10:00:00"}
        """;

        consumer.consume(msg);

        verify(repository).save(any());
    }
}
