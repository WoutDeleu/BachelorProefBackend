package com.example.BachelorProefBackend.UserManagement;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.List;

@Configuration
public class UserConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner1(UserRepository users) {
        return args -> {
            User_entity student1 = new User_entity("Wannes", "Vermeiren", "wannes.vermeiren@hotmail.eu", "0453699356", true, false, false, false);
            User_entity student2 = new User_entity("Toon", "Eeraerts", "toon.eeraerts@hotmail.eu", "0453699356", true, false, false, false);
            User_entity administrator = new User_entity("Pol", "Thys", "Pol.thys@hotmail.eu", "0453699356", false, true, false, false);
            User_entity promotor = new User_entity("Alain", "Vandamme", "alain.vandamme@vandammeplastic.eu", "0453699356", false, false, true, false);
            User_entity coordinator = new User_entity("Michel", "Drets", "michel.drets@vandammeplastic.eu", "0453699356", false, false, false, true);

            User_entity alphamale = new User_entity("Tony", "Wauters", "tony.wauters@kuleuven.be", "0596345698", true, true, true,true);

            users.saveAll(List.of(student1,student2,administrator,promotor,coordinator,alphamale));
        };
    }
}
