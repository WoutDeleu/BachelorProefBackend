package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.FacultyRepository;
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
public class TargetAudienceService {

    private final TargetAudienceRepository targetAudienceRepository;

    @Autowired
    public TargetAudienceService(TargetAudienceRepository targetAudienceRepository){
        this.targetAudienceRepository = targetAudienceRepository;
    }

    @GetMapping
    public List<TargetAudience> getAllTargetAudiences() {return targetAudienceRepository.findAll();}

    @PostMapping
    public void addNewTargetAudience(TargetAudience targetAudience) {
        targetAudienceRepository.save(targetAudience);
    }

    @DeleteMapping
    public void deleteTargetAudience(long id){
        targetAudienceRepository.deleteById(id);
    }

    @PutMapping
    public void updateTargetAudience(long id, Campus campus, Faculty faculty){
        if(!targetAudienceRepository.existsById(id)) throw new IllegalStateException("TargetAudience does not exist (id: "+id+")");
        TargetAudience targetAudience = targetAudienceRepository.getById(id);
        if(campus != null) targetAudience.setCampus(campus);
        if(faculty != null) targetAudience.setFaculty(faculty);
    }
}
