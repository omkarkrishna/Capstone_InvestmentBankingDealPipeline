package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.DealAnalyticsResponse;
import com.bank.dealpipeline.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsControllerTest {

    @Mock
    private AnalyticsService analyticsService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AnalyticsController analyticsController;

    @Test
    void getAnalytics_admin() {


        doReturn(Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        )).when(authentication).getAuthorities();

        when(analyticsService.getAnalytics(true))
                .thenReturn(new DealAnalyticsResponse());

        DealAnalyticsResponse response =
                analyticsController.getAnalytics(authentication);

        assertNotNull(response);
    }
}
