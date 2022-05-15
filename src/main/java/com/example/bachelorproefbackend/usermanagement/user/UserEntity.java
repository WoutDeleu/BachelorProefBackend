package com.example.bachelorproefbackend.usermanagement.user;
import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreference;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Data
@Table
@Entity
@NoArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telNr;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) //load all roles every time we load a user
    private Collection<Role> roles;
    @OneToMany(mappedBy = "student") //OneToThree
    @JsonIgnore
    private Collection<SubjectPreference> preferredSubjects;
    @ManyToMany
    @JsonIgnore
    @JoinTable(name="subject_favourites")
    private Collection<Subject> favouriteSubjects = new ArrayList<>();
    @ManyToMany
    @JoinTable(name="subject_boosted")
    @JsonIgnore
    private Collection<Subject> boostedSubjects = new ArrayList<>();
    @ManyToOne //TwoToOne
    @JsonIgnore
    private Subject finalSubject; //For students
    @ManyToOne
    private TargetAudience targetAudience;
    @OneToMany(mappedBy = "promotor")
    @JsonIgnore
    private Collection<Subject> subjects; //For promotor
    @ManyToOne
    @JsonIgnore
    private Company company;
    private String companyName;


    public UserEntity(String firstName, String lastName, String email, String telNr, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNr = telNr;
        this.password = password;
    }

    public void addFavouriteSubject(Subject subject){
        if(!favouriteSubjects.contains(subject)) favouriteSubjects.add(subject);
    }

    public void removeFavouriteSubject(Subject subject){
        favouriteSubjects.remove(subject);
    }
    public void addSubjectPreference(SubjectPreference sp){
        preferredSubjects.add(sp);
    }
    public void addBoostedSubject(Subject subject) {
        boostedSubjects.add(subject);
    }
    public void removeBoostedSubject(Subject subject) {
        boostedSubjects.remove(subject);
    }

    public void addRole(Role role) {
        if(roles==null) roles = new ArrayList<>(5);
        if(!roles.contains(role)) roles.add(role);
    }

    public void setCompany(Company company) {
        this.company = company;
        this.companyName = company.getName();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", preferredSubjects=" + preferredSubjects +
                ", favouriteSubjects=" + favouriteSubjects +
                ", finalSubject=" + finalSubject +
                ", targetAudience=" + targetAudience +
                ", subjects=" + subjects +
                ", company=" + company +
                '}';
    }


}