package com.skillstorm.dtos;

import com.skillstorm.models.Counselor;

public record StudentDto(String firstName, String lastName, Counselor counselor) {}
