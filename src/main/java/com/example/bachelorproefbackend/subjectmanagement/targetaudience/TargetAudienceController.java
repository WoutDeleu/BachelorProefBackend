package com.example.bachelorproefbackend.subjectmanagement.targetaudience;

import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.campus.CampusRepository;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationRepository;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/targetAudience")
public class TargetAudienceController {

    private final TargetAudienceService targetAudienceService;
    private final CampusRepository campusRepository;
    private final FacultyRepository facultyRepository;
    private final EducationRepository educationRepository;

    @Autowired
    public TargetAudienceController (TargetAudienceService targetAudienceService, CampusRepository campusRepository, FacultyRepository facultyRepository, EducationRepository educationRepository){
        this.targetAudienceService = targetAudienceService;
        this.campusRepository = campusRepository;
        this.facultyRepository = facultyRepository;
        this.educationRepository = educationRepository;
    }

    @GetMapping
    public List<TargetAudience> getAllTargetAudiences() {return targetAudienceService.getAllTargetAudiences();}

    @PostMapping
    public void addNewTargetAudience(@RequestParam long campusId, long facultyId, long educationId){
        Campus campus = campusRepository.getById(campusId);
        Faculty faculty = facultyRepository.getById(facultyId);
        Education education = educationRepository.getById(educationId);
        targetAudienceService.addNewTargetAudience(new TargetAudience(campus, faculty, education));
    }

    @DeleteMapping(path="{targetAudienceId}")
    public void deleteTargetAudience(@PathVariable("targetAudienceId") long id){
        targetAudienceService.deleteTargetAudience(id);
    }

    @PutMapping(path="{targetAudienceId}")
    public void updateTargetAudience(@PathVariable("targetAudienceId") long id,
                             @RequestParam(required = false) long campusId,
                             @RequestParam(required = false) long facultyId){
        Campus campus = campusRepository.getById(campusId);
        Faculty faculty = facultyRepository.getById(facultyId);
        targetAudienceService.updateTargetAudience(id, campus, faculty);
    }
}
