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
    private Long id;
    private String accountName;
    private String accountType;
    private BigDecimal balance;
    private List<AccountCoinDto> coins;

    public AccountDto(Long id ,String accountName, String accountType, BigDecimal balance, List<AccountCoinDto> coins) {
        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.coins = coins;
    }

    public static AccountDto mapAccount(Account account) {
        return new AccountDto(
                account.getId(),
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
