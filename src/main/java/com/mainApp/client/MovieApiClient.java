package com.mainApp.client;

import com.mainApp.model.dto.TMDBResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MovieApiClient {
    private TMDBResponseDto ApiSpec;
    private String API_KEY ;
    private String BASE_URL;
    private final RestClient restClient;



    public MovieApiClient(RestClient.Builder builder){

        this.ApiSpec = new TMDBResponseDto();
        this.API_KEY = this.ApiSpec.getApiKey();
        this.BASE_URL = this.ApiSpec.getBaseUrl();
        this.restClient = builder.baseUrl("https://api.themoviedb.org/3").build();
    }

    public MovieResponse getMovieDetails(String movieId, String apiKey) {
        return restClient.get()
                .uri("/movie/{id}?api_key={key}", movieId, apiKey)
                .retrieve()
                .body(MovieResponse.class); // Automatically maps JSON to your DTO
    }
}






