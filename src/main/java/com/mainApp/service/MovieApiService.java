package com.mainApp.service;

import com.mainApp.config.Secrets;
import com.mainApp.service.response.TmdbSearchMovieResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MovieApiService {
    // Class responsible for TMDB API requests and business logic
    private final String ApiKey;
    private final RestClient restClient;

    public MovieApiService(@NotNull RestClient.Builder builder) {
        Secrets sec = new Secrets();
        String apiTokenKey = Secrets.keys.TMDB_API_KEY.name();

        this.ApiKey = sec.get(apiTokenKey);
        String baseUrl = "https://api.themoviedb.org/3";
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public TmdbSearchMovieResponse getMovieByName(String movieName, String language, String page) {
        return restClient.get()
                .uri("query={movieName}&include_adult=false&language={language}&page={page}", movieName, language, page)
                .header("Authorization", "Bearer " + this.ApiKey)
                .retrieve()
                .body(TmdbSearchMovieResponse.class); // Automatically maps JSON to your DTO
    }

    public TmdbSearchMovieResponse getMovieById(String movieId) {
        return restClient.get()
                .uri("/movie/{id}?api_key={key}", movieId, this.ApiKey)
                .retrieve()
                .body(TmdbSearchMovieResponse.class); // Automatically maps JSON to your DTO
    }
}