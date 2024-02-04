package com.stock.model.portfolio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "portfolio_spending")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioSpending implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Column(name = "category")
    private String category;
    @Column(name = "title")
    private String title;
    @Column(name = "cost", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal cost;
    @Column(name = "date")
    @CreatedDate
    private Date date;

    public PortfolioSpending(String id, String category, String title, BigDecimal cost, Date date) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.cost = cost;
        this.date = date;
    }
}
