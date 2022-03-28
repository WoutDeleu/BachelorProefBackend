package com.example.BachelorProefBackend.SubjectManagement.Campus;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table
@Entity
@NoArgsConstructor
public class Campus {

    @Id
    @SequenceGenerator(name="campus_sequence", sequenceName = "campus_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campus_sequence")
    private long id;
    private String name;
    private String address;


    public Campus(String name, String address) {
        this.name = name;
        this.address = address;
    }



}
