package com.skillstorm.dtos;

// this DTO (Data Transfer Object) is used to transfer data between layers of the application
// we're using it to take incoming requests that don't include full Java objects
public record CounselorDto(String firstName, String lastName) {}
