package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Campus.CampusRepository;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.FacultyRepository;
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

    @Autowired
    public TargetAudienceController (TargetAudienceService targetAudienceService, CampusRepository campusRepository, FacultyRepository facultyRepository){
        this.targetAudienceService = targetAudienceService;
        this.campusRepository = campusRepository;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public List<TargetAudience> getAllTargetAudiences() {return targetAudienceService.getAllTargetAudiences();}

    @PostMapping
    public void addNewTargetAudience(@RequestParam long campusId, long facultyId){
        Campus campus = campusRepository.getById(campusId);
        Faculty faculty = facultyRepository.getById(facultyId);
        targetAudienceService.addNewTargetAudience(new TargetAudience(campus, faculty));
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
