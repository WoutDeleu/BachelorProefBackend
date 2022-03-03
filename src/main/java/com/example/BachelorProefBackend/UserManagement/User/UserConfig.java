package com.example.BachelorProefBackend.UserManagement.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner1(UserRepository users, UserService userService) {
        return args -> {
            userService.addNewUser(new User_entity("Wannes", "Vermeiren", "wannesvermeirenzele@gmail.com", "+32 456 30 81 62", "password", new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Toon", "Eeraerts", "toon.eeraerts@student.kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Pol", "Thys", "Pol.thys@hotmail.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Alain", "Vandamme", "alain.vandamme@vandammeplastic.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Tony", "Wauters", "tony.wauters@kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(), 0));
            userService.addNewUser(new User_entity("Michel", "Drets", "michel.drets@vandammeplastic.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0));

            userService.addRoleToUser("wannesvermeirenzele@gmail.com", "ROLE_STUDENT");
            userService.addRoleToUser("toon.eeraerts@student.kuleuven.be", "ROLE_STUDENT");
            userService.addRoleToUser("Pol.thys@hotmail.eu", "ROLE_ADMIN");
            userService.addRoleToUser("michel.drets@vandammeplastic.eu", "ROLE_PROMOTOR");

        };
    }
}
