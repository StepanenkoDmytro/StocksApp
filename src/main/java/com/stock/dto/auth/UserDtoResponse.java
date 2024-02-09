package com.stock.dto.auth;

import com.stock.dto.accountDtos.UserDto;
import com.stock.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDtoResponse {
    private UserDto user;
    private String token;

    public static UserDtoResponse mapUserDtoResponse(User user, String token) {
        return new UserDtoResponse(
                UserDto.mapUserToUserDto(user),
                token
        );
    }
}
