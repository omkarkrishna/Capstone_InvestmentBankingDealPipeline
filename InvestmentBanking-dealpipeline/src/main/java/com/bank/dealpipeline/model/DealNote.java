package com.bank.dealpipeline.model;

import java.time.LocalDateTime;

public class DealNote {

    private String note;
    private String addedBy;
    private LocalDateTime addedAt;

    public DealNote(String note, String addedBy) {
        this.note = note;
        this.addedBy = addedBy;
        this.addedAt = LocalDateTime.now();
    }

    public String getNote() {
        return note;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}
