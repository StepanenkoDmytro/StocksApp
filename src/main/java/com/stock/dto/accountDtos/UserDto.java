package com.stock.dto.accountDtos;

import com.stock.dto.portolioDTOs.PortfolioDTO;
import com.stock.model.portfolio.Portfolio;
import com.stock.model.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private List<PortfolioDTO> portfolio;
    private BigDecimal totalBalance;

    public UserDto(Long id, String email, List<PortfolioDTO> portfolio) {
        this.id = id;
        this.email = email;
        this.portfolio = portfolio;
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                mapAccountsListToDto(user.getPortfolios())
                );
    }

    public static List<PortfolioDTO> mapAccountsListToDto(List<Portfolio> portfolios){
        return portfolios.stream()
                .map(PortfolioDTO::mapPortfolio)
                .toList();
    }
}
