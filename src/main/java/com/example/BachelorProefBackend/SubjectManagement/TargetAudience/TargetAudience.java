package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;

import javax.persistence.*;

@Entity
@Table
public class TargetAudience {

    @Id
    @SequenceGenerator(name="targetaudience_sequence", sequenceName = "targetaudience_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "targetaudience_sequence")
    private long id;
    @ManyToOne
    private Campus campus;
    @ManyToOne
    private Faculty faculty;

    public TargetAudience() { }

    public TargetAudience(Campus campus, Faculty faculty) {
        this.campus = campus;
        this.faculty = faculty;
    }

    public long getId() {
        return id;
    }
    public Campus getCampus() {
        return campus;
    }
    public Faculty getFaculty() {
        return faculty;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
