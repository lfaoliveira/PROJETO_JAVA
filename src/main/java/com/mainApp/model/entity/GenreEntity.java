package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "genres")
public class GenreEntity {
    private int id;
    private String name;
}
