package com.mainApp.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mainApp.config.Secrets;
import com.mainApp.service.response.TmdbSearchMovieResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MoviesApiControllerTest {

    private static WireMockServer wireMockServer;
    private static MovieApiService movieService;

    @BeforeAll
    static void setupWireMock() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        String baseUrl = "http://localhost:" + wireMockServer.port();
        String test_api_key = Secrets.INSTANCE.get(Secrets.Keys.TMDB_API_KEY.name());
        RestClient.Builder builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + test_api_key);

        movieService = new MovieApiService(builder);
    }

    @AfterAll
    static void tearDownWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void testSearchMoviesByName_ShouldValidateSchema() throws IOException {
        String jsonPath = "src/test/resources/search-movie-star-wars.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath)));

        stubFor(get(urlPathEqualTo("/search/movie"))
                .withQueryParam("query", equalTo("Star Wars"))
                .withQueryParam("include_adult", equalTo("false"))
                .withQueryParam("language", equalTo("en-US"))
                .withQueryParam("page", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)));
        TmdbSearchMovieResponse response = movieService.getMovieByName("Star Wars", "en-US", "1");


        // Validações de schema básicas
        assertThat(response).isNotNull();
        assertThat(response.page()).isPositive();
        assertThat(response.results()).isNotNull().isNotEmpty();
        assertThat(response.total_pages()).isPositive();
        assertThat(response.total_results()).isPositive();
        log.info("Response Schema OK");
        // Valida estrutura dos resultados
        response.results().forEach(movie -> {
            assertThat(movie.id()).isPositive();
            assertThat(movie.title()).isNotBlank();
        });

        verify(getRequestedFor(urlPathEqualTo("/search/movie"))
                .withQueryParam("query", equalTo("Star Wars")));
        log.info("Test OK");
    }
}
