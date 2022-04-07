package com.example.bachelorproefbackend.subjectmanagement.tag;


import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Collection<Subject> allSubjects;

    public Tag(String name){
        this.name = name;
    }


}
