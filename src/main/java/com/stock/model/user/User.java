package com.stock.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stock.model.BaseEntity;
import com.stock.model.portfolio.Portfolio;
import lombok.*;

import javax.persistence.*;
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
