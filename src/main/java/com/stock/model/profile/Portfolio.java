package com.stock.model.profile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stock.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "portfolio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio extends BaseEntity {
    @Column(name = "portfolio_id")
    private String accountID;
    @Column(name = "balance", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal balance;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            mappedBy = "portfolio")
    @JsonManagedReference
    private List<Profit> profitsList;
    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortfolioSpending> spendingsList;
    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortfolioCoin> coinsList;
    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortfolioStock> stocksList;
}
