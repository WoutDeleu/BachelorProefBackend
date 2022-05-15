package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectService;
import com.example.bachelorproefbackend.subjectmanagement.tag.Tag;
import com.example.bachelorproefbackend.subjectmanagement.tag.TagRepository;
import com.example.bachelorproefbackend.subjectmanagement.tag.TagService;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.company.CompanyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;


import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectRepository;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner(TagService tagService, UserService userService, SubjectRepository subjectRepository, SubjectService subjectService, CompanyService companyService, TagRepository tagRepository) {
        return args -> {
            UserEntity pieter = new UserEntity("Pieter", "Vermeiren", "p.vermeiren@hamann.be", "+32 456 30 81 62", "password");
            userService.addNewUser(pieter);
            userService.addNewUser(new UserEntity("Toon", "Eeraerts", "toon.eeraerts@student.kuleuven.be", "+32 456 30 81 62", "password"));
            userService.addNewUser(new UserEntity("Wout", "Deleu", "wout.deleu@student.kuleuven.be", "+32 456 30 81 62", "password"));
            userService.addNewUser(new UserEntity("Wannes", "Vermeiren", "wannes.vermeiren@student.kuleuven.be", "+32 456 30 81 62", "password"));
            userService.addNewUser(new UserEntity("Tony", "Wauters", "tony.wauters@kuleuven.be", "+32 456 30 81 62", "password"));
            userService.addNewUser(new UserEntity("Liesbet", "Van der Perre", "liesbet.vanderperre@kuleuven.be", "+32 456 30 81 62", "password"));
            userService.addNewUser(new UserEntity("Admin", " ", "admin@kuleuven.be", "+32 456 30 81 62", "password"));

            userService.addRoleToUser("wout.deleu@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("wannes.vermeiren@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("toon.eeraerts@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("tony.wauters@kuleuven.be", "ROLE_COORDINATOR");
            userService.addRoleToUser("liesbet.vanderperre@kuleuven.be", "ROLE_COORDINATOR");
            userService.addRoleToUser("tony.wauters@kuleuven.be", "ROLE_PROMOTOR");
            userService.addRoleToUser("admin@kuleuven.be", "ROLE_ADMIN");
            userService.addRoleToUser("p.vermeiren@hamann.be", "ROLE_CONTACT");

            Tag tag1 = new Tag("Luchtvaart");
            Tag tag2 = new Tag("Software engineering");
            Tag tag3 = new Tag("Machine learning");
            tagService.addNewTag(tag1);
            tagService.addNewTag(tag2);
            tagService.addNewTag(tag3);

            Subject subject1 = new Subject("Vliegtuigen", "Hoe blijven ze in de lucht?", 1);
            Subject subject2 = new Subject("Software project failure", "Hoe zorgen we dat dit project in goede banen loopt?", 1);
            Subject subject3 = new Subject("Atletiek 800m", "Wat is de optimale split voor alle 800m tijden?", 2);
            Subject subject4 = new Subject("Kernfusie", "Zal kernfusie ooit rendabel worden?", 1);
            Subject subject5 = new Subject("Datatransmissie", "Optimaliseer de communicatie tussen wagens en de cloud.", 3);
            subjectRepository.save(subject1);
            subjectRepository.save(subject2);
            subjectRepository.save(subject3);
            subjectRepository.save(subject4);
            subjectRepository.save(subject5);

            companyService.addNewCompany(new Company("Hamann","Vantegemstraat 3, 9230 Wetteren", "BE 0873.604.566", "Transportcompany"));
            companyService.addNewCompany(new Company("TomTom", "Gaston Crommenlaan 4, 9050 Ledeberg", "BE 0436.193.459", "Traffic control Software"));
            companyService.addNewCompany(new Company("Coolblue", "Borsbeeksebrug 28, 2600 Berchem", "BE 0867.686.774", "Elektronicaverkoop"));

        };
    }
}
