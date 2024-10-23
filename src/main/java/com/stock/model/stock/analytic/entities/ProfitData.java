package com.stock.model.stock.analytic.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "profits_data")
@Data
public class ProfitData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "profit_data")
    private BigDecimal profitData;
    @Column(name = "date")
    private LocalDate date;
}
