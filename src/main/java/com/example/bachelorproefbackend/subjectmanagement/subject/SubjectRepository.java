package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findById(long id);

    List<Subject> findAllByCompany_Id(long id);

    List<Subject> findAllByApproved(boolean bool);

    List<Subject> findAllByTargetAudiencesContains(TargetAudience targetAudience);

    int countSubjectsByApproved(boolean bool);

//    @Query(value="SELECT s FROM Subject s WHERE s.approved = false")
//    List<Subject> findAllNonApproved();

//    @Query(value="SELECT COUNT (s) FROM Subject s WHERE s.approved = false")
//    int countAllNonApproved();

}
