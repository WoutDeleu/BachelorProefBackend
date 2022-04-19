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

    @GetMapping(path="byFaculties")
    public List<Campus> getAllCampusesByFaculties(@RequestParam long [] facultyIds){
        Faculty [] faculties = new Faculty[facultyIds.length];
        for(int i = 0; i<facultyIds.length; i++){
            Faculty faculty = facultyRepository.getById(facultyIds[i]);
            faculties[i] = faculty;
        }
        return campusService.getAllCampusesByFaculties(faculties);
    }

    @GetMapping(path="byEducations")
    public List<Campus> getAllCampusesByEducations(@RequestParam long [] educationIds){
        Education [] educations = new Education[educationIds.length];
        for(int i = 0; i<educationIds.length; i++){
            Education education = educationRepository.getById(educationIds[i]);
            educations[i]= education;
        }
        return campusService.getAllCampusesByEducations(educations);
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
