package com.example.BachelorProefBackend.SubjectManagement.Education;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table
@Entity
@NoArgsConstructor
public class Education {
    @Id
    @SequenceGenerator(name="campus_sequence", sequenceName = "campus_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campus_sequence")
    private long id;
    private String name;


    public Education(String name) {
        this.name = name;
    }
}
