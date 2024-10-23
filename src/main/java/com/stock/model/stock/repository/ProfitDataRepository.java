package com.stock.model.stock.repository;

import com.stock.model.stock.analytic.entities.ProfitData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitDataRepository extends JpaRepository<ProfitData, Long> {
    List<ProfitData> findAllByTicker(String ticker);
}
