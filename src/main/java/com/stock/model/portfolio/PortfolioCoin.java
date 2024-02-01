package com.stock.model.portfolio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "portfolio_coins")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCoin implements Serializable {
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
    @Column(name = "id_coin")
    private String idCoin;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "count", columnDefinition = "DECIMAL(20,10)")
    private BigDecimal count;
    @Column(name = "avg_price", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal avgPrice;
    @Column(name = "created")
    @CreatedDate
    private Date created;
}
