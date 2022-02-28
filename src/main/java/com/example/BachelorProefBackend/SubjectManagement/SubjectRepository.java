package com.example.BachelorProefBackend.SubjectManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    //Nog geen speciale methodes, dus voorlopig leeg ok
}
