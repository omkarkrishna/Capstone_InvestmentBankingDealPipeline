package com.bank.dealpipeline.dto;

import jakarta.validation.constraints.NotBlank;

public class DealRequest {

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Deal type is required")
    private String dealType;

    @NotBlank(message = "Sector is required")
    private String sector;

    // OPTIONAL
    private String summary;

    public String getClientName() {
        return clientName;
    }

    public String getDealType() {
        return dealType;
    }

    public String getSector() {
        return sector;
    }

    public String getSummary() {
        return summary;
    }
}
