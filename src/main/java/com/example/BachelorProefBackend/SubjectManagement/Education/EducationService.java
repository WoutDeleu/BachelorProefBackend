package com.example.BachelorProefBackend.SubjectManagement.Education;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Campus.CampusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository){
        this.educationRepository = educationRepository;
    }

    @GetMapping
    public List<Education> getAllEducations() {return educationRepository.findAll();}

    @PostMapping
    public void addNewEducation(Education education) {
        educationRepository.save(education);
    }

    @DeleteMapping
    public void deleteEducation(long id){
        educationRepository.deleteById(id);
    }

    @PutMapping
    public void updateEducation(long id, String name){
        if(!educationRepository.existsById(id)) throw new IllegalStateException("Education does not exist (id: "+id+")");
        Education education = educationRepository.getById(id);
        if(name != null && name.length()>0) education.setName(name);

    }
}
