package com.mainApp.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "collections")
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String posterPath;
    private String backdropPath;
}
