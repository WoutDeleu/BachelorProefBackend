package com.example.BachelorProefBackend.SubjectManagement.Subject;

import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="subjectManagement/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {this.subjectService = subjectService;}

    //GET
    @GetMapping
    public List<Subject> getAllSubjects() {return subjectService.getAllSubjects();}
    @GetMapping(path="{subjectId}")
    public Subject getSubjectById(@PathVariable("subjectId") Long subject_id){
        return subjectService.getSubjectById(subject_id);
    }
    @GetMapping(path="{subjectId}/students")
    public List<User_entity> getAllStudents(@PathVariable("subjectId") long id){return subjectService.getAllUsers(id);}

    //POST
    @PostMapping
    public void addNewSubject(@RequestParam String name, String description, int nrOfStudents) {
        subjectService.addNewSubject(new Subject(name, description, nrOfStudents));
    }

    //DELETE
    @DeleteMapping(path="{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") long id) {subjectService.deleteSubject(id);}

    //PUT
    @PutMapping(path="{subjectId}")
    public void updateSubject(@PathVariable("subjectId") long id, @RequestParam(required = false) String name, @RequestParam(required = false) String description) {
        subjectService.updateSubject(id, name, description);
    }

}
