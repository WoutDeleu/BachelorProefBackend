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
}