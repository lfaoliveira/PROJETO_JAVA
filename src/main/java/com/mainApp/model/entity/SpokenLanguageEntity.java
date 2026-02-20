package com.mainApp.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "languages")
public class SpokenLanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String englishName;
    private String iso6391;
    private String name;
}
