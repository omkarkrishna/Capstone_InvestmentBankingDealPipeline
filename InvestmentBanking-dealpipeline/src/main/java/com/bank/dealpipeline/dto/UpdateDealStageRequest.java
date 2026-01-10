package com.bank.dealpipeline.dto;

import com.bank.dealpipeline.model.DealStage;
import jakarta.validation.constraints.NotNull;

public class UpdateDealStageRequest {

    @NotNull
    private DealStage stage;

    public DealStage getStage() {
        return stage;
    }
}
