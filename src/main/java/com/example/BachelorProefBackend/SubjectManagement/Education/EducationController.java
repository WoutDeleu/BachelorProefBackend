package com.example.BachelorProefBackend.SubjectManagement.Education;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Campus.CampusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/education")
public class EducationController {
    private final EducationService educationService;

    @Autowired
    public EducationController (EducationService educationService){
        this.educationService = educationService;
    }

    @GetMapping
    public List<Education> getAllEducations() {return educationService.getAllEducations();}

    @PostMapping
    public void addNewEducation(@RequestParam String name){
        educationService.addNewEducation(new Education(name));
    }

    @DeleteMapping(path="{educationId}")
    public void deleteEducation(@PathVariable("educationId") long id){
        educationService.deleteEducation(id);
    }

    @PutMapping(path="{educationId}")
    public void updateCampus(@PathVariable("educationId") long id,
                             @RequestParam(required = false) String name){
        educationService.updateEducation(id, name);
    }
}
