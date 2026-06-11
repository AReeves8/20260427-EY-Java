package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.StudentDto;
import com.skillstorm.models.Student;
import com.skillstorm.repositories.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // find all
    public ResponseEntity<Iterable<Student>> getAll() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    // create one
    public ResponseEntity<Student> createOne(StudentDto dto) {
        return ResponseEntity.status(201).body(this.repo.save(new Student(0, dto.firstName(), dto.lastName(), dto.counselor())));
    }

    // update one
    public ResponseEntity<Student> updateOne(int id, StudentDto dto) {
        if (this.repo.existsById(id)) {
            Student updated = this.repo.save(new Student(id, dto.firstName(), dto.lastName(), dto.counselor()));
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
