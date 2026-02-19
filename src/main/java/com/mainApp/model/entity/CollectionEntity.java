package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "collections")
public class CollectionEntity {
    private int id;
    private String name;
    private String posterPath;
    private String backdropPath;

}
