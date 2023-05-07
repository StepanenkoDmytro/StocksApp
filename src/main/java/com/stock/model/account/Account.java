package com.stock.model.account;

import com.stock.model.BaseEntity;
import com.stock.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "account_number")
    private String account_number;

    @Column(name = "account_name")
    private String account_name;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType account_type;

    @Column(name = "balance")
    private BigDecimal balance;
}
