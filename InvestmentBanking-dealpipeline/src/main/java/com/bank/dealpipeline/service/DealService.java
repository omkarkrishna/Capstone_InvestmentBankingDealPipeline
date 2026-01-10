package com.bank.dealpipeline.service;
import com.bank.dealpipeline.kafka.DealEventProducer;
import com.bank.dealpipeline.dto.DealRequest;
import com.bank.dealpipeline.dto.UpdateDealRequest;
import com.bank.dealpipeline.exception.ResourceNotFoundException;
import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealActivity;
import com.bank.dealpipeline.model.DealNote;
import com.bank.dealpipeline.model.DealStage;
import com.bank.dealpipeline.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final DealEventProducer dealEventProducer;


    public DealService(DealRepository dealRepository,DealEventProducer dealEventProducer) {
        this.dealRepository = dealRepository;
        this.dealEventProducer = dealEventProducer;
    }

    // CREATE DEAL
    public Deal createDeal(DealRequest request, String username) {

        Deal deal = new Deal();
        deal.setClientName(request.getClientName());
        deal.setDealType(request.getDealType());
        deal.setSector(request.getSector());
        deal.setSummary(request.getSummary());
        deal.setCreatedBy(username);

        deal.getActivities().add(
                new DealActivity("Deal created", username)
        );

        Deal savedDeal= dealRepository.save(deal);
        dealEventProducer.publishDealCreatedEvent(
                savedDeal.getId(),
                savedDeal.getStage().name()
        );

        return savedDeal;
    }

    // GET ALL DEALS
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    // UPDATE DEAL STAGE
    public Deal updateDealStage(String dealId, DealStage stage) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        DealStage oldStage = deal.getStage();

        deal.setStage(stage);
        deal.setUpdatedAt(LocalDateTime.now());

        deal.getActivities().add(
                new DealActivity(
                        "Stage changed from " + oldStage + " to " + stage,
                        "SYSTEM"
                )
        );

        Deal updatedDeal = dealRepository.save(deal);
        dealEventProducer.publishDealStageUpdatedEvent(
                updatedDeal.getId(),
                updatedDeal.getStage().name()
        );

        return updatedDeal;
    }

    //  ADD NOTE TO DEAL
    public Deal addNoteToDeal(String dealId, String noteText, String username) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        DealNote note = new DealNote(noteText, username);
        deal.getNotes().add(note);
        deal.setUpdatedAt(LocalDateTime.now());

        deal.getActivities().add(
                new DealActivity("Note added", username)
        );

        return dealRepository.save(deal);
    }

    // UPDATE DEAL VALUE (ADMIN)
    public Deal updateDealValue(String dealId, Double dealValue) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        Double oldValue = deal.getDealValue();

        deal.setDealValue(dealValue);
        deal.setUpdatedAt(LocalDateTime.now());

        deal.getActivities().add(
                new DealActivity(
                        "Deal value updated from " + oldValue + " to " + dealValue,
                        "ADMIN"
                )
        );

        return dealRepository.save(deal);
    }

    //  FILTER DEALS BY STAGE
    public List<Deal> getDealsByStage(DealStage stage) {
        return dealRepository.findByStage(stage);
    }

    // UPDATE DEAL DETAILS
    public Deal updateDeal(String dealId, UpdateDealRequest request) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setDealType(request.getDealType());
        deal.setSector(request.getSector());
        deal.setSummary(request.getSummary());
        deal.setUpdatedAt(LocalDateTime.now());

        deal.getActivities().add(
                new DealActivity("Deal details updated", "SYSTEM")
        );

        return dealRepository.save(deal);
    }

    // DELETE DEAL
    public void deleteDeal(String dealId) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.getActivities().add(
                new DealActivity("Deal deleted", "ADMIN")
        );

        dealRepository.delete(deal);
    }

    //  GET DEAL BY ID (ROLE BASED)
    public Deal getDealById(String dealId, String role) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        if ("USER".equals(role)) {

            // hide sensitive value
            deal.setDealValue(null);

            // hide value related activities
            deal.getActivities().removeIf(
                    a -> a.getMessage().toLowerCase().contains("value")
            );
        }

        return deal;
    }
}
