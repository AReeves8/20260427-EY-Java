package com.skillstorm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.dtos.CounselorDto;
import com.skillstorm.models.Counselor;
import com.skillstorm.services.CounselorService;

// the main purpose of a controller class is to map HTTP requests to handler methods
// we can split off functionality between controllers based on URL

// this combines @Controller and @ResponseBody, which designate this class as a controller and one that returns JSON, not webpages
@RestController
// this maps all requests with /counselors after the domain:port to this controller
@RequestMapping("/counselors")
public class CounselorController {

    // we inject the CounselorService bean here just like we did in the service with the repo
    private final CounselorService service;

    public CounselorController(CounselorService service) {
        this.service = service;
    }

    // this method will be called when a GET request is made to /counselors
    @GetMapping // without specifying otherwise, this will use the same mapping as the overall controller
    // in this case, that's localhost:8080/counselors
    public ResponseEntity<Iterable<Counselor>> getAll(@RequestParam(required = false) String sw, 
                                                      @RequestParam(required = false) String fn) {
        return service.getAll(sw, fn);
    }

    // REST conventions indicate POST requests should go to the root URL for that entity type, like this:
    @PostMapping
    public ResponseEntity<Counselor> createOne(@RequestBody CounselorDto dto) {
        return service.createOne(dto);
    }

    @PutMapping("/{id}") // this is called a path variable, whatever value the user includes in the id will go into the id variable
    // we notate each variable with where it's coming from
    // @PathVariable takes it from the URL, @RequestBody takes it from the body of the request
    // for PathVariable, if the names match, you don't need the parentheses below; including here for clarity
    public ResponseEntity<Counselor> updateOne(@PathVariable("id") int id, @RequestBody CounselorDto dto) {
        return service.updateOne(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        return service.deleteOne(id);
    }

}
