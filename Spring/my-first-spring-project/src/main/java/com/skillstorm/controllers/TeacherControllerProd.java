package com.skillstorm.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Teacher;
import com.skillstorm.services.TeacherService;

@RestController
@RequestMapping("/teachers")
@Profile("prod")
public class TeacherControllerProd {

    private final TeacherService service;

    public TeacherControllerProd(TeacherService service) {
        this.service = service;
    }

    // find all
    @GetMapping
    public ResponseEntity<Iterable<Teacher>> getAll() {
        return service.getAll();
    }

}
