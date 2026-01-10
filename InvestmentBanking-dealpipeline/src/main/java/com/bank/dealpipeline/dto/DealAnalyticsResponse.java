package com.bank.dealpipeline.dto;

import com.bank.dealpipeline.model.DealStage;
import java.util.Map;

public class DealAnalyticsResponse {

    // Count of deals per stage (PROSPECT, CLOSED, etc.)
    private Map<DealStage, Long> stageCount;

    // Total number of WON deals (CLOSED)
    private long wonDeals;

    // Total number of LOST deals
    private long lostDeals;

    // Sum of deal values (ADMIN sensitive but API can return)
    private double totalDealValue;

    // Average deal value
    private double averageDealValue;

    //getters & setters

    public Map<DealStage, Long> getStageCount() {
        return stageCount;
    }

    public void setStageCount(Map<DealStage, Long> stageCount) {
        this.stageCount = stageCount;
    }

    public long getWonDeals() {
        return wonDeals;
    }

    public void setWonDeals(long wonDeals) {
        this.wonDeals = wonDeals;
    }

    public long getLostDeals() {
        return lostDeals;
    }

    public void setLostDeals(long lostDeals) {
        this.lostDeals = lostDeals;
    }

    public double getTotalDealValue() {
        return totalDealValue;
    }

    public void setTotalDealValue(double totalDealValue) {
        this.totalDealValue = totalDealValue;
    }

    public double getAverageDealValue() {
        return averageDealValue;
    }

    public void setAverageDealValue(double averageDealValue) {
        this.averageDealValue = averageDealValue;
    }
}
