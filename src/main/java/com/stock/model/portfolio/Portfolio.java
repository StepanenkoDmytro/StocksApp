package com.stock.model.portfolio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stock.model.BaseEntity;
import com.stock.model.user.User;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@Table(name = "portfolio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
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

    public void addSpending(PortfolioSpending newSpending) {
        if(spendingsList == null) {
            spendingsList = new ArrayList<>();
        }
        PortfolioSpending existingSpending = spendingsList.stream()
                .filter(spending -> spending.getId() != null && spending.getId().equals(newSpending.getId()))
                .findFirst().orElse(null);

        if (existingSpending == null) {
            spendingsList.add(newSpending);
            newSpending.setPortfolio(this);
        } else {
            int index = spendingsList.indexOf(existingSpending);
            spendingsList.set(index, newSpending);
            newSpending.setPortfolio(this);
        }
    }

    public void deleteSpending(String id) {
        if(spendingsList == null) {
            return;
        }
        Iterator<PortfolioSpending> iterator = spendingsList.iterator();
        while (iterator.hasNext()) {
            PortfolioSpending spending = iterator.next();
            if (spending.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}
