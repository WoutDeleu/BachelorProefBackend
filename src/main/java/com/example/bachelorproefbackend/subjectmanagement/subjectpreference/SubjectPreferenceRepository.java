package com.example.bachelorproefbackend.subjectmanagement.subjectpreference;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectPreferenceRepository extends JpaRepository<SubjectPreference, Long> {
    SubjectPreference findByIndex(int index);


}
