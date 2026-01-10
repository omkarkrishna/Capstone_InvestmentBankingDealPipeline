package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.DealAnalyticsResponse;
import com.bank.dealpipeline.service.AnalyticsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public DealAnalyticsResponse getAnalytics(Authentication auth) {

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return analyticsService.getAnalytics(isAdmin);
    }
}
