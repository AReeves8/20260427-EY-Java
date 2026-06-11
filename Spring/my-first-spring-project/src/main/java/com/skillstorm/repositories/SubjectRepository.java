package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer> {}
