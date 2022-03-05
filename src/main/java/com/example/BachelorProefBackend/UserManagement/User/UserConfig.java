package com.example.BachelorProefBackend.UserManagement.User;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

import com.example.BachelorProefBackend.SubjectManagement.Subject.SubjectRepository;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner(UserService userService, SubjectRepository subjectRepository) {
        return args -> {
            userService.addNewUser(new User_entity("Toon", "Eeraerts", "toon.eeraerts@student.kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(),0));
            userService.addNewUser(new User_entity("Wout", "Deleu", "wout.deleu@student.kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Wannes", "Vermeiren", "wannes.vermeiren@student.kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Tony", "Wauters", "tony.wauters@kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Liesbet", "Van der Perre", "liesbet.vanderperre@kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Admin", " ", "admin@kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(),new ArrayList<>(), 0));

            userService.addRoleToUser("wout.deleu@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("wannes.vermeiren@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("toon.eeraerts@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("tony.wauters@kuleuven.be", "ROLE_COORDINATOR");
            userService.addRoleToUser("liesbet.vanderperre@kuleuven.be", "ROLE_COORDINATOR");
            userService.addRoleToUser("tony.wauters@kuleuven.be", "ROLE_PROMOTOR");
            userService.addRoleToUser("admin@kuleuven.be", "ROLE_ADMIN");

            Subject subject1 = new Subject("Vliegtuigen", "Hoe blijven ze in de lucht?", 1, 0, 0, 0, new ArrayList<>());
            Subject subject2 = new Subject("Software project failure", "Hoe zorgen we dat dit project in goede banen loopt?", 1,0, 0,0, new ArrayList<>());
            Subject subject3 = new Subject("Nalu studie", "Hoeveel nalu's is te veel voor Toon", 2,0, 0, 0, new ArrayList<>());
            subjectRepository.saveAll(List.of(subject1, subject2, subject3));

            userService.addNewPreferredSubject(7, subject1);
            userService.addNewPreferredSubject(8, subject2);
            userService.addNewPreferredSubject(6, subject3);

        };
    }
}
