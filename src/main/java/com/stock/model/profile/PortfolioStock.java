package com.stock.model.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stock.model.account.Account;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "portfolio_stocks")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioStock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Column(name = "asset_type")
    private String assetType;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "exchange")
    private String exchange;
    @Column(name = "currency")
    private String currency;
    @Column(name = "avg_price")
    private BigDecimal avgPrice;
    @Column(name = "count")
    private int count;
    @Column(name = "country")
    private String country;
    @Column(name = "sector")
    private String sector;
    @Column(name = "industry")
    private String industry;
    @Column(name = "dividend_yield")
    private BigDecimal dividendYield;
    @Column(name = "created")
    @CreatedDate
    private Date created;
}
