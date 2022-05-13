package com.example.bachelorproefbackend.usermanagement.company;


import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    private String btwNr;
    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Collection<UserEntity> contacts;
    //Contact is the user from the company who has access to our system
    //Each company must have at least one contact, and they are created at the same time.
    private String description;
    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Collection<Subject> subjects;
    private boolean approved; // Companies must be approved by admin

    public Company() { }

    public Company(String name, String address, String btwNr, String description) {
        this.name = name;
        this.address = address;
        this.btwNr = btwNr;
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
    public String getBtwNr() {
        return btwNr;
    }
    public Collection<UserEntity> getContacts() {
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
    public void setBtwNr(String btwNr) {
        this.btwNr = btwNr;
    }
    public void setContacts(Collection<UserEntity> contacts) {
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
    public void addContact(UserEntity contact){contacts.add(contact);}
    public void addSubject(Subject subject){subjects.add(subject);}

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", BTWnr=" + btwNr +
                ", contacts=" + contacts +
                ", description='" + description + '\'' +
                ", subjects=" + subjects +
                '}';
    }

}

