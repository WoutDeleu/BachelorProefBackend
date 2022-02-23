package com.example.BachelorProefBackend.UserManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner(UserRepository users) {
        return args -> {
            User_entity student1 = new User_entity("Wannes", "Vermeiren", "wannes.vermeiren@hotmail.eu", "0453699356", true, false, false, false);
            users.saveAll(List.of(student1));
        };
    }
}
