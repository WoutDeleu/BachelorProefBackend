package com.example.BachelorProefBackend.UserManagement.Company;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findById(long id);


}
