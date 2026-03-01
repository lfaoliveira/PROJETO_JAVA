package com.mainApp.service;

import com.mainApp.config.Secrets;
import com.mainApp.service.response.TmdbSearchMovieResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class MovieApiService {
    // Class responsible for TMDB API requests and business logic
    private final String ApiKey;
    private final RestClient restClient;
    public String baseUrl;

    public MovieApiService(@NotNull RestClient.Builder builder) {

        String apiTokenKey = Secrets.Keys.TMDB_API_KEY.name();

        this.ApiKey = Secrets.INSTANCE.get(apiTokenKey);
        baseUrl = "https://api.themoviedb.org/3";
        this.restClient = builder.build();
    }

    public TmdbSearchMovieResponse getMovieByName(String movieName, String language, String page) {
        return restClient.get()
                .uri("/search/movie?query={movieName}&include_adult=false&language={language}&page={page}", movieName, language, page)
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