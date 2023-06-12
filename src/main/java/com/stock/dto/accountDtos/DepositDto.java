package com.stock.dto.accountDtos;

import lombok.Data;

@Data
public class DepositDto {
    private Long accountID;
    private Long depositAmount;
}
