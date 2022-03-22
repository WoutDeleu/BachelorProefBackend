package com.example.BachelorProefBackend.SubjectManagement.Faculty;

import javax.persistence.*;

@Entity
@Table
public class Faculty {

    @Id
    @SequenceGenerator(name="faculty_sequence", sequenceName = "faculty_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_sequence")
    private long id;
    private String name;

    public Faculty() {}

    public Faculty(String name) {
        this.name = name;
    }

    public long getId() {return id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
