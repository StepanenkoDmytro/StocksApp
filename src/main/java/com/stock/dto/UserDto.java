package com.stock.dto;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.model.account.Account;
import com.stock.model.user.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private String username;
    private String email;
    private Long imageID;
    private List<AccountDto> accounts;

    public UserDto(Long imageID, String username, String email, List<AccountDto> accounts) {
        this.imageID = imageID;
        this.username = username;
        this.email = email;
        this.accounts = accounts;
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getImage().getId(),
                user.getUsername(),
                user.getEmail(),
                mapAccountsListToDto(user.getAccounts())
                );
    }

    private static List<AccountDto> mapAccountsListToDto(List<Account> accounts){
        return accounts.stream()
                .map(AccountDto::mapAccount)
                .collect(Collectors.toList());
    }
}
