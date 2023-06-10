package com.stock.dto.stocks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompaniesForClient {
    List<CompanyDto> data;
    int totalPages;
    int totalItems;
    int currentPage;
}
