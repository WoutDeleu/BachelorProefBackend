package com.example.BachelorProefBackend.UserManagement;
import javax.persistence.*;

@Entity //Mapping naar database
@Table
public class User_entity {
    //Tutorial:: https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpa-primary-key.html
    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String telNr;
    private boolean isStudent;
    private boolean isAdministrator;
    private boolean isPromotor;
    private boolean isCoordinator;
    private Long targetAudienceId;


    public User_entity() { }

    public User_entity(String firstname, String lastname, String email, String telNr, boolean isStudent, boolean isAdministrator, boolean isPromotor, boolean isCoordinator) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telNr = telNr;
        this.isStudent = isStudent;
        this.isAdministrator = isAdministrator;
        this.isPromotor = isPromotor;
        this.isCoordinator = isCoordinator;
    }


    public Long getId() {
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
    public boolean isStudent() {
        return isStudent;
    }
    public boolean isAdministrator() {
        return isAdministrator;
    }
    public boolean isPromotor() {
        return isPromotor;
    }
    public boolean isCoordinator() {
        return isCoordinator;
    }
    public Long getTargetAudienceId() {
        return targetAudienceId;
    }
    public String getTelNr() {
        return telNr;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void setStudent(boolean student) {
        isStudent = student;
    }
    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
    public void setPromotor(boolean promotor) {
        isPromotor = promotor;
    }
    public void setCoordinator(boolean coordinator) {
        isCoordinator = coordinator;
    }
    public void setTargetAudienceId(Long targetAudienceId) {
        this.targetAudienceId = targetAudienceId;
    }
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    @Override
    public String toString() {
        return "User_entity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", isStudent=" + isStudent +
                ", isAdministrator=" + isAdministrator +
                ", isPromotor=" + isPromotor +
                ", isCoordinator=" + isCoordinator +
                ", targetAudienceId=" + targetAudienceId +
                '}';
    }
}