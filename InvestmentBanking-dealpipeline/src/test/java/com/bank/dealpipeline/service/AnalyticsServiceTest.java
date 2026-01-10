package com.bank.dealpipeline.service;

import com.bank.dealpipeline.dto.DealAnalyticsResponse;
import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealStage;
import com.bank.dealpipeline.repository.DealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void get_analytics_for_user() {
        Deal deal1 = new Deal();
        deal1.setStage(DealStage.PROSPECT);

        Deal deal2 = new Deal();
        deal2.setStage(DealStage.CLOSED);

        Deal deal3 = new Deal();
        deal3.setStage(DealStage.LOST);

        when(dealRepository.findAll())
                .thenReturn(List.of(deal1, deal2, deal3));

        DealAnalyticsResponse response =
                analyticsService.getAnalytics(false);

        assertEquals(1, response.getStageCount().get(DealStage.PROSPECT));
        assertEquals(1, response.getStageCount().get(DealStage.CLOSED));
        assertEquals(1, response.getStageCount().get(DealStage.LOST));

        assertEquals(1, response.getWonDeals());
        assertEquals(1, response.getLostDeals());

        // USER should not see admin analytics
        assertEquals(0.0, response.getTotalDealValue());
        assertEquals(0.0, response.getAverageDealValue());
    }

    @Test
    void get_analytics_for_admin() {
        Deal deal1 = new Deal();
        deal1.setStage(DealStage.CLOSED);
        deal1.setDealValue(1000.0);

        Deal deal2 = new Deal();
        deal2.setStage(DealStage.CLOSED);
        deal2.setDealValue(2000.0);

        Deal deal3 = new Deal();
        deal3.setStage(DealStage.PROSPECT);
        deal3.setDealValue(null); // should be ignored

        when(dealRepository.findAll())
                .thenReturn(List.of(deal1, deal2, deal3));

        DealAnalyticsResponse response =
                analyticsService.getAnalytics(true);

        assertEquals(2, response.getWonDeals());
        assertEquals(0, response.getLostDeals());

        assertEquals(3000.0, response.getTotalDealValue());
        assertEquals(1500.0, response.getAverageDealValue());
    }
}
