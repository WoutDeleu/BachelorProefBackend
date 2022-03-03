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
            Role user = new Role("ROLE_USER");
            Role manager = new Role("ROLE_MANAGER");
            Role admin = new Role("ROLE_ADMIN");
            Role superAdmin = new Role("ROLE_SUPER_ADMIN");

            roleRepository.saveAll(List.of(user, manager, admin, superAdmin));

        };
    }
}
