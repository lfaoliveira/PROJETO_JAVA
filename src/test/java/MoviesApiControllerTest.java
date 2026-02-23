import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainApp.controller.MoviesApiController;
import com.mainApp.model.entity.MovieEntity;
import com.mainApp.service.MovieApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MoviesApiController.class)
public class MoviesApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieApiService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetMovieDetails() throws Exception {
        // Dados do filme Star Wars
        MovieEntity movie = new MovieEntity();
        movie.setId(11);
        movie.setTitle("Star Wars");
        movie.setOriginalTitle("Star Wars");
        movie.setAdult(false);
        movie.setBackdropPath("/2w4xG178RpB4MDAIfTkqAuSJzec.jpg");
        movie.setPosterPath("/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg");
        movie.setOverview("Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.");
        movie.setReleaseDate("1977-05-25");
        movie.setBudget(11000000);
        movie.setRevenue(775398007);
        movie.setRuntime(121);
        movie.setVoteAverage(8.201);
        movie.setVoteCount(21958);
        movie.setPopularity(21.7621);
        movie.setTagline("A long time ago in a galaxy far, far away...");
        movie.setStatus("Released");
        movie.setImdbId("tt0076759");
        movie.setHomepage("http://www.starwars.com/films/star-wars-episode-iv-a-new-hope");
        movie.setOriginalLanguage("en");
        movie.setVideo(false);
        String mockmovieId = "11";
        when(movieService.getMovieByName("Star Wars", "en-US")).thenReturn(movie);


        mockMvc.perform(get("/api/movies/{id}", 11)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(11)).andExpect(jsonPath("$.title").value("Star Wars")).andExpect(jsonPath("$.original_title").value("Star Wars")).andExpect(jsonPath("$.adult").value(false)).andExpect(jsonPath("$.backdrop_path").value("/2w4xG178RpB4MDAIfTkqAuSJzec.jpg")).andExpect(jsonPath("$.poster_path").value("/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg")).andExpect(jsonPath("$.budget").value(11000000)).andExpect(jsonPath("$.revenue").value(775398007)).andExpect(jsonPath("$.runtime").value(121)).andExpect(jsonPath("$.vote_average").value(8.201)).andExpect(jsonPath("$.vote_count").value(21958)).andExpect(jsonPath("$.popularity").value(21.7621)).andExpect(jsonPath("$.tagline").value("A long time ago in a galaxy far, far away...")).andExpect(jsonPath("$.status").value("Released")).andExpect(jsonPath("$.imdb_id").value("tt0076759")).andExpect(jsonPath("$.release_date").value("1977-05-25"));
    }
}