package com.stock.dto.accountDtos;

import com.stock.model.account.AccountCoin;
import lombok.Data;

@Data
public class AccountCoinDto {
    private String idCoin;
    private String name;

    public AccountCoinDto(String idCoin, String name) {
        this.idCoin = idCoin;
        this.name = name;
    }

    public static AccountCoinDto mapAccountCoin(AccountCoin coin){
        return new AccountCoinDto(coin.getId_coin(),coin.getName());
    }
}
