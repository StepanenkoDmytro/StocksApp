package com.stock.model.portfolio;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "profit")
@Data
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(name = "profit", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal profit;

    @Column(name = "created")
    @CreatedDate
    private Date created;
}
