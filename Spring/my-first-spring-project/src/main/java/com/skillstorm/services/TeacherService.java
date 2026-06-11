package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.TeacherDto;
import com.skillstorm.models.Teacher;
import com.skillstorm.repositories.TeacherRepository;

@Service
public class TeacherService {

    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }

    // find all
    public ResponseEntity<Iterable<Teacher>> getAll() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    // create one
    public ResponseEntity<Teacher> createOne(TeacherDto dto) {
        return ResponseEntity.status(201).body(this.repo.save(new Teacher(0, dto.firstName(), dto.lastName(), dto.subject())));
    }

    // update one
    public ResponseEntity<Teacher> updateOne(int id, TeacherDto dto) {
        if (this.repo.existsById(id)) {
            Teacher updated = this.repo.save(new Teacher(id, dto.firstName(), dto.lastName(), dto.subject()));
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // delete one
    public ResponseEntity<Void> deleteOne(int id) {
        this.repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
