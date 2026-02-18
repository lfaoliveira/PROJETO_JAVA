package com.mainApp.model.dto;

// Record to map Google Places Result
public record Movie(
        boolean adult,
        String backdropPath,
        Collection belongsToCollection,
        long budget,
        java.util.List<Genre> genres,
        String homepage,
        int id,
        String imdbId,
        java.util.List<String> originCountry,
        String originalLanguage,
        String originalTitle,
        String overview,
        double popularity,
        String posterPath,
        java.util.List<ProductionCompany> productionCompanies,
        java.util.List<ProductionCountry> productionCountries,
        String releaseDate,
        long revenue,
        int runtime,
        java.util.List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String title,
        boolean video,
        double voteAverage,
        int voteCount
) {

}