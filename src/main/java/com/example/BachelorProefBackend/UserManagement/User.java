package com.example.BachelorProefBackend.UserManagement;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue
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