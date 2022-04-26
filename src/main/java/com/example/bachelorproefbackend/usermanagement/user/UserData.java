package com.example.bachelorproefbackend.usermanagement.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {
    private int totalAmount;
    private int nrOfAdmins;
    private int nrOfCoordinators;
    private int nrOfPromotors;
    private int nrOfStudents;
    private int nrOfContacts;
    private int nrOfCompanies;
    private int nrOfNonApprovedCompanies;
    private int nrOfStudentsWithFinalSubject;

}
