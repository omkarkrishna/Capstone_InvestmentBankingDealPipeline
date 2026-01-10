package com.bank.dealpipeline.service;

import com.bank.dealpipeline.dto.DealAnalyticsResponse;
import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealStage;
import com.bank.dealpipeline.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final DealRepository dealRepository;

    public AnalyticsService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public DealAnalyticsResponse getAnalytics(boolean isAdmin) {

        List<Deal> deals = dealRepository.findAll();

        // Stage count
        Map<DealStage, Long> stageCount = new EnumMap<>(DealStage.class);

        for (DealStage stage : DealStage.values()) {
            stageCount.put(
                    stage,
                    deals.stream().filter(d -> d.getStage() == stage).count()
            );
        }


        long won = deals.stream()
                .filter(d -> d.getStage() == DealStage.CLOSED)
                .count();

        long lost = deals.stream()
                .filter(d -> d.getStage() == DealStage.LOST)
                .count();

        DealAnalyticsResponse response = new DealAnalyticsResponse();
        response.setStageCount(stageCount);
        response.setWonDeals(won);
        response.setLostDeals(lost);

        // ADMIN ONLY analytics
        if (isAdmin) {
            List<Double> values = deals.stream()
                    .map(Deal::getDealValue)
                    .filter(v -> v != null && v > 0)
                    .toList();

            double total = values.stream().mapToDouble(Double::doubleValue).sum();
            double avg = values.isEmpty() ? 0 : total / values.size();

            response.setTotalDealValue(total);
            response.setAverageDealValue(avg);
        }

        return response;
    }
}
