package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.*;
import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealStage;
import com.bank.dealpipeline.service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @Mock
    private DealService dealService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private DealController dealController;

    @Test
    void createDeal() {
        DealRequest request = mock(DealRequest.class);
        Deal deal = new Deal();

        when(authentication.getName()).thenReturn("john");
        when(dealService.createDeal(request, "john")).thenReturn(deal);

        Deal result = dealController.createDeal(request, authentication);

        assertNotNull(result);
    }

    @Test
    void updateDealStage() {
        UpdateDealStageRequest request = mock(UpdateDealStageRequest.class);
        Deal deal = new Deal();

        when(request.getStage()).thenReturn(DealStage.CLOSED);
        when(dealService.updateDealStage("1", DealStage.CLOSED))
                .thenReturn(deal);

        Deal result = dealController.updateDealStage("1", request);

        assertNotNull(result);
    }

    @Test
    void addDealNote() {
        AddDealNoteRequest request = mock(AddDealNoteRequest.class);
        Deal deal = new Deal();

        when(authentication.getName()).thenReturn("john");
        when(request.getNote()).thenReturn("Important");
        when(dealService.addNoteToDeal("1", "Important", "john"))
                .thenReturn(deal);

        Deal result = dealController.addDealNote("1", request, authentication);

        assertNotNull(result);
    }

    @Test
    void updateDealValue() {
        UpdateDealValueRequest request = mock(UpdateDealValueRequest.class);
        Deal deal = new Deal();

        when(request.getDealValue()).thenReturn(1000.0);
        when(dealService.updateDealValue("1", 1000.0))
                .thenReturn(deal);

        Deal result = dealController.updateDealValue("1", request);

        assertNotNull(result);
    }

    @Test
    void getDeals_withoutStage() {
        when(dealService.getAllDeals())
                .thenReturn(Arrays.asList(new Deal()));

        List<Deal> deals = dealController.getDeals(null);

        assertEquals(1, deals.size());
    }

    @Test
    void getDeals_withStage() {
        when(dealService.getDealsByStage(DealStage.CLOSED))
                .thenReturn(Arrays.asList(new Deal()));

        List<Deal> deals = dealController.getDeals(DealStage.CLOSED);

        assertEquals(1, deals.size());
    }

    @Test
    void getDeal_userRole() {
        Deal deal = new Deal();

        doReturn(Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        )).when(authentication).getAuthorities();

        when(dealService.getDealById("1", "USER"))
                .thenReturn(deal);

        Deal result = dealController.getDeal("1", authentication);

        assertNotNull(result);
    }

    @Test
    void deleteDeal() {
        doNothing().when(dealService).deleteDeal("1");

        dealController.deleteDeal("1");

        verify(dealService).deleteDeal("1");
    }
}
