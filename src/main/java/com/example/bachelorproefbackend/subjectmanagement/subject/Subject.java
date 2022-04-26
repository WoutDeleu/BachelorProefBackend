package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.subjectmanagement.tag.Tag;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private Collection<UserEntity> students;
    @OneToMany(mappedBy = "finalSubject")
    private Collection<UserEntity> finalStudents; //
    @ManyToMany
    private Collection<TargetAudience> targetAudiences;
    @ManyToMany
    private Collection<Tag> tags;
    @ManyToOne
    private UserEntity promotor;
    private boolean approved; // Subjects from students or companies must be approved by coordinator or admin
    private boolean hasPdf;


    public Subject(String name, String description, int nrOfStudents, Tag [] tags) {
        this.name = name;
        this.description = description;
        this.nrOfStudents = nrOfStudents;
        this.tags = new ArrayList<>();
        for(int i = 0; i<tags.length; i++){
            this.tags.add(tags[i]);
        }
        approved = false;
    }

    public Subject(String name, String description, int nrOfStudents) {
        this.name = name;
        this.description = description;
        this.nrOfStudents = nrOfStudents;
        approved = false;
    }

    public void addTag (Tag tag) {tags.add(tag);}
    public void addTargetAudience (TargetAudience targetAudience) {targetAudiences.add(targetAudience);}
    public void addFinalStudent (UserEntity student) {finalStudents.add(student);}

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
                ", targetAudience=" + targetAudiences +
                ", tags=" + tags +
                '}';
    }

}
