package com.stock.model.icons;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "google_icons")
@Data
public class GoogleIcon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_icon")
    private String categoryIcon;
    @Column(name = "icon")
    private String icon;
}
