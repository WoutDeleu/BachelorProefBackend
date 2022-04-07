package com.example.bachelorproefbackend.usermanagement.company;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectService;
import com.example.bachelorproefbackend.usermanagement.role.RoleRepository;
import com.example.bachelorproefbackend.usermanagement.user.UserService;
import com.example.bachelorproefbackend.usermanagement.user.User_entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="userManagement/company")
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final SubjectService subjectService;


    @Autowired
    public CompanyController(CompanyService companyService, UserService userService, RoleRepository roleRepository, SubjectService subjectService){
        this.companyService = companyService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {return companyService.getAllCompanies();}

    @GetMapping(path="{companyId}")
    public Company getCompanyById(@PathVariable("companyId") long id) {return companyService.getCompanyById(id);}

    @GetMapping(path="{companyId}/subjects")
    public List<Subject> getCompanySubjectsById(@PathVariable("companyId") long id) {return companyService.getCompanySubjectsById(id);}

    @PostMapping
    public void addNewCompany(@RequestParam String name, String address, String BTWnr, String description){
        companyService.addNewCompany(new Company(name, address, BTWnr, description));
    }

    @PostMapping(path="{companyId}/addContact")
    public void addNewContact(@PathVariable("companyId") long companyId, @RequestParam long userId, Authentication authentication){
        User_entity user = userService.getUserById(userId);
        companyService.addNewContact(companyId, user, authentication);
    }

    @DeleteMapping(path="{companyId}")
    public void deleteCompany(@PathVariable("companyId") long id, Authentication authentication) {
        companyService.deleteCompany(id, authentication);
    }

    @PutMapping(path="{companyId}")
    public void updateCompany(@PathVariable("companyId") long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String address,
                              @RequestParam(required = false) String BTWnr,
                              @RequestParam(required = false) String description,
                              Authentication authentication) {
        companyService.updateCompany(id, name, address, BTWnr, description, authentication);
    }

    @PutMapping(path="{companyId}/approve")
    public void approveCompany(@PathVariable("companyId") long id) {
        companyService.approveCompany(id);
    }
}
