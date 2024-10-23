package com.stock.dto.accountDtos;

import com.stock.model.user.account.entities.AccountCoin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCoinDto {
    private String idCoin;
    private String name;
    private String symbol;
    private BigDecimal countCoin;
    private BigDecimal avgPrice;

    public AccountCoinDto(String idCoin, String name, String symbol, BigDecimal countCoin, BigDecimal avgPrice) {
        this.idCoin = idCoin;
        this.name = name;
        this.symbol = symbol;
        this.countCoin = countCoin;
        this.avgPrice = avgPrice;
    }

    public static AccountCoinDto mapAccountCoin(AccountCoin coin){
        return new AccountCoinDto(
                coin.getIdCoin(),
                coin.getName(),
                coin.getSymbol(),
                coin.getCountCoin(),
                coin.getAvgPrice());
    }
}
