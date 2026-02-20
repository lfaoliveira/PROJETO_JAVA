package com.mainApp.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "production_countries")
public class ProductionCountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String iso31661;
    private String name;
}
