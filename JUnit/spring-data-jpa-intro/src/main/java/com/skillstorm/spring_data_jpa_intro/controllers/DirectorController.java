package com.skillstorm.spring_data_jpa_intro.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.spring_data_jpa_intro.models.Director;
import com.skillstorm.spring_data_jpa_intro.services.DirectorService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/directors")
@CrossOrigin({
    "https://d1s6x4sf3u5pf5.cloudfront.net",    // backend CloudFront domain 
    "https://d1hp3vvy5gkcob.cloudfront.net",    // frontend CloudFront domain
    "http://127.0.0.1:5500", 
    "http://localhost:4200"})       
public class DirectorController {

    private final DirectorService service;

    public DirectorController(DirectorService service) {
        this.service = service;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Director>> getAllDirectors(
        @RequestParam(defaultValue = "2") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(service.getAllDirectors(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Director>> getAllDirectors() {
        return new ResponseEntity<>(service.getAllDirectors(), HttpStatus.OK);
    }
    
}
