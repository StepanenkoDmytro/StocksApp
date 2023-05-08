package com.stock.model.account;

import com.stock.model.user.Status;
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
}
