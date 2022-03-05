package com.example.BachelorProefBackend.SubjectManagement.Subject;

import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {this.subjectRepository = subjectRepository;}

    //GET
    @GetMapping
    public List<Subject> getAllSubjects() {return subjectRepository.findAll();}

    @GetMapping
    public Subject getSubjectById(long subject_id) {
        return subjectRepository.findById(subject_id);
    }
    @GetMapping
    public List<User_entity> getAllUsers(long id){
        if(subjectRepository.existsById(id))
            return new ArrayList<User_entity>(subjectRepository.findById(id).getStudents());
        else throw new RuntimeException("Subject not found");
    }

    //DELETE
    public void deleteSubject(long id){
        if(!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: "+id+")");
        subjectRepository.deleteById(id);
    }

    //POST
    public void addNewSubject(Subject subject){subjectRepository.save(subject);}

    //PUT
    @Transactional
    public void updateSubject(long id, String name, String description) {
        if (!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: " + id + ")");
        Subject subject = subjectRepository.getById(id);
        if(name != null && name.length()>0 && !Objects.equals(subject.getName(), name)) subject.setName(name);
        if(description != null && description.length()>0 && !Objects.equals(subject.getDescription(), description)) subject.setDescription(description);
    }
}
