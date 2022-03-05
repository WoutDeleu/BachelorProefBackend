package com.example.BachelorProefBackend.UserManagement.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
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

        };
    }
}
