package com.mainApp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private String browserName;

    @Column(nullable = false)
    private String browserVersion;

    @Column(nullable = false)
    private String requestPath;

    @Column(nullable = false)
    private String httpMethod;

    @Column(nullable = false)
    private Integer statusCode;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}

