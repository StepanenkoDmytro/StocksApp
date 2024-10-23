package com.stock.model.user.account.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stock.dto.coins.CoinDto;
import com.stock.service.helpers.CoinBuyHelper;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account_coins")
@Data
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AccountCoin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "id_coin")
    private String idCoin;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;

    @Column(name = "count_coin", columnDefinition = "DECIMAL(20,10)")
    private BigDecimal countCoin;

    @Column(name = "average_price", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal avgPrice;

    @Column(name = "created")
    @CreatedDate
    private Date created;


    public AccountCoin() {
    }

    @PrePersist
    public void prePersist() {
        created = new Date();
    }


    public static AccountCoin fromCoin(CoinDto coin, BigDecimal amountUSD){
        BigDecimal amountCOIN = CoinBuyHelper.convertCoinByAmount(amountUSD, coin.getPrice());

        AccountCoin accountCoin = new AccountCoin();
        accountCoin.setIdCoin(coin.getId());
        accountCoin.setName(coin.getName());
        accountCoin.setSymbol(coin.getSymbol());
        accountCoin.setCountCoin(amountCOIN);
        accountCoin.setAvgPrice(coin.getPrice());

        return accountCoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCoin that = (AccountCoin) o;
        return Objects.equals(idCoin, that.idCoin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCoin);
    }
    @Override
    public String toString() {
        return "AccountCoin{" +
//                "id=" + id +
//                ", account=" + account.getAccount_name() +
                ", id_coin='" + idCoin + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
//                ", price=" + price +
                ", amountCOIN=" + countCoin +
                ", amountUSD=" + avgPrice +
                ", created=" + created +
                '}';
    }
}
