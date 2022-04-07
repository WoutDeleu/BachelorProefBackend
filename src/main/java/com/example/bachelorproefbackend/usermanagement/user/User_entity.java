package com.example.bachelorproefbackend.usermanagement.user;
import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Table
@Entity
@NoArgsConstructor
public class User_entity {

    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telNr;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) //load all roles every time we load a user
    private Collection<Role> roles;
    @ManyToMany
    @JsonIgnore //No recursion between user en subject, showing data over and over again
    @JoinTable(name="subject_preference")
    private Collection<Subject> preferredSubjects = new ArrayList<>();
    @ManyToOne //TwoToOne
    private Subject finalSubject; //For students
    @ManyToOne
    private TargetAudience targetAudience;
    @OneToMany(mappedBy = "promotor")
    @JsonIgnore
    private Collection<Subject> subject; //For promotor
    @ManyToOne
    @JsonIgnore
    private Company company;


    public User_entity(String firstName, String lastName, String email, String telNr, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNr = telNr;
        this.password = password;
    }

    public void addPreferredSubject(Subject subject){
        preferredSubjects.add(subject);
    }


    @Override
    public String toString() {
        return "User_entity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", preferredSubjects=" + preferredSubjects +
                ", finalSubject=" + finalSubject +
                '}';
    }


}