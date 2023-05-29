package com.stock.dto.trasact;

import com.stock.model.trasact.Transact;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactDto {
    private Long accountID;
    private String transactionType;
    private BigDecimal amount;
    private String source;
    private String status;
    private String reasonCode;
    private String purchaseDetails;
    private Date created;

    public TransactDto(Long accountID, String transactionType, BigDecimal amount, String source, String status ,String reasonCode, String purchaseDetails, Date created) {
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.source = source;
        this.status = status;
        this.reasonCode = reasonCode;
        this.purchaseDetails = purchaseDetails;
        this.created = created;
    }

    public static TransactDto mapTransactToDto(Transact transact) {
        return new TransactDto(
                transact.getAccount().getId(),
                transact.getTransaction_type().name(),
                transact.getAmount(),
                transact.getSource().name(),
                transact.getStatus(),
                transact.getReasonCode().name(),
                transact.getPurchaseDetails(),
                transact.getCreated()
        );
    }
}
