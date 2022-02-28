package com.example.BachelorProefBackend.SubjectManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SubjectConfig {

    @Bean
    CommandLineRunner commandLineRunner2(SubjectRepository subjects) {
        return args -> {
            Subject subject1 = new Subject("Vliegtuigen", "Hoe blijven ze in de lucht?", 0, 0, 0);
            Subject subject2 = new Subject("Software project failure", "Hoe zorgen we dat dit project in goede banen loopt?", 0, 0,0);
            Subject subject3 = new Subject("Nalu studie", "Hoeveel nalu's is te veel voor Toon", 0, 0, 0);

            subjects.saveAll(List.of(subject1,subject2,subject3));
        };
    }
}
