package com.bank.dealpipeline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_events")
public class AuditEvent {

    @Id
    private String id;

    private String eventType;
    private String dealId;
    private String stage;
    private LocalDateTime timestamp;

    public AuditEvent() {}

    public AuditEvent(String eventType, String dealId, String stage, LocalDateTime timestamp) {
        this.eventType = eventType;
        this.dealId = dealId;
        this.stage = stage;
        this.timestamp = timestamp;
    }

    // getters & setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDealId() { return dealId; }
    public void setDealId(String dealId) { this.dealId = dealId; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
