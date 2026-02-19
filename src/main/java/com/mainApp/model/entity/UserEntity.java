package com.mainApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class UserEntity {
    private Long id;
    private String sessionId;
}
