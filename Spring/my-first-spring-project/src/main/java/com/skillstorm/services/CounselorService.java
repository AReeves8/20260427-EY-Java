package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.CounselorDto;
import com.skillstorm.models.Counselor;
import com.skillstorm.repositories.CounselorRepository;

// the @Service annotation is like the @Repository one, but for service classes
// it will be automatically detected and registered as a bean in the Spring context
// it indicates a class being used for business logic between controller and repository
@Service
public class CounselorService {

    // we need a Bean of type CounselorRepository
    // this requires dependency injection
    // there are three ways to do this:

    // 1. @Autowired
    // least preferable, doesn't work great with testing
    // @Autowired
    // private CounselorRepository counselorRepository;

    // 2. Setter-based injection
    // better but not the most preferred
    // private CounselorRepository counselorRepository;

    // @Autowired
    // public void setCounselorRepository(CounselorRepository counselorRepository) {
    //     this.counselorRepository = counselorRepository;
    // }

    // 3. Constructor-based injection
    // most preferred, works great with testing
    private final CounselorRepository repo;

    // @Autowired -- this annotation is no longer required but won't break it if you include it
    // if you have multiple constructors, the one with the @Autowired annotation will be used
    public CounselorService(CounselorRepository repo) {
        this.repo = repo;
    }

    // each method in our controller will call to a different method here
    // this one is for getting all Counselors
    // important to know what gets returned from each repo method, but we can use this class to alter what is returned
    public ResponseEntity<Iterable<Counselor>> getAll(String lastNameStartsWith) {
        if (lastNameStartsWith == null) {
            return ResponseEntity.ok(this.repo.findAll());
        }
        return ResponseEntity.ok(this.repo.findByLastNameStartsWith(lastNameStartsWith));
    }

    // create one
    // very shorthand syntax here, note that we're hardcoding in an id of 0 so as not to overwrite anything
    public ResponseEntity<Counselor> createOne(CounselorDto dto) {
        return ResponseEntity.status(201).body(this.repo.save(new Counselor(0, dto.firstName(), dto.lastName())));
    }

    // update one by ID
    public ResponseEntity<Counselor> updateOne(int id, CounselorDto dto) {
        // some things to be aware of -- we must check that the id exists, and we have to remember that save is used for both creating AND updating
        if (this.repo.existsById(id)) {
            Counselor updated = this.repo.save(new Counselor(id, dto.firstName(), dto.lastName()));
            // to control the response per different results, we can use ResponseEntity
            // here, we're adjusting the status and adding a body with the updated Counselor
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        // here, we're using a pre-fab method to add a 404 status, then building with no (null) body
        return ResponseEntity.notFound().build();
    }

    // delete one
    public ResponseEntity<Void> deleteOne(int id) {
        this.repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
