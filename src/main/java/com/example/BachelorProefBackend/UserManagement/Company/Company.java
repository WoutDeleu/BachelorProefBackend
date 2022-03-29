package com.example.BachelorProefBackend.UserManagement.Company;


import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.SubjectManagement.Subject.SubjectService;
import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
public class Company {
    @Id
    @SequenceGenerator(name="company_sequence", sequenceName = "company_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_sequence")
    private long id;
    private String name;
    private String address;
    private String BTWnr;
    @OneToMany(mappedBy = "company")
    private Collection<User_entity> contacts;
    //Contact is the user from the company who has access to our system
    //Each company must have at least one contact, and they are created at the same time.
    private String description;
    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Collection<Subject> subjects;
    private boolean approved; // Companies must be approved by admin

    public Company() { }

    public Company(String name, String address, String BTWnr, String description) {
        this.name = name;
        this.address = address;
        this.BTWnr = BTWnr;
        this.description = description;
        approved = false;
    }

    public long getId() {return id;}
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getBTWnr() {
        return BTWnr;
    }
    public Collection<User_entity> getContacts() {
        return contacts;
    }
    public String getDescription() {
        return description;
    }
    public Collection<Subject> getSubjects() {
        return subjects;
    }
    public boolean isApproved() {
        return approved;
    }

    public void setName(String name) {this.name = name;}
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBTWnr(String BTWnr) {
        this.BTWnr = BTWnr;
    }
    public void setContacts(Collection<User_entity> contacts) {
        this.contacts = contacts;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public void addContact(User_entity contact){contacts.add(contact);}
    public void addSubject(Subject subject){subjects.add(subject);}

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", BTWnr=" + BTWnr +
                ", contacts=" + contacts +
                ", description='" + description + '\'' +
                ", subjects=" + subjects +
                '}';
    }

}

