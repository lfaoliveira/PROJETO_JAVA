package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "languages")
public class SpokenLanguageEntity {
    private String englishName;
    private String iso6391;
    private String name;
}
