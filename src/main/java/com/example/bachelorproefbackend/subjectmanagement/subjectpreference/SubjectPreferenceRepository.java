package com.example.bachelorproefbackend.subjectmanagement.subjectpreference;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SubjectPreferenceRepository extends JpaRepository<SubjectPreference, Long> {
    SubjectPreference findByIndex(int index);
    Collection<SubjectPreference> findBySubjectId(long subjectId);
    Collection<SubjectPreference> findByStudentId(long studentId);

}
