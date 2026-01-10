package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.*;
import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealStage;
import com.bank.dealpipeline.service.DealService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    // Create Deal (USER / ADMIN)
    @PostMapping
    public Deal createDeal(@Valid @RequestBody DealRequest request, Authentication authentication) {
        String username = authentication.getName();
        return dealService.createDeal(request, username);
    }

    //Update Deals (USER / ADMIN)
    @PatchMapping("/{id}/stage")
    public Deal updateDealStage(@PathVariable String id, @Valid @RequestBody UpdateDealStageRequest request) {
        return dealService.updateDealStage(id, request.getStage());
    }

    //add deal Notes
    @PostMapping("/{id}/notes")
    public Deal addDealNote(@PathVariable String id, @Valid @RequestBody AddDealNoteRequest request, Authentication authentication) {
        String username = authentication.getName();
        return dealService.addNoteToDeal(id, request.getNote(), username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/value")
    public Deal updateDealValue(@PathVariable String id, @Valid @RequestBody UpdateDealValueRequest request) {
        return dealService.updateDealValue(id, request.getDealValue());
    }

    @GetMapping
    public List<Deal> getDeals(@RequestParam(required = false) DealStage stage) {
        if (stage != null) {
            return dealService.getDealsByStage(stage);
        }
        return dealService.getAllDeals();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Deal updateDeal(@PathVariable String id, @Valid @RequestBody UpdateDealRequest request) {
        return dealService.updateDeal(id, request);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDeal(@PathVariable String id) {
        dealService.deleteDeal(id);
    }

    @GetMapping("/{id}")
    public Deal getDeal(@PathVariable String id, Authentication authentication) {
        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return dealService.getDealById(id, role);
    }





}
