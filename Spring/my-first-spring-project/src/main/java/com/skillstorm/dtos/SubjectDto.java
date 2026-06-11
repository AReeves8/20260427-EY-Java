package com.skillstorm.dtos;

import java.util.List;

import com.skillstorm.models.Student;
import com.skillstorm.models.Teacher;

public record SubjectDto(String title, Teacher teacher, List<Student> students) {}
