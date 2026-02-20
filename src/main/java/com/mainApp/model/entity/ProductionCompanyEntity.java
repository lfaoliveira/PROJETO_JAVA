package com.mainApp.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "production_companies")
public class ProductionCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logoPath;
    private String name;
    private String originCountry;
}
