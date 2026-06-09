package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Artist;
import com.skillstorm.repositories.ArtistRepository;

// the @Service annotation is like the @Repository one, but for service classes
// it will be automatically detected and registered as a bean in the Spring context
// it indicates a class being used for business logic between controller and repository
@Service
public class ArtistService {

    // we need a Bean of type ArtistRepository
    // this requires dependency injection
    // there are three ways to do this:

    // 1. @Autowired
    // least preferable, doesn't work great with testing
    // @Autowired
    // private ArtistRepository artistRepository;

    // 2. Setter-based injection
    // better but not the most preferred
    // private ArtistRepository artistRepository;

    // @Autowired
    // public void setArtistRepository(ArtistRepository artistRepository) {
    //     this.artistRepository = artistRepository;
    // }

    // 3. Constructor-based injection
    // most preferred, works great with testing
    private final ArtistRepository repo;

    // @Autowired -- this annotation is no longer required but won't break it if you include it
    // if you have multiple constructors, the one with the @Autowired annotation will be used
    public ArtistService(ArtistRepository artistRepository) {
        this.repo = artistRepository;
    }

    // each method in our controller will call to a different method here
    // this one is for getting all Artists
    // important to know what gets returned from each repo method, but we can use this class to alter what is returned
    public Iterable<Artist> getAll() {
        return this.repo.findAll();
    }

}
