package com.skillstorm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    public Student() {
    }

    public Student(int id, String firstName, String lastName, Counselor counselor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.counselor = counselor;
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

}