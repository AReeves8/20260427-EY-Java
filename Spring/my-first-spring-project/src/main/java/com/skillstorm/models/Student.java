package com.skillstorm.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // if we don't want to utilize the relationship, we can just leave this id as is
    // @Column(name = "counselor_id")
    // private int counselorId;

    // instead, if we want to return the counselor objects instead, we can map the relationship
    // here, it's a ManyToOne
    // we then specify the join column -- name is the column in THIS table, referencedColumnName is the column in the OTHER table
    @ManyToOne
    @JsonIgnoreProperties(value = {"students"})
    @JoinColumn(name = "counselor_id", referencedColumnName = "id")
    private Counselor counselor;

    @ManyToMany
    // for many to many, we can choose either side to be the controlling side, but we need some data
    // the JoinTable annotation lays out that data
    @JoinTable(
        name = "enrollment",                                    // the name of the join/junction table
        joinColumns = @JoinColumn(name = "student_id"),         // the column(s) in the join table pointing to THIS table
        inverseJoinColumns = @JoinColumn(name = "subject_id")   // the column(s) in the join table pointing to the OTHER table
    )
    @JsonIgnoreProperties(value = {"students"})
    private List<Subject> subjects;

    public Student() {
    }

    public Student(int id, String firstName, String lastName, Counselor counselor, List<Subject> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.counselor = counselor;
        this.subjects = subjects;
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

    public Counselor getCounselor() {
        return counselor;
    }

    public void setCounselor(Counselor counselor) {
        this.counselor = counselor;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}