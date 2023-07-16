package com.stock.model.trasact;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountStock;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transacts")
@Data
public class Transact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "count", columnDefinition = "DECIMAL(20,10)")
    private BigDecimal count;
    @Column(name = "price", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;
    @Column(name = "amount", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;
    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private Source source;
    @Column(name = "status_transact")
    private String status;
    @Column(name = "reason_code")
    @Enumerated(EnumType.STRING)
    private ReasonCode reasonCode;

    @Column(name = "purchase_details")
    private String purchaseDetails;

    @Column(name = "created")
    @CreatedDate
    private Date created;

    @PrePersist
    public void prePersist() {
        created = new Date();
    }

    public static Transact transactCOIN(String status, BigDecimal amount, AccountCoin coin){
        Transact transactLog = new Transact();
        transactLog.setTransactionType(TransactionType.BUY_CRYPTO);
        transactLog.setAmount(amount);
        transactLog.setSource(Source.COINCAP);
        transactLog.setStatus(status);
        transactLog.setReasonCode(ReasonCode.BUY_CRYPTO_SUCCESS);
        transactLog.setPurchaseDetails(coin.getName());
        return transactLog;
    }

    public static Transact transactSTOCK(String status, BigDecimal purchasePrice, AccountStock stock){
        Transact transactLog = new Transact();
        transactLog.setTransactionType(TransactionType.BUY_STOCKS);
        transactLog.setAmount(purchasePrice);
        transactLog.setSource(Source.BIG_BANK);
        transactLog.setStatus(status);
        transactLog.setReasonCode(ReasonCode.BUY_STOCKS_FAILED);
        transactLog.setPurchaseDetails(stock.getName());
        return transactLog;
    }

    public static Transact transactDeposit(BigDecimal deposit){
        Transact transact = new Transact();
        transact.setTransactionType(TransactionType.DEPOSIT_ACCOUNT);
        transact.setAmount(deposit);
        transact.setSource(Source.BIG_BANK);
        transact.setStatus("success");
        transact.setReasonCode(ReasonCode.DEPOSIT_SUCCESS);
        transact.setPurchaseDetails("USD");
        return transact;
    }
}
