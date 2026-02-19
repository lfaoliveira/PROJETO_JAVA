package com.mainApp.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean adult;
    private String backdropPath;
    @OneToOne
    @JoinColumn(name = "belongs_to_collection_id")
    private CollectionEntity belongsToCollection;
    private long budget;
    private List<GenreEntity> genres;
    private String homepage;
    private String imdbId;
    private List<String> originCountry;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private List<ProductionCompanyEntity> productionCompanies;
    private List<ProductionCountryEntity> productionCountries;
    private String releaseDate;
    private long revenue;
    private int runtime;
    private List<SpokenLanguageEntity> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    public CollectionEntity getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(CollectionEntity belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

}