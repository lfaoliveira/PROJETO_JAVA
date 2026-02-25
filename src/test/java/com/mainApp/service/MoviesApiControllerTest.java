package com.mainApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mainApp.model.dto.TmdbResumedMovieDto;
import com.mainApp.service.response.TmdbSearchMovieResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class MoviesApiControllerTest {

    private static WireMockServer wireMockServer;
    private static MovieApiService movieService;
    private static ObjectMapper objectMapper = new ObjectMapper(); // Jackson para converter JSON em Objeto

    @BeforeAll
    static void setupWireMock() {
        // 1. Inicia o servidor na porta dinâmica
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        // 2. Configura o RestClient para apontar para o WireMock (Localhost)
        String baseUrl = "http://localhost:" + wireMockServer.port();

        RestClient.Builder builder = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer test-token"); // Header padrão simulado

        // 3. Instancia o SERVIÇO REAL (sem @Override fake)
        // Isso garante que estamos testando a lógica real de serialização/deserialização do seu service
        movieService = new MovieApiService(builder);
    }

    @AfterAll
    static void tearDownWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void testSearchMoviesByName_ShouldMatchFirst5Results() throws IOException {
        // --- ARRANGE ---
        // 1. Lendo o arquivo JSON do disco
        String jsonPath = "src/test/resources/search-movie-star-wars.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath)));

        // 2. Convertendo o JSON para o Objeto Esperado (A "Gabarito")
        // Isso é crucial: transformamos o arquivo em objeto Java para comparar objeto com objeto
        TmdbSearchMovieResponse expectedResponse = objectMapper.readValue(jsonContent, TmdbSearchMovieResponse.class);

        // 3. Configurando o WireMock para retornar esse exato JSON quando chamado
        stubFor(get(urlPathEqualTo("/search/movie"))
                .withQueryParam("query", equalTo("Star Wars"))
                .withQueryParam("include_adult", equalTo("false"))
                .withQueryParam("language", equalTo("en-US"))
                .withQueryParam("page", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonContent)));

        // --- ACT ---
        TmdbSearchMovieResponse actualResponse = movieService.getMovieByName("Star Wars", "en-US", "1");

        // --- ASSERT ---

        // Validação básica
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.results()).isNotEmpty();

        // PEGAR APENAS OS PRIMEIROS 5 ELEMENTOS (Tanto do Real quanto do Esperado)
        List<TmdbResumedMovieDto> actualFirst5 = actualResponse.results().subList(0, 5);
        List<TmdbResumedMovieDto> expectedFirst5 = expectedResponse.results().subList(0, 5);

        // A Mágica do AssertJ: Comparação Recursiva
        // Isso verifica se title, id, overview, poster_path, etc. são IDÊNTICOS campo a campo
        assertThat(actualFirst5)
                .usingRecursiveComparison()
                .ignoringCollectionOrder() // Opcional: Se a ordem exata importar, remova essa linha
                .isEqualTo(expectedFirst5);

        // Verificação extra de chamada
        verify(getRequestedFor(urlPathEqualTo("/search/movie"))
                .withQueryParam("query", equalTo("Star Wars")));
    }
}