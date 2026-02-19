package com.mainApp.model.dto;

import com.mainApp.config.Secrets;

public class TMDBResponseDto {
    private String ApiKey = "your_api_key_here";
    private final String BaseUrl = "https://api.themoviedb.org";

    public TMDBResponse() {
        this.ApiKey = Secrets.get("TMDB_API_KEY", "123456789");
    }

    public static TMDBResponse fromJson(String string) {
        return null;
    }

    public String getApiKey() {
        return this.ApiKey;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }
}