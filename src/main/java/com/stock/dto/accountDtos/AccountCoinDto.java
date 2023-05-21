package com.stock.dto.accountDtos;

import com.stock.model.account.AccountCoin;
import lombok.Data;

@Data
public class AccountCoinDto {
    private String id_coin;
    private String name;

    public AccountCoinDto(String id_coin, String name) {
        this.id_coin = id_coin;
        this.name = name;
    }

    public static AccountCoinDto mapAccountCoin(AccountCoin coin){
        return new AccountCoinDto(coin.getId_coin(),coin.getName());
    }
}
