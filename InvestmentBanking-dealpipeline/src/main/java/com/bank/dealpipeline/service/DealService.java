package com.bank.dealpipeline.service;

import com.bank.dealpipeline.kafka.DealEventProducer;
import com.bank.dealpipeline.dto.DealRequest;
import com.bank.dealpipeline.dto.UpdateDealRequest;
import com.bank.dealpipeline.exception.ResourceNotFoundException;
import com.bank.dealpipeline.model.*;
import com.bank.dealpipeline.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final DealEventProducer dealEventProducer;

    public DealService(DealRepository dealRepository,
                       DealEventProducer dealEventProducer) {
        this.dealRepository = dealRepository;
        this.dealEventProducer = dealEventProducer;
    }

    // create deal
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

        Deal savedDeal = dealRepository.save(deal);

        dealEventProducer.publishDealEvent(
                "DEAL_CREATED",
                savedDeal.getId(),
                savedDeal.getStage().name()
        );

        return savedDeal;
    }

    // get all deals
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    // update deal stage
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

        dealEventProducer.publishDealEvent(
                "DEAL_STAGE_UPDATED",
                updatedDeal.getId(),
                updatedDeal.getStage().name()
        );

        return updatedDeal;
    }

    // add note to deal
    public Deal addNoteToDeal(String dealId, String noteText, String username) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.getNotes().add(new DealNote(noteText, username));
        deal.setUpdatedAt(LocalDateTime.now());

        deal.getActivities().add(
                new DealActivity("Note added", username)
        );

        return dealRepository.save(deal);
    }

    // update deal value
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

        Deal updatedDeal = dealRepository.save(deal);

        dealEventProducer.publishDealEvent(
                "DEAL_VALUE_UPDATED",
                updatedDeal.getId(),
                updatedDeal.getStage().name()
        );

        return updatedDeal;
    }

    // filter deals by stage
    public List<Deal> getDealsByStage(DealStage stage) {
        return dealRepository.findByStage(stage);
    }

    // update deal details
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

    // delete deal
    public void deleteDeal(String dealId) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        dealEventProducer.publishDealEvent(
                "DEAL_DELETED",
                deal.getId(),
                deal.getStage().name()
        );

        dealRepository.delete(deal);
    }

    // get deal by id with role based filtering
    public Deal getDealById(String dealId, String role) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        if ("USER".equals(role)) {
            deal.setDealValue(null);
            deal.getActivities().removeIf(
                    a -> a.getMessage().toLowerCase().contains("value")
            );
        }

        return deal;
    }
}
