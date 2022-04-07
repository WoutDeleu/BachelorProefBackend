package com.example.bachelorproefbackend.usermanagement.company;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.usermanagement.user.UserService;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
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

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService){
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {return companyService.getAllCompanies();}

    @GetMapping(path = "nonApproved")
    public List<Company> getAllNonApprovedCompanies() {return companyService.getAllNonApprovedCompanies();}

    @GetMapping(path="{companyId}")
    public Company getCompanyById(@PathVariable("companyId") long id) {return companyService.getCompanyById(id);}

    @GetMapping(path="{companyId}/subjects")
    public List<Subject> getCompanySubjectsById(@PathVariable("companyId") long id) {return companyService.getCompanySubjectsById(id);}

    @PostMapping
    public void addNewCompany(@RequestParam String name, String address, String btwNr, String description){
        companyService.addNewCompany(new Company(name, address, btwNr, description));
    }

    @PostMapping(path="{companyId}/addContact")
    public void addNewContact(@PathVariable("companyId") long companyId, @RequestParam long userId, Authentication authentication){
        UserEntity user = userService.getUserById(userId);
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
                              @RequestParam(required = false) String btwNr,
                              @RequestParam(required = false) String description,
                              Authentication authentication) {
        companyService.updateCompany(id, name, address, btwNr, description, authentication);
    }

    @PutMapping(path="{companyId}/approve")
    public void approveCompany(@PathVariable("companyId") long id) {
        companyService.approveCompany(id);
    }
}
