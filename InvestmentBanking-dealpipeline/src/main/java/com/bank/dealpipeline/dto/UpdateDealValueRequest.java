package com.bank.dealpipeline.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateDealValueRequest {

    @NotNull
    @Positive
    private Double dealValue;

    public Double getDealValue() {
        return dealValue;
    }
}
