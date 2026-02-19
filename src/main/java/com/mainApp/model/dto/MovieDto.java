package com.mainApp.model.dto;

// Record to map Google Places Result
public record MovieDto(
        boolean adult,
        String backdropPath,
        CollectionDto belongsToCollection,
        long budget,
        java.util.List<GenreDto> genres,
        String homepage,
        int id,
        String imdbId,
        java.util.List<String> originCountry,
        String originalLanguage,
        String originalTitle,
        String overview,
        double popularity,
        String posterPath,
        java.util.List<ProductionCompanyDto> productionCompanies,
        java.util.List<ProductionCountryDto> productionCountries,
        String releaseDate,
        long revenue,
        int runtime,
        java.util.List<SpokenLanguageDto> spokenLanguages,
        String status,
        String tagline,
        String title,
        boolean video,
        double voteAverage,
        int voteCount
) {

}