package com.bank.dealpipeline.repository;

import com.bank.dealpipeline.model.Deal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class DealRepositoryTest {

    @Autowired
    private DealRepository dealRepository;

    @Test
    void saveAndFindDeal() {

        Deal deal = new Deal();
        deal.setClientName("Acme");

        dealRepository.save(deal);

        Optional<Deal> found =
                dealRepository.findById(deal.getId());

        assertTrue(found.isPresent());
        assertEquals("Acme", found.get().getClientName());
    }
}
