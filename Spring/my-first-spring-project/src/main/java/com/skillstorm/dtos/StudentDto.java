package com.skillstorm.dtos;

import java.util.List;

import com.skillstorm.models.Counselor;
import com.skillstorm.models.Subject;

public record StudentDto(String firstName, String lastName, Counselor counselor, List<Subject> subjects) {}
