package com.bank.dealpipeline.repository;

import com.bank.dealpipeline.model.AuditEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class AuditEventRepositoryTest {

    @Autowired
    private AuditEventRepository repository;

    @Test
    void saveAuditEvent() {

        AuditEvent event =
                new AuditEvent("CREATE","D1","INIT", LocalDateTime.now());

        repository.save(event);

        assertEquals(1, repository.findAll().size());
    }
}
