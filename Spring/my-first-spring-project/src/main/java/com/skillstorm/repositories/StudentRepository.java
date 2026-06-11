package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {}
