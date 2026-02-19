package com.mainApp.service;

import com.mainApp.config.Secrets;
import com.mainApp.model.dto.MovieDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MovieApiService {
    // Class responsible for TMDB API requests and business logic
    private final String ApiKey;
    private final RestClient restClient;

    public MovieApiService(RestClient.Builder builder) {
        Secrets sec = new Secrets();
        String apiTokenKey = Secrets.keys.TMDB_API_KEY.name();

        this.ApiKey = sec.get(apiTokenKey);
        String baseUrl = "https://api.themoviedb.org/3";
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public MovieDto getMovieDetails(String movieId) {
        return restClient.get()
                .uri("/movie/{id}?api_key={key}", movieId, this.ApiKey)
                .retrieve()
                .body(MovieDto.class); // Automatically maps JSON to your DTO
    }
}