package com.example.bachelorproefbackend.subjectmanagement.education;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
    Education findByName(String name);
}
