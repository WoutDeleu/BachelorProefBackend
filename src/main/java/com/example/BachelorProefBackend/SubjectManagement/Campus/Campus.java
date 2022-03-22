package com.example.BachelorProefBackend.SubjectManagement.Campus;

import javax.persistence.*;

@Entity
@Table
public class Campus {

    @Id
    @SequenceGenerator(name="campus_sequence", sequenceName = "campus_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campus_sequence")
    private long id;
    private String name;
    private String address;

    public Campus() {}

    public Campus(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {return id;}
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {
        this.address = address;
    }

}
