package com.example.bachelorproefbackend.subjectmanagement.campus;

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
@RequestMapping(path="subjectManagement/campus")
public class CampusController {
    private final CampusService campusService;
    private final FacultyRepository facultyRepository;
    private final EducationRepository educationRepository;

    @Autowired
    public CampusController (CampusService campusService, EducationRepository educationRepository, FacultyRepository facultyRepository){
        this.campusService = campusService;
        this.facultyRepository = facultyRepository;
        this.educationRepository = educationRepository;
    }

    @GetMapping
    public List<Campus> getAllCampuses() {return campusService.getAllCampuses();}

    @GetMapping(path="byFaculty/{facultyId}")
    public List<Campus> getAllCampusesByFaculty(@PathVariable ("facultyId") long id){
        Faculty faculty = facultyRepository.getById(id);
        return campusService.getAllCampusesByFaculty(faculty);
    }

    @GetMapping(path="byEducation/{educationId}")
    public List<Campus> getAllCampusesByEducation(@PathVariable ("educationId") long id){
        Education education = educationRepository.getById(id);
        return campusService.getAllCampusesByEducation(education);
    }

    @PostMapping
    public void addNewCampus(@RequestParam String name, String address){
        campusService.addNewCampus(new Campus(name, address));
    }

    @DeleteMapping(path="{campusId}")
    public void deleteCampus(@PathVariable("campusId") long id){
        campusService.deleteCampus(id);
    }

    @PutMapping(path="{campusId}")
    public void updateCampus(@PathVariable("campusId") long id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String address){
        campusService.updateCampus(id, name, address);
    }

}
