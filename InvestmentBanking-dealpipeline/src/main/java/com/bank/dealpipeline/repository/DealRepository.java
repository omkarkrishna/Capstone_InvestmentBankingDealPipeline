package com.bank.dealpipeline.repository;

import com.bank.dealpipeline.model.Deal;
import com.bank.dealpipeline.model.DealStage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DealRepository extends MongoRepository<Deal, String> {

    List<Deal> findByStage(DealStage stage);
}
