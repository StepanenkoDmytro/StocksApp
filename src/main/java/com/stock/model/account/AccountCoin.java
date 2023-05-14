package com.stock.model.account;

import com.stock.dto.CoinDto;
import com.stock.helper.CoinBuyHelper;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account_coins")
@Data
public class AccountCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "id_coin")
    private String id_coin;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;

    @Column(name = "price", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

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
        accountCoin.setId_coin(coin.getId());
        accountCoin.setName(coin.getName());
        accountCoin.setSymbol(coin.getSymbol());
        accountCoin.setPrice(coin.getPriceUSD());
        accountCoin.setAmountCOIN(amountCOIN);
        accountCoin.setAmountUSD(amountUSD);

        return accountCoin;
    }
}
