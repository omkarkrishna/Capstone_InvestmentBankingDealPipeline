package com.bank.dealpipeline.dto;

import jakarta.validation.constraints.NotBlank;

public class AddDealNoteRequest {

    @NotBlank
    private String note;

    public String getNote() {
        return note;
    }
}
