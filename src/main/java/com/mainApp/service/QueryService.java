package com.mainApp.service;

import com.mainApp.client.MovieApiClient;
import com.mainApp.client.MovieResponse;
import com.mainApp.config.Secrets;
import org.springframework.stereotype.Service;


@Service
public class QueryService {

    private final MovieApiClient movieApiClient;
    private final Secrets secrets; // Assuming this holds your API Key

    public QueryService(MovieApiClient movieApiClient, Secrets secrets) {
        this.movieApiClient = movieApiClient;
        this.secrets = secrets;
    }

    public void processMovieData(String movieId) {
        String apiTokenKey = Secrets.keys.TMDB_API_KEY.name();
        MovieResponse data = movieApiClient.getMovieDetails(movieId, secrets.get(apiTokenKey));
        // Do something with the data...
        System.out.println("Fetched: " + data.getTitle());
    }
}


