package com.example.bachelorproefbackend.subjectmanagement.education;


import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EducationService {

    private final EducationRepository educationRepository;
    private final TargetAudienceService targetAudienceService;

    @Autowired
    public EducationService(EducationRepository educationRepository, TargetAudienceService targetAudienceService){
        this.educationRepository = educationRepository;
        this.targetAudienceService = targetAudienceService;
    }

    @GetMapping
    public List<Education> getAllEducations() {return educationRepository.findAll();}

    @GetMapping
    public List<Education> getAllEducationsByFaculties(Faculty [] faculties){
        List<Education> result = new ArrayList<>();
        for (Faculty f : faculties){
            List<TargetAudience> targets = targetAudienceService.getAllByFaculty(f);
            for (TargetAudience t : targets) {
                result.add(t.getEducation());
            }
        }
        return result;
    }

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
