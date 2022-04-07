package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Education.Education;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;
import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Table
@Entity
@NoArgsConstructor
public class TargetAudience {

    @Id
    @SequenceGenerator(name="targetaudience_sequence", sequenceName = "targetaudience_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "targetaudience_sequence")
    private long id;
    @ManyToOne
    private Campus campus;
    @ManyToOne
    private Faculty faculty;
    @ManyToOne
    private Education education;
    @ManyToMany(mappedBy = "targetAudiences")
    @JsonIgnore
    private Collection<Subject> allSubjects;



    public TargetAudience(Campus campus, Faculty faculty, Education education) {
        this.campus = campus;
        this.faculty = faculty;
        this.education = education;
    }

    @Override
    public String toString() {
        return "TargetAudience{" +
                "id=" + id +
                ", campus=" + campus +
                ", faculty=" + faculty +
                ", education=" + education +
                '}';
    }
}
