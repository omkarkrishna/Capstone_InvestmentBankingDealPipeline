package com.bank.dealpipeline.model;

import java.time.LocalDateTime;

public class DealActivity {

    private String message;
    private String performedBy;
    private LocalDateTime timestamp;

    public DealActivity(String message, String performedBy) {
        this.message = message;
        this.performedBy = performedBy;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
