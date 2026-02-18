package com.mainApp.model.entity;

import java.util.List;

public class MovieEntity {
    private boolean adult;
    private String backdropPath;
    private CollectionEntity belongsToCollection;
    private long budget;
    private List<GenreEntity> genres;
    private String homepage;
    private int id;
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
}