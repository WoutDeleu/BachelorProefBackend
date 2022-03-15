package com.example.BachelorProefBackend.UserManagement.Company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="userManagement/company")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    //GET
    @GetMapping
    public List<Company> getAllCompanies() {return companyService.getAllCompanies();}
    @GetMapping(path="{companyId}")
    public Company getCompanyById(@PathVariable("companyId") long id) {return companyService.getCompanyById(id);}

    //POST
    @PostMapping
    public void addNewCompany(@RequestParam String name, String address, String BTWnr, String description){
        companyService.addNewCompany(new Company(name, address, BTWnr, description));
    }

    //DELETE
    @DeleteMapping(path="{companyId}")
    public void deleteCompany(@PathVariable("companyId") long id) {companyService.deleteCompany(id);}

    //PUT
    @PutMapping(path="{companyId}")
    public void updateCompany(@PathVariable("companyId") long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String address,
                              @RequestParam(required = false) String BTWnr,
                              @RequestParam(required = false) String description) {
        companyService.updateCompany(id, name, address, BTWnr, description);
    }
}
