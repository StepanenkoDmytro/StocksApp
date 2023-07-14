package com.stock.repository.stock;

import com.stock.model.stock.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findBySymbol(String symbol);
}
