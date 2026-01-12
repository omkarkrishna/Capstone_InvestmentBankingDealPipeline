package com.bank.dealpipeline.repository;

import com.bank.dealpipeline.model.AuditEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditEventRepository extends MongoRepository<AuditEvent, String> {
}
