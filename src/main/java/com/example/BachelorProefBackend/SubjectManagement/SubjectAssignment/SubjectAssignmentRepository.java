package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment, Long> {
    List<SubjectAssignment> findAllByStudentId(long id);
    List<SubjectAssignment> findAllByStudent2Id(long id);


}
