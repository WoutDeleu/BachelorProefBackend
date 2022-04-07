package com.example.bachelorproefbackend.subjectmanagement.education;

import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/education")
public class EducationController {
    private final EducationService educationService;
    private final FacultyRepository facultyRepository;

    @Autowired
    public EducationController (EducationService educationService, FacultyRepository facultyRepository){
        this.educationService = educationService;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public List<Education> getAllEducations() {return educationService.getAllEducations();}

    @GetMapping(path="byFaculty/{facultyId}")
    public List<Education> getAllEducationsByFaculty(@PathVariable("facultyId") long id) {
        Faculty faculty = facultyRepository.getById(id);
        return educationService.getAllEducationsByFaculty(faculty);
    }

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
