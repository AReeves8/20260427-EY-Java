package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.SubjectDto;
import com.skillstorm.models.Subject;
import com.skillstorm.repositories.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository repo;

    public SubjectService(SubjectRepository repo) {
        this.repo = repo;
    }

    // find all
    public ResponseEntity<Iterable<Subject>> getAll() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    // create one
    public ResponseEntity<Subject> createOne(SubjectDto dto) {
        return ResponseEntity.status(201).body(this.repo.save(new Subject(0, dto.title(), dto.teacher())));
    }

    // update one
    public ResponseEntity<Subject> updateOne(int id, SubjectDto dto) {
        if (this.repo.existsById(id)) {
            Subject updated = this.repo.save(new Subject(id, dto.title(), dto.teacher()));
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
