package com.mainApp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean adult;
    private String backdropPath;
    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "belongs_to_collection_id")
    private CollectionEntity belongsToCollection;
    private long budget;
    @ManyToMany
    @JoinTable(name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres;

    private String homepage;
    private String imdbId;
    private List<String> originCountry;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    @ManyToMany
    @JoinTable(name = "movie_companies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<ProductionCompanyEntity> productionCompanies;
    @ManyToMany
    @JoinTable(name = "movie_contries",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<ProductionCountryEntity> productionCountries;
    private String releaseDate;
    private long revenue;
    private int runtime;
    @ManyToMany
    @JoinTable(name = "movie_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<SpokenLanguageEntity> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

}