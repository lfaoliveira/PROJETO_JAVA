package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "production_countries")
public class ProductionCountryEntity {
    private String iso31661;
    private String name;
}
