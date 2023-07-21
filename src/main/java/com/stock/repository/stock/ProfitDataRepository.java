package com.stock.repository.stock;

import com.stock.model.stock.analytic.ProfitData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitDataRepository extends JpaRepository<ProfitData, Long> {
    List<ProfitData> findAllByTicker(String ticker);
}
