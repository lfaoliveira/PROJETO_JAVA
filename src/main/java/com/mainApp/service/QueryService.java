package com.mainApp.service;

import com.mainApp.config.Secrets;
import org.springframework.stereotype.Service;


@Service
public class QueryService {

    private final TmdbService movieApiClient;
    private final Secrets secrets; // Assuming this holds your API Key

    public QueryService(TmdbService movieApiClient, Secrets secrets) {
        this.movieApiClient = movieApiClient;
        this.secrets = secrets;
    }

    public void processMovieData(String movieId) {
        String apiTokenKey = Secrets.keys.TMDB_API_KEY.name();
        MovieApiResponse data = movieApiClient.getMovieDetails(movieId, secrets.get(apiTokenKey));
        // Do something with the data...
        System.out.println("Fetched: " + data.getTitle());
    }
}


