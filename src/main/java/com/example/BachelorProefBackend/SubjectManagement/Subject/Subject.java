package com.example.BachelorProefBackend.SubjectManagement.Subject;

import com.example.BachelorProefBackend.SubjectManagement.Tag.Tag;
import com.example.BachelorProefBackend.SubjectManagement.TargetAudience.TargetAudience;
import com.example.BachelorProefBackend.UserManagement.Company.Company;
import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Table
@Entity
@NoArgsConstructor
public class Subject {

    @Id
    @SequenceGenerator(name="subject_sequence", sequenceName = "subject_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
    private long id;
    private String name;
    private String description;
    private int nrOfStudents;
    @ManyToOne
    private Company company;
    @ManyToMany(mappedBy = "preferredSubjects")
    private Collection<User_entity> students;
    @OneToMany(mappedBy = "finalSubject")
    private Collection<User_entity> finalStudents; //
    @ManyToMany
    private Collection<TargetAudience> targetAudience;
    @ManyToMany
    private Collection<Tag> tags;
    @ManyToOne
    private User_entity promotor;


    public Subject(String name, String description, int nrOfStudents) {
        this.name = name;
        this.description = description;
        this.nrOfStudents = nrOfStudents;
    }

    public void addTag (Tag tag) {tags.add(tag);}


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", nrOfStudents=" + nrOfStudents +
                ", company=" + company +
                ", students=" + students +
                ", finalStudents=" + finalStudents +
                ", targetAudience=" + targetAudience +
                ", tags=" + tags +
                '}';
    }

}
