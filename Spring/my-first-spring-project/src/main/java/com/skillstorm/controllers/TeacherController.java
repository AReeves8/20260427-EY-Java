package com.skillstorm.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.dtos.TeacherDto;
import com.skillstorm.models.Teacher;
import com.skillstorm.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teachers")
@Profile("dev")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    // find all
    @GetMapping
    public ResponseEntity<Iterable<Teacher>> getAll() {
        return service.getAll();
    }

    // create one
    @PostMapping
    public ResponseEntity<Teacher> createOne(@RequestBody TeacherDto dto) {
        return service.createOne(dto);
    }

    // update one
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateOne(@PathVariable("id") int id, @RequestBody TeacherDto dto) {
        return service.updateOne(id, dto);
    }

    // delete one
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        return service.deleteOne(id);
    }

    // a method for checking if our validation is working!
    @PostMapping("/validation")
    public ResponseEntity<String> checkValidation(@RequestBody @Valid Teacher teacher) {
        return ResponseEntity.ok("VALID!!");
    }

}
