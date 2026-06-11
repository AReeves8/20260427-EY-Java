package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {}
