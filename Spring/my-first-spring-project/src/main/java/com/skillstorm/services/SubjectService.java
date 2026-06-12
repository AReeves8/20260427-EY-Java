package com.skillstorm.services;

import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        // notice how when uncommented this still gets caught by the global exception handler
        // int x = 1/0;

        return ResponseEntity.ok(this.repo.findAll());
    }

    // create one
    public ResponseEntity<Subject> createOne(SubjectDto dto) {
        return ResponseEntity.status(201).body(this.repo.save(new Subject(0, dto.title(), dto.teacher(), dto.students())));
    }

    // update one
    public ResponseEntity<Subject> updateOne(int id, SubjectDto dto) {
        if (this.repo.existsById(id)) {
            Subject updated = this.repo.save(new Subject(id, dto.title(), dto.teacher(), dto.students()));
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        // we COULD do it this way...
        // return ResponseEntity.notFound().build();

        // OR we can utilize our global exception handler!
        // this particular exception allows us to feed through a status and reason which will be picked up and presented to the user
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject with id " + id + " does not exist in the database.");
    }

    // delete one
    public ResponseEntity<Void> deleteOne(int id) {
        this.repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
