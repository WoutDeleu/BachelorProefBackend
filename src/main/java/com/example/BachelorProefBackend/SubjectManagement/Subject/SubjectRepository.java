package com.example.BachelorProefBackend.SubjectManagement.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    //Nog geen speciale methodes, dus voorlopig leeg ok
    Subject findById(long id);

    List<Subject> findAllByCompany_Id(long id);

}
