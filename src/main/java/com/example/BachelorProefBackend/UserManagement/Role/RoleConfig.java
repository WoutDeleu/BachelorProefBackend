package com.example.BachelorProefBackend.UserManagement.Role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.List;

@Configuration
public class RoleConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner4(RoleRepository roleRepository) {
        return args -> {
            Role user = new Role("ROLE_STUDENT");
            Role manager = new Role("ROLE_COORDINATOR");
            Role admin = new Role("ROLE_ADMIN");
            Role superAdmin = new Role("ROLE_PROMOTOR");

            roleRepository.saveAll(List.of(user, manager, admin, superAdmin));

        };
    }
}
