package com.stock.model.portfolio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "portfolio_category")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCategory implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Column(name = "title")
    private String title;
    @Column(name = "icon")
    private String icon;
    @Column(name = "parent_id")
    private String parent;

    public PortfolioCategory(String id, String title, String icon, String parent) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.parent = parent;
    }
}
