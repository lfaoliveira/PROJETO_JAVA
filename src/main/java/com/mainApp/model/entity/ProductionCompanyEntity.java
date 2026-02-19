package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "production_companies")
public class ProductionCompanyEntity {
    private int id;
    private String logoPath;
    private String name;
    private String originCountry;
}
