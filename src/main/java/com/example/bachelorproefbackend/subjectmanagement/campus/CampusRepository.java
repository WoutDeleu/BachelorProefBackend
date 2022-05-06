package com.example.bachelorproefbackend.subjectmanagement.campus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
    Campus findByName(String name);
}
