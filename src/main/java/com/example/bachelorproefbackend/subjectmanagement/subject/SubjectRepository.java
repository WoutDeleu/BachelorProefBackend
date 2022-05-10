package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findById(long id);

    List<Subject> findAllByCompany_Id(long id);

    List<Subject> findAllByApproved(boolean bool);

    List<Subject> findAllByPromotor(UserEntity promotor);

    List<Subject> findAllByCompany(Company company);

    List<Subject> findAllByTargetAudiencesContains(TargetAudience targetAudience);

    List<Subject> findAllByTargetAudiencesContainsAndAndApproved(TargetAudience targetAudience, boolean approved);

    int countSubjectsByApproved(boolean bool);


}
