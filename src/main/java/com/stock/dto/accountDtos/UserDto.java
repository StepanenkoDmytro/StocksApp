package com.stock.dto.accountDtos;

import com.stock.model.account.Account;
import com.stock.model.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    private String email;
    private List<AccountDto> accounts;
    private BigDecimal totalBalance;

    public UserDto(Long id, String email, List<AccountDto> accounts) {
        this.id = id;
        this.email = email;
        this.accounts = accounts;
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                mapAccountsListToDto(user.getAccounts())
                );
    }

    public static List<AccountDto> mapAccountsListToDto(List<Account> accounts){
        return accounts.stream()
                .map(AccountDto::mapAccount)
                .toList();
    }
}
