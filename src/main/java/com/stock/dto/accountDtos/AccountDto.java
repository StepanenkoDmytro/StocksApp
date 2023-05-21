package com.stock.dto.accountDtos;

import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AccountDto {
    private String account_name;
    private String account_type;
    private BigDecimal balance;
    private List<AccountCoinDto> coins;

    public AccountDto(String account_name, String account_type, BigDecimal balance, List<AccountCoinDto> coins) {
        this.account_name = account_name;
        this.account_type = account_type;
        this.balance = balance;
        this.coins = coins;
    }

    public static AccountDto mapAccount(Account account) {
        return new AccountDto(
                account.getAccount_name(),
                account.getAccount_type().name(),
                account.getBalance(),
                mapAccountCoins(account.getCoins()));
    }

    private static List<AccountCoinDto> mapAccountCoins(List<AccountCoin> coins) {
        return coins.stream()
                .map(AccountCoinDto::mapAccountCoin)
                .collect(Collectors.toList());
    }
}
