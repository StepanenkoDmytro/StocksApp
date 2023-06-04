package com.stock.dto.accountDtos;

import com.stock.model.account.AccountCoin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCoinDto {
    private String idCoin;
    private String name;
    private BigDecimal amountCOIN;

    private BigDecimal amountUSD;

    public AccountCoinDto(String idCoin, String name, BigDecimal amountCOIN, BigDecimal amountUSD) {
        this.idCoin = idCoin;
        this.name = name;
        this.amountCOIN = amountCOIN;
        this.amountUSD = amountUSD;
    }

    public static AccountCoinDto mapAccountCoin(AccountCoin coin){
        return new AccountCoinDto(
                coin.getIdCoin(),
                coin.getName(),
                coin.getAmountCOIN(),
                coin.getAmountUSD());
    }
}
