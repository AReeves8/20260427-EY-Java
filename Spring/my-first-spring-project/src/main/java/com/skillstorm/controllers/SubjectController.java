package com.skillstorm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.server.ResponseStatusException;

import com.skillstorm.dtos.SubjectDto;
import com.skillstorm.models.Subject;
import com.skillstorm.services.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    // creating a logger for use in this class

    private final SubjectService service;
    private final Logger logger;

    // when we use the factory to get a logger, we pass in the class; this class will show up in the logs, so we can track it
    public SubjectController(SubjectService service) {
        this.service = service;
        this.logger = LoggerFactory.getLogger(SubjectController.class);
    }

    // find all
    @GetMapping
    public ResponseEntity<Iterable<Subject>> getAll() {
        // System.out.println("Here's a regular old sysout...");
        // using the logger
        // there is a hierarchy of log levels, from most to least severe: error, warn, info, debug, trace
        // whatever level you set for your logs, you'll see those and any that are more severe
        logger.debug("*********************** Here's a debug level log instead of the old print statement. ************************");

        // runtime exceptions thrown ANYWHERE in our controller/service/repo dependency injection tree
        // will be automatically handled by Spring Web so as not to crash our program
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "This endpoint is not implemented yet.");

        // our global exception handler catches this here but also ANYWHERE in the Spring Web method stack
        // int x = 1/0;

        return service.getAll();
    }

    // create one
    @PostMapping
    public ResponseEntity<Subject> createOne(@RequestBody SubjectDto dto) {
        return service.createOne(dto);
    }

    // update one
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateOne(@PathVariable int id, @RequestBody SubjectDto dto) {
        return service.updateOne(id, dto);
    }

    // delete one
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        return service.deleteOne(id);
    }

}
