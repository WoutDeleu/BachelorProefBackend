package com.example.BachelorProefBackend.SubjectManagement.Tag;


import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Data
@Table
@Entity
@NoArgsConstructor
public class Tag {

    @Id
    @SequenceGenerator(name="tag_sequence", sequenceName = "tag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    private long id;
    private String name;
    @ManyToMany
    private Collection<Subject> allSubjects;

    public Tag(String name){
        this.name = name;
    }


}
