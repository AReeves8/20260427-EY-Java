package com.skillstorm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.dtos.StudentDto;
import com.skillstorm.models.Student;
import com.skillstorm.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // find all
    // @ResponseStatus(org.springframework.http.HttpStatus.BAD_GATEWAY) // if you don't wish to use response entities, you can adjust the status with this
    @GetMapping
    public ResponseEntity<Iterable<Student>> getAll() {
        return service.getAll();
    }

    // create one
    @PostMapping
    public ResponseEntity<Student> createOne(@RequestBody StudentDto dto) {
        return service.createOne(dto);
    }

    // update one
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateOne(@PathVariable("id") int id, @RequestBody StudentDto dto) {
        return service.updateOne(id, dto);
    }

    // delete one
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        return service.deleteOne(id);
    }

}
