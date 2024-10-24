package com.stock.model.user;

import com.stock.config.BaseEntity;
import com.stock.model.role.Role;
import com.stock.model.user.portfolio.entities.Portfolio;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "userPortfolios",
        attributeNodes = {
        @NamedAttributeNode(value = "portfolios")
})
public class User extends BaseEntity {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

//    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            mappedBy = "user")
    private List<Portfolio> portfolios;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "recovery_code_expiration")
    private LocalDateTime recoveryCodeExpiration;

    @Column(name = "auth_source")
    private String authSource;

    public User() {
    }

    public void addPortfolio(Portfolio portfolio) {
        if (portfolios == null) {
            portfolios = new ArrayList<>();
        }
        portfolios.add(portfolio);
        portfolio.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
