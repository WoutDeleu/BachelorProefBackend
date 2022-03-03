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
    CommandLineRunner commandLineRunner1(UserRepository users) {
        return args -> {
            User_entity student1 = new User_entity("Wannes", "Vermeiren", "wannes.vermeirenzele@gmail.com", "+32 456 30 81 62", "password", new ArrayList<>(), 0);
            User_entity student2 = new User_entity("Toon", "Eeraerts", "toon.eeraerts@student.kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(), 0);
            User_entity administrator = new User_entity("Pol", "Thys", "Pol.thys@hotmail.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0);
            User_entity promotor = new User_entity("Alain", "Vandamme", "alain.vandamme@vandammeplastic.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0);
            User_entity alphamale = new User_entity("Tony", "Wauters", "tony.wauters@kuleuven.be", "+32 456 30 81 62", "password", new ArrayList<>(), 0);
            User_entity coordinator = new User_entity("Michel", "Drets", "michel.drets@vandammeplastic.eu", "+32 456 30 81 62", "password", new ArrayList<>(), 0);

            users.saveAll(List.of(student1,student2,administrator,promotor,coordinator,alphamale));

        };
    }
}
