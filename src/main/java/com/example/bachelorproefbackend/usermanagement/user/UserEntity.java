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
import javax.transaction.Transactional;
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
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) //load all roles every time we load a user
    private Collection<Role> roles;
    @OneToMany(mappedBy = "student") //OneToThree
    private Collection<SubjectPreference> preferredSubjects;
    @ManyToMany
    @JsonIgnore //No recursion between user en subject, showing data over and over again
    @JoinTable(name="subject_favourites")
    private Collection<Subject> favouriteSubjects = new ArrayList<>();
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


    public UserEntity(String firstName, String lastName, String email, String telNr, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNr = telNr;
        this.password = password;
    }

    public void addFavouriteSubject(Subject subject){
        favouriteSubjects.add(subject);
    }

//    @Transactional
//    public void addSubjectPreference(Subject subject, int index){
//        SubjectPreference subjectPreference = null;
//        for(SubjectPreference sp : preferredSubjects){
//            if(sp.getIndex()==index) subjectPreference=sp;
//        }
//
//        if(subjectPreference==null){
//            subjectPreference = new SubjectPreference(subject, this, index);
//            preferredSubjects.add(subjectPreference);
//            log.info("zou in array moeten zitten");
//        }
//        else{
//            subjectPreference.setSubject(subject);
//        }
//    }

    public void addSubjectPreference(SubjectPreference sp){
        preferredSubjects.add(sp);
    }

    public void addRole(Role role) {
        if(roles==null) roles = new ArrayList<>(5);
        if(!roles.contains(role)) roles.add(role);
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