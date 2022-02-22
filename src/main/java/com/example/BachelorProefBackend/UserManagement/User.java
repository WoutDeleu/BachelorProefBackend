package com.example.BachelorProefBackend.UserManagement;

import javax.persistence.*;

@Entity
@Table
public class User {
    //Tutorial:: https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpa-primary-key.html
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean isStudent;
    private boolean isAdministrator;
    private boolean isPromotor;
    private boolean isCoordinator;
    private long targetAudienceId;


    public User() {

    }

    public User(String firstname, String lastname, String email, boolean isStudent, boolean isAdministrator, boolean isPromotor, boolean isCoordinator) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.isStudent = isStudent;
        this.isAdministrator = isAdministrator;
        this.isPromotor = isPromotor;
        this.isCoordinator = isCoordinator;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public boolean isPromotor() {
        return isPromotor;
    }

    public void setPromotor(boolean promotor) {
        isPromotor = promotor;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean coordinator) {
        isCoordinator = coordinator;
    }

    public long getTargetAudienceId() {
        return targetAudienceId;
    }

    public void setTargetAudienceId(long targetAudienceId) {
        this.targetAudienceId = targetAudienceId;
    }
}