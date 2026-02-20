package com.mainApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Bean
    public WebClient tmdbClient(WebClient.Builder builder) {
        return builder.baseUrl("https://api.themoviedb.org/3").build();
    }
}