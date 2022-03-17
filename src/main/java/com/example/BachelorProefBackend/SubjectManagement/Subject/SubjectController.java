package com.example.BachelorProefBackend.SubjectManagement.Subject;

import com.example.BachelorProefBackend.UserManagement.Company.Company;
import com.example.BachelorProefBackend.UserManagement.Company.CompanyService;
import com.example.BachelorProefBackend.UserManagement.User.User_entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="subjectManagement/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final CompanyService companyService;

    @Autowired
    public SubjectController(SubjectService subjectService, CompanyService companyService) {
        this.subjectService = subjectService;
        this.companyService = companyService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {return subjectService.getAllSubjects();}

    @GetMapping(path="{subjectId}")
    public Subject getSubjectById(@PathVariable("subjectId") Long subject_id){
        return subjectService.getSubjectById(subject_id);
    }
//    @GetMapping(path="{subjectId}/students")
//    public List<User_entity> getAllStudents(@PathVariable("subjectId") long id){return subjectService.getAllUsers(id);}


    @PostMapping
    public void addNewSubject(@RequestParam String name, String description, int nrOfStudents, Authentication authentication) {
        subjectService.addNewSubject(new Subject(name, description, nrOfStudents), authentication);
    }

    @DeleteMapping(path="{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") long id) {subjectService.deleteSubject(id);}

    @PutMapping(path="{subjectId}")
    public void updateSubject(@PathVariable("subjectId") long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) int nrOfStudents) {
        subjectService.updateSubject(id, name, description, nrOfStudents);
    }

    @PutMapping(path="{subjectId}/addCompany")
    public void addCompany(@PathVariable("subjectId") long subjectId, @RequestParam long companyId, Authentication authentication){
        Company company = companyService.getCompanyById(companyId);
        subjectService.addCompany(subjectId, company, authentication);
    }

}
