package com.example.BachelorProefBackend.UserManagement.User;
import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.UserManagement.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity //Mapping to database
@Table
/* For the time being not in use, because I don't want any setters of contructors with access to id
@Data //Getters and setters (Lombok)
@NoArgsConstructor //Default constructor (Lombok)
@AllArgsConstructor //Constructor (Lombok)
 */
public class User_entity {

    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String telNr;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) //load all roles every time we load a user
    private Collection<Role> roles = new ArrayList<>();
    @ManyToMany
    @JsonIgnore // No recursion between user en subject, showing data over and over again
    @JoinTable(name="subject_preference")
    private Collection<Subject> preferredSubjects = new ArrayList<>();
    @ManyToOne // TwoToOne
    private Subject finalSubject;
    private long targetAudienceId;

    public User_entity() { }

    public User_entity(String firstname, String lastname, String email, String telNr, String password, Collection<Role> roles, Collection<Subject> preferredSubjects,long targetAudienceId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telNr = telNr;
        this.password = password;
        this.roles = roles;
        this.preferredSubjects = preferredSubjects;
        this.targetAudienceId = targetAudienceId;
    }

    public long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {return password;}
    public Collection<Role> getRoles() {return roles;}
    public Collection<Subject> getPreferredSubjects() {return preferredSubjects;}
    public Subject getFinalSubject(){return finalSubject;}
    public long getTargetAudienceId() {
        return targetAudienceId;
    }
    public String getTelNr() {
        return telNr;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {this.password = password;}
    public void setRoles(Collection<Role> roles) {this.roles = roles;}
    public void setPreferredSubjects(Collection<Subject> preferredSubjects) {this.preferredSubjects = preferredSubjects;}
    public void setFinalSubject(Subject finalSubject){this.finalSubject = finalSubject;}
    public void setTargetAudienceId(long targetAudienceId) {
        this.targetAudienceId = targetAudienceId;
    }
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public void addPreferredSubject(Subject subject){
        preferredSubjects.add(subject);
    }

    @Override
    public String toString() {
        return "User_entity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", preferredSubjects=" + preferredSubjects +
                ", finalSubject=" + finalSubject +
                ", targetAudienceId=" + targetAudienceId +
                '}';
    }
}