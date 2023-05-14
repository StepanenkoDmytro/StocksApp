package com.stock.model.account;

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
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;
    @Column(name = "amount", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;
    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private Source source;
    @Column(name = "status_transact")
    private String status;
    @Column(name = "reason_code")
    @Enumerated(EnumType.STRING)
    private ReasonCode reason_code;

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
        transactLog.setTransaction_type(TransactionType.BUY_CRYPTO);
        transactLog.setAmount(amount);
        transactLog.setSource(Source.COINCAP);
        transactLog.setStatus(status);
        transactLog.setReason_code(ReasonCode.BUY_CRYPTO_SUCCESS);
        transactLog.setPurchaseDetails(coin.getName());
        return transactLog;
    }

    public static Transact transactDeposit(BigDecimal deposit){
        Transact transact = new Transact();
        transact.setTransaction_type(TransactionType.DEPOSIT_ACCOUNT);
        transact.setAmount(deposit);
        transact.setSource(Source.BIG_BANK);
        transact.setStatus("success");
        transact.setReason_code(ReasonCode.DEPOSIT_SUCCESS);
        transact.setPurchaseDetails("USD");
        return transact;
    }
}
