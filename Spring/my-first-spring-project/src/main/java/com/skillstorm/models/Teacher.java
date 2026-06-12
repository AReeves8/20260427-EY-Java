package com.skillstorm.models;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // adding validations for properties
    // when the user sends in an object to the controller, these will kick in and check if it meets the requirements
    // the object MUST be tagged in the controller with the @Valid annotation
    // when we get the error back, we can see exactly which properties violated which constraints
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 32)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 32)
    @Column(name = "last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"teacher"})
    private Subject subject;

    public Teacher() {
    }

    public Teacher(int id, String firstName, String lastName, Subject subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}