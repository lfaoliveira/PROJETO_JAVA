package com.mainApp.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "genres")
@Data
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
