package com.example.bachelorproefbackend.subjectmanagement.targetaudience;

import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TargetAudienceRepository extends JpaRepository<TargetAudience, Long> {
    List<TargetAudience> findAllByFaculty(Faculty faculty);

    List<TargetAudience> findAllByEducation(Education education);

    List<TargetAudience> findAllByCampus(Campus campus);

    List<TargetAudience> findAllByCampusId(long id);

}
