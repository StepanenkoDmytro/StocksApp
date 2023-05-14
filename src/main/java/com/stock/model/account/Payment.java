package com.stock.model.account;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "beneficiary")
    private String beneficiary;

    @Column(name = "beneficiary_acc_no")
    private String beneficiary_acc_no;

    @Column(name = "amount", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @Column(name = "reference_no")
    private String reference_no;

    @Column(name = "status_payment")
    private String status;

    @Column(name = "reason_code")
    private String reason_code;

    @Column(name = "created")
    @CreatedDate
    private Date created;
}
