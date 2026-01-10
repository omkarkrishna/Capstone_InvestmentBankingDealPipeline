package com.bank.dealpipeline.service;

import com.bank.dealpipeline.dto.DealRequest;
import com.bank.dealpipeline.dto.UpdateDealRequest;
import com.bank.dealpipeline.model.*;
import com.bank.dealpipeline.repository.DealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealService dealService;

    @Test
    void create_deal_success() {
        DealRequest request = mock(DealRequest.class);

        when(request.getClientName()).thenReturn("Acme");
        when(request.getDealType()).thenReturn("M&A");
        when(request.getSector()).thenReturn("Tech");

        when(dealRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        Deal deal = dealService.createDeal(request, "john");

        assertEquals("Acme", deal.getClientName());
        assertEquals("john", deal.getCreatedBy());
        assertEquals(1, deal.getActivities().size());
    }

    @Test
    void update_deal_stage() {
        Deal deal = new Deal();
        deal.setStage(DealStage.PROSPECT);

        when(dealRepository.findById("1"))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any()))
                .thenReturn(deal);

        Deal updated = dealService.updateDealStage("1", DealStage.CLOSED);

        assertEquals(DealStage.CLOSED, updated.getStage());
    }

    @Test
    void add_note_to_deal() {
        Deal deal = new Deal();

        when(dealRepository.findById("1"))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any()))
                .thenReturn(deal);

        Deal updated = dealService.addNoteToDeal("1", "Important note", "john");

        assertEquals(1, updated.getNotes().size());
        assertEquals("Important note",
                updated.getNotes().get(0).getNote());
    }

    @Test
    void update_deal_value() {
        Deal deal = new Deal();
        deal.setDealValue(1000.0);

        when(dealRepository.findById("1"))
                .thenReturn(Optional.of(deal));
        when(dealRepository.save(any()))
                .thenReturn(deal);

        Deal updated = dealService.updateDealValue("1", 2000.0);

        assertEquals(2000.0, updated.getDealValue());
    }

    @Test
    void get_deal_by_id_user_role_hides_value() {
        Deal deal = new Deal();
        deal.setDealValue(5000.0);
        deal.getActivities().add(
                new DealActivity("Deal value updated", "ADMIN")
        );

        when(dealRepository.findById("1"))
                .thenReturn(Optional.of(deal));

        Deal result = dealService.getDealById("1", "USER");

        assertNull(result.getDealValue());
        assertTrue(result.getActivities().isEmpty());
    }
}
