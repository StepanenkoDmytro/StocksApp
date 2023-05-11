package com.stock.model.account;

import com.stock.api.entity.Coin;
import com.stock.dto.CoinDto;
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
    private String transaction_type;
    @Column(name = "amount", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;
    @Column(name = "source")
    private String source;
    @Column(name = "status_transact")
    private String status;
    @Column(name = "reason_code")
    private String reason_code;
    @Column(name = "created")
    @CreatedDate
    private Date created;

    @PrePersist
    public void prePersist() {
        created = new Date();
    }

    public static Transact transactCOIN(String status, BigDecimal amount, String coin){
        Transact transactLog = new Transact();
        transactLog.setTransaction_type("BYU_COIN");
        transactLog.setAmount(amount);
        transactLog.setSource("online");
        transactLog.setStatus(status);
        transactLog.setReason_code("Buy_"+ coin);
        return transactLog;
    }
}
