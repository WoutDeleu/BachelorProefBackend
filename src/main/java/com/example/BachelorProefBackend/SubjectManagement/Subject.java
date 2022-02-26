package com.example.BachelorProefBackend.SubjectManagement;
import javax.persistence.*;

@Entity
@Table
public class Subject {
    @Id
    @SequenceGenerator(name="subject_sequence", sequenceName = "subject_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
    private long id;

}
