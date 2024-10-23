package com.stock.model.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompaniesForClient {
    private List<CompanyDto> data;
    private int totalPages;
    private long totalItems;
    private int currentPage;
}
