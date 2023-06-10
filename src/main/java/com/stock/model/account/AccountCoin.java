package com.stock.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stock.dto.coins.CoinDto;
import com.stock.helper.CoinBuyHelper;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

//    @Column(name = "price", columnDefinition = "DECIMAL(10,2)")
//    private BigDecimal price;

    @Column(name = "amountCOIN", columnDefinition = "DECIMAL(20,10)")
    private BigDecimal amountCOIN;

    @Column(name = "amountUSD", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amountUSD;

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
        BigDecimal amountCOIN = CoinBuyHelper.convertCoinByAmount(amountUSD, coin.getPriceUSD());

        AccountCoin accountCoin = new AccountCoin();
        accountCoin.setIdCoin(coin.getId());
        accountCoin.setName(coin.getName());
        accountCoin.setSymbol(coin.getSymbol());
        accountCoin.setAmountCOIN(amountCOIN);
        accountCoin.setAmountUSD(amountUSD);

        return accountCoin;
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
                ", amountCOIN=" + amountCOIN +
                ", amountUSD=" + amountUSD +
                ", created=" + created +
                '}';
    }
}
