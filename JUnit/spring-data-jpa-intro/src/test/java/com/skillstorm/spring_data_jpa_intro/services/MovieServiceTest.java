package com.skillstorm.spring_data_jpa_intro.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skillstorm.spring_data_jpa_intro.models.Director;
import com.skillstorm.spring_data_jpa_intro.models.Genre;
import com.skillstorm.spring_data_jpa_intro.models.Movie;
import com.skillstorm.spring_data_jpa_intro.repositories.MovieRepository;

/**
 * ExtendWith - JUnit annotation to tell the class that it needs some other extensions
 *  - MockitoExtension is needed to initialize the Mocks
 */
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    /**
     * Mock - create a dummy object that we can use
     * InjectMocks - will create the object with any needed mocks
     *      
     *  - MovieService cannot be created without a MovieRepository or DirectorService
     *    so mock those dependencies and then inject them so we can use our MovieService class   
     */
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private DirectorService directorService;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie;

    @BeforeEach
    void dataInit() {
        testMovie = new Movie(1, "Inception", 9, Genre.SCIENCE_FICTION, 
            new Director(1, "Christopher", "Nolan"));
    }

    /**
     * Can group related tests together by putting them in nested classes
     *      - be sure to use @Nested so JUnit knows to look in this class for tests
     */
    @Nested
    @DisplayName("getMovies()")
    class GetMovies {
        @Test
        @DisplayName("returns all movies when getMovies() does not recieve a rating param")
        void returnsAllMoviesWhenNoRatingFilter() {

            // mock repository call to return our test movie data 
            when(movieRepository.findAll()).thenReturn(List.of(testMovie));

            // call the method
            List<Movie> results = movieService.getMovies(null);

            /**
             * assert methods are used to verify test results
             *      - when they fail, they throw an exception
             *      - TESTS WILL ONLY FAIL IF AN EXCEPTION IS THROWN DURING EXECUTION
             * 
             *      - can have MANY assertions inside of a method, ALL will need to pass for the test to pass
             */
            assertEquals(1, results.size());

            /**
             * verify() - checks if a specific method was invoked or not
             *      - different verification modes (like never()) to verify the invocation of other methods
             * 
             *      - throw exceptions if they fail
             */
            verify(movieRepository).findAll();
            verify(movieRepository, never()).findByRatingGreaterThanEqual(anyInt());
        }

        @Test
        @DisplayName("returns filtered movies when getMovies() recieves a rating param")
        void returnsFilteredMoviesWhenRatingGiven() {

            // mocking the findByRatingGreaterThanEqual method
            when(movieRepository.findByRatingGreaterThanEqual(8))
                .thenReturn(Optional.of(List.of(testMovie)));

            // calling the method
            List<Movie> results = movieService.getMovies(8);

            // verifying results
            assertNotNull(results);
            assertEquals(1, results.size());
        }

        @Test
        @DisplayName("returns filtered movies when getMovies() recieves a rating param")
        void returnsNoMoviesWhenFilteredResultsIsEmpty() {

            // mocking the findByRatingGreaterThanEqual method
            when(movieRepository.findByRatingGreaterThanEqual(10))
                .thenReturn(Optional.empty());

            // calling the method
            List<Movie> results = movieService.getMovies(10);

            // verifying results
            assertNull(results);
        }
    }
    

}
