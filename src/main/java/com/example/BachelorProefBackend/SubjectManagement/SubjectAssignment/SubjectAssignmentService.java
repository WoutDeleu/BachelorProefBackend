package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class SubjectAssignmentService {

    private final SubjectAssignmentRepository saRepository;

    @Autowired
    public SubjectAssignmentService(SubjectAssignmentRepository saRepository) {this.saRepository = saRepository;}

    //GET
    @GetMapping
    public List<SubjectAssignment> getAllSubjectAssignments() {return saRepository.findAll();}
    @GetMapping
    public List<SubjectAssignment> getSubjectAssignmentById(long saId){
        return saRepository.findAllById(Collections.singleton(saId));
    }

    //DELETE
    public void deleteSubjectAssignment(long id){
        if(!saRepository.existsById(id)) throw new IllegalStateException("SubjectAssignment does not exist (id: "+id+ ")");
        saRepository.deleteById(id);
    }

    //POST
    public void addNewSubjectAssignment(SubjectAssignment sa){
        saRepository.save(sa);
    }

    //PUT
    @Transactional
    public void updateSubjectAssignment(long id, long subjectId, long studentId, long student2Id){
        if(!saRepository.existsById(id)) throw new IllegalStateException("SubjectAssignment does not exist (id: " + id + ")");
        SubjectAssignment sa = saRepository.getById(id);
        if(subjectId != 0 && sa.getSubjectId()!=subjectId) sa.setSubjectId(subjectId);
        if(studentId != 0 && sa.getStudentId()!=studentId) sa.setStudentId(studentId);
        if(student2Id != 0 && sa.getStudent2Id()!=student2Id) sa.setStudent2Id(student2Id);
    }
}
