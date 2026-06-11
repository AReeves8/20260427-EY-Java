package com.skillstorm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.dtos.SubjectDto;
import com.skillstorm.models.Subject;
import com.skillstorm.services.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    // find all
    @GetMapping
    public ResponseEntity<Iterable<Subject>> getAll() {
        return service.getAll();
    }

    // create one
    @PostMapping
    public ResponseEntity<Subject> createOne(@RequestBody SubjectDto dto) {
        return service.createOne(dto);
    }

    // update one
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateOne(@PathVariable("id") int id, @RequestBody SubjectDto dto) {
        return service.updateOne(id, dto);
    }

    // delete one
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        return service.deleteOne(id);
    }

}
