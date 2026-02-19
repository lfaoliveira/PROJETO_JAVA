package com.mainApp.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TmdbService {
    private TMDBResponseDto ApiSpec;
    private String API_KEY ;
    private String BASE_URL;
    private final RestClient restClient;

    public TmdbService(RestClient.Builder builder){

        this.ApiSpec = new TMDBResponseDto();
        this.API_KEY = this.ApiSpec.getApiKey();
        this.BASE_URL = this.ApiSpec.getBaseUrl();
        this.restClient = builder.baseUrl("https://api.themoviedb.org/3").build();
    }

    public MovieApiResponse getMovieDetails(String movieId, String apiKey) {
        return restClient.get()
                .uri("/movie/{id}?api_key={key}", movieId, apiKey)
                .retrieve()
                .body(MovieApiResponse.class); // Automatically maps JSON to your DTO
    }
}