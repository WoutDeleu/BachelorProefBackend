package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SubjectAssignmentConfig {

    @Bean
    CommandLineRunner commandLineRunner3(SubjectAssignmentRepository subjectAssignments) {
        return args -> {
            SubjectAssignment subjectAssignment1 = new SubjectAssignment(3,2,0);
            SubjectAssignment subjectAssignment2 = new SubjectAssignment(1,1,6);


            subjectAssignments.saveAll(List.of(subjectAssignment1,subjectAssignment2));
        };
    }
}
