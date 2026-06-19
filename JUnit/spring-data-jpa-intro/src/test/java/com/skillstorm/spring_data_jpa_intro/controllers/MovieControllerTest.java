package com.skillstorm.spring_data_jpa_intro.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spring_data_jpa_intro.models.Director;
import com.skillstorm.spring_data_jpa_intro.models.Genre;
import com.skillstorm.spring_data_jpa_intro.models.Movie;
import com.skillstorm.spring_data_jpa_intro.services.MovieService;

@WebMvcTest(MovieController.class)
@DisplayName("MovieController - Web Layer Tests")
class MovieControllerTest {

    // NOTE: Autowired is ok to use in test classes, and sometimes is the only way to have Spring inject a bean for us.


    // MockMvc lets you send fake HTTP requests directly to the controller
    // without starting a real server. @WebMvcTest configures it automatically.
    @Autowired
    private MockMvc mockMvc;

    // Jackson's ObjectMapper converts Java objects to/from JSON.
    // Used to build request bodies for POST and PUT calls.
    @Autowired
    private ObjectMapper objectMapper;

    // @MockitoBean is the Spring equivalent of @Mock (replaced @MockBean in Spring Boot 3.4+).
    // It registers a Mockito mock in the Spring application context so the
    // controller can have it injected — the real MovieService is never created.
    @MockitoBean
    private MovieService movieService;

    private Movie testMovie;

    @BeforeEach
    void dataInit() {
        testMovie = new Movie(1, "Inception", 9, Genre.SCIENCE_FICTION, 
            new Director(1, "Christopher", "Nolan"));
    }

    @Nested
    @DisplayName("GET /api/v1/movies")
    class GetAllMovies {

        @Test
        @DisplayName("200 OK with a list of all movies when no rating filter is given")
        void returnsAllMovies() throws Exception {
            when(movieService.getMovies(null)).thenReturn(List.of(testMovie));

            mockMvc.perform(get("/api/v1/movies"))
                    .andExpect(status().isOk())
                    // jsonPath navigates the JSON response body using dot/bracket notation
                    .andExpect(jsonPath("$[0].title").value("Inception"))
                    .andExpect(jsonPath("$[0].rating").value(9));
        }

        @Test
        @DisplayName("200 OK with a filtered list when a rating query param is supplied")
        void returnsFilteredMovies() throws Exception {
            when(movieService.getMovies(8)).thenReturn(List.of(testMovie));

            mockMvc.perform(get("/api/v1/movies").param("rating", "8"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        @DisplayName("404 Not Found when the service returns null (no matching movies)")
        void returns404WhenNoMoviesFound() throws Exception {
            when(movieService.getMovies(anyInt())).thenReturn(null);

            mockMvc.perform(get("/api/v1/movies").param("rating", "99"))
                    .andExpect(status().isNotFound());
        }
    }
}
