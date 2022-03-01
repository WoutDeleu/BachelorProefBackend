package com.example.BachelorProefBackend.UserManagement.User;
import com.example.BachelorProefBackend.UserManagement.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity //Mapping naar database
@Table
@Data //Getters and setters (Lombok)
@NoArgsConstructor //Default constructor (Lombok)
@AllArgsConstructor //Constructor (Lombok)
public class User_entity {
    //Tutorial:: https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpa-primary-key.html
    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String telNr;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)//load all roles every time we load a user
    private Collection<Role> roles = new ArrayList<>();
    private long targetAudienceId;
//    private boolean isStudent;
//    private boolean isAdministrator;
//    private boolean isPromotor;
//    private boolean isCoordinator;



//    public User_entity() { }
//
//    public User_entity(String firstname, String lastname, String email, String telNr, boolean isStudent, boolean isAdministrator, boolean isPromotor, boolean isCoordinator) {
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.email = email;
//        this.telNr = telNr;
//        this.isStudent = isStudent;
//        this.isAdministrator = isAdministrator;
//        this.isPromotor = isPromotor;
//        this.isCoordinator = isCoordinator;
//    }
//
//
//    public long getId() {
//        return id;
//    }
//    public String getFirstname() {
//        return firstname;
//    }
//    public String getLastname() {
//        return lastname;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public boolean isStudent() {
//        return isStudent;
//    }
//    public boolean isAdministrator() {
//        return isAdministrator;
//    }
//    public boolean isPromotor() {
//        return isPromotor;
//    }
//    public boolean isCoordinator() {
//        return isCoordinator;
//    }
//    public long getTargetAudienceId() {
//        return targetAudienceId;
//    }
//    public String getTelNr() {
//        return telNr;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public void setStudent(boolean student) {
//        isStudent = student;
//    }
//    public void setAdministrator(boolean administrator) {
//        isAdministrator = administrator;
//    }
//    public void setPromotor(boolean promotor) {
//        isPromotor = promotor;
//    }
//    public void setCoordinator(boolean coordinator) {
//        isCoordinator = coordinator;
//    }
//    public void setTargetAudienceId(long targetAudienceId) {
//        this.targetAudienceId = targetAudienceId;
//    }
//    public void setTelNr(String telNr) {
//        this.telNr = telNr;
//    }

    @Override
    public String toString() {
        return "User_entity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
//                ", isStudent=" + isStudent +
//                ", isAdministrator=" + isAdministrator +
//                ", isPromotor=" + isPromotor +
//                ", isCoordinator=" + isCoordinator +
                ", targetAudienceId=" + targetAudienceId +
                '}';
    }
}