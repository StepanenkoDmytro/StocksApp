package com.stock.dto.accountDtos;

import com.stock.model.user.account.entities.Account;
import com.stock.model.user.account.entities.AccountCoin;
import com.stock.model.user.account.entities.AccountStock;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountName;
    private String accountType;
    private BigDecimal balance;
    private BigDecimal fixedProfit;
    private List<AccountCoinDto> coins;
    private List<AccountStockDto> stocks;

    public AccountDto(Long id, String accountName, String accountType, BigDecimal balance, BigDecimal fixedProfit, List<AccountCoinDto> coins, List<AccountStockDto> stocks) {
        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.coins = coins;
        this.stocks = stocks;
        this.fixedProfit = fixedProfit;
    }

    public static AccountDto mapAccount(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getAccountType().name(),
                account.getBalance(),
                account.getFixedProfit(),
                mapAccountCoins(account.getCoins()),
                mapAccountStocks(account.getStocks()));
    }

    private static List<AccountCoinDto> mapAccountCoins(List<AccountCoin> coins) {
        if (coins == null) {
            return new ArrayList<>();
        }
        return coins.stream()
                .map(AccountCoinDto::mapAccountCoin)
                .toList();
    }

    private static List<AccountStockDto> mapAccountStocks(List<AccountStock> stocks) {
        if (stocks == null) {
            return new ArrayList<>();
        }
        return stocks.stream()
                .map(AccountStockDto::mapToDto)
                .toList();
    }
}
