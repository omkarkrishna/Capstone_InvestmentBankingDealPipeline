package com.bank.dealpipeline.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateDealRequest {

    @NotBlank
    private String dealType;

    @NotBlank
    private String sector;

    private String summary;

    // getters & setters
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

    public String getSummary() {
        return summary;
    }
}
