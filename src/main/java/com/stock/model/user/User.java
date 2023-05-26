package com.stock.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stock.model.BaseEntity;
import com.stock.model.account.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "userAccountsCoins",
        attributeNodes = {
        @NamedAttributeNode(value = "accounts")
})
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            mappedBy = "user")
    private List<Account> accounts;

    public User() {
    }

    public void addAccount(Account account) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        accounts.add(account);
        account.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", image=" + image +
                '}';
    }
}
