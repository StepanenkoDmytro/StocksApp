package com.stock.model.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stock.model.BaseEntity;
import com.stock.model.trasact.Payment;
import com.stock.model.trasact.Transact;
import com.stock.model.user.User;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "account_number")
    private String accountNumber;

    //TODO: Delete
    @Column(name = "account_name")
    private String accountName;

    //TODO: Delete
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "balance", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal balance;

    @Column(name = "fixed_profit", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal fixedProfit;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "account")
    @JsonManagedReference
    private List<Payment> payments;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "account")
    @JsonManagedReference
    private List<Transact> transacts;

    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountCoin> coins;

    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountStock> stocks;

    public Account() {
    }

    public void addStocks(AccountStock stock) {
        if(stock == null) {
            stocks = new ArrayList<>();
        }
        stocks.add(stock);
        stock.setAccount(this);
    }

    public void addCoins(AccountCoin coin) {
        if(coins == null) {
            coins = new ArrayList<>();
        }
        coins.add(coin);
        coin.setAccount(this);
    }

    public void addTransact(Transact transact){
        if(transacts == null){
            transacts = new ArrayList<>();
        }
        transact.setUserID(user.getId());
        transact.setAccount(this);
        transacts.add(transact);
    }

    public void setPayment(Payment payment){
        if(payments == null) {
            payments = new ArrayList<>();
        }
        payment.setUserID(user.getId());
        payment.setAccount(this);
        payments.add(payment);
    }

    @PrePersist
    @Override
    public void prePersist() {
        super.prePersist();
        balance = new BigDecimal(0);
        fixedProfit = new BigDecimal(0);
    }

    @Override
    public String toString() {
        return "Account{" +
                ", account_number='" + accountNumber + '\'' +
                ", account_name='" + accountName + '\'' +
                ", account_type=" + accountType +
                ", balance=" + balance +
                '}';
    }
}
