package com.example.bachelorproefbackend.usermanagement.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.List;

@Configuration
public class RoleConfig {

    //add some default users to experiment with
    @Bean
    CommandLineRunner commandLineRunner4(RoleRepository roleRepository, RoleService roleService) {
        return args -> {
            Role student = new Role("ROLE_STUDENT");
            Role coordinator = new Role("ROLE_COORDINATOR");
            Role admin = new Role("ROLE_ADMIN");
            Role promotor = new Role("ROLE_PROMOTOR");
            Role contact = new Role("ROLE_CONTACT");

            roleService.addNewRole(student);
            roleService.addNewRole(coordinator);
            roleService.addNewRole(admin);
            roleService.addNewRole(promotor);
            roleService.addNewRole(contact);


        };
    }

}
