package com.skillstorm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Artist;
import com.skillstorm.services.ArtistService;

// the main purpose of a controller class is to map HTTP requests to handler methods
// we can split off functionality between controllers based on URL

// this combines @Controller and @ResponseBody, which designate this class as a controller and one that returns JSON, not webpages
@RestController
// this maps all requests with /artists after the domain:port to this controller
@RequestMapping("/artists")
public class ArtistController {

    // we inject the ArtistService bean here just like we did in the service with the repo
    private final ArtistService service;

    public ArtistController(ArtistService artistService) {
        this.service = artistService;
    }

    // this method will be called when a GET request is made to /artists
    @GetMapping // without specifying otherwise, this will use the same mapping as the overall controller
    // in this case, that's localhost:8080/artists
    public Iterable<Artist> getAll() {
        return service.getAll();
    }

}
