package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.SubjectManagement.Subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectAssignmentService {

    private final SubjectAssignmentRepository saRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectAssignmentService(SubjectAssignmentRepository saRepository, SubjectRepository subjectRepository) {
        this.saRepository = saRepository;
        this.subjectRepository = subjectRepository;
    }

    //GET
    @GetMapping
    public List<SubjectAssignment> getAllSubjectAssignments() {return saRepository.findAll();}
    @GetMapping
    public List<SubjectAssignment> getSubjectAssignmentById(long saId){
        return saRepository.findAllById(Collections.singleton(saId));
    }
    @GetMapping
    public List<Subject> getSubjectByUserId(long id){
        List<SubjectAssignment> list1 = saRepository.findAllByStudentId(id);
        List<SubjectAssignment> list2 = saRepository.findAllByStudent2Id(id);
        List<SubjectAssignment> list = list1;
        list.addAll(list2);

        List<Subject> result = new ArrayList<Subject>();
        for (SubjectAssignment sa : list){
            if(!subjectRepository.existsById(sa.getSubjectId())) throw new IllegalStateException("Subject does not exist (id: " +id+ ")");
            else {
                Subject subject = subjectRepository.findById(sa.getSubjectId());
                result.add(subject);
            }
        }
        return result;

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
