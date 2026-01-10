package com.bank.dealpipeline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "deals")
public class Deal {

    @Id
    private String id;

    private String clientName;
    private String dealType;
    private String sector;

    private DealStage stage;

    private String createdBy; // username
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String summary;

    public Deal() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.stage = DealStage.PROSPECT;
    }
    // deal notes
    private List<DealNote> notes = new ArrayList<>();

    public List<DealNote> getNotes() {
        return notes;
    }
    // deal value
    private Double dealValue; // sensitive (ADMIN only)

    public Double getDealValue() {
        return dealValue;
    }

    public void setDealValue(Double dealValue) {
        this.dealValue = dealValue;
    }


    // getters and setters
    public String getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public DealStage getStage() {
        return stage;
    }

    public void setStage(DealStage stage) {
        this.stage = stage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // deal activity
    private List<DealActivity> activities = new ArrayList<>();

    public List<DealActivity> getActivities() {
        return activities;
    }

    //deal summary
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
