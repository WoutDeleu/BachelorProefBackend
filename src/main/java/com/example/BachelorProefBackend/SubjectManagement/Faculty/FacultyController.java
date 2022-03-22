package com.example.BachelorProefBackend.SubjectManagement.Faculty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController (FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<Faculty> getAllFaculties() {return facultyService.getAllFaculties();}

    @PostMapping
    public void addNewFaculty(@RequestParam String name){
        facultyService.addNewFaculty(new Faculty(name));
    }

    @DeleteMapping(path="{facultyId}")
    public void deleteFaculty(@PathVariable("facultyId") long id){
        facultyService.deleteFaculty(id);
    }

    @PutMapping(path="{facultyId}")
    public void updateFaculty(@PathVariable("facultyId") long id,
                             @RequestParam(required = false) String name){
        facultyService.updateFaculty(id, name);
    }

}

