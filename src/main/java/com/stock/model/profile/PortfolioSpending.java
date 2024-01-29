package com.stock.model.profile;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}
