package com.example.BachelorProefBackend.UserManagement.Company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies() {return companyRepository.findAll();}
    @GetMapping
    public Company getCompanyById(long id) {return companyRepository.findById(id);}

    @PostMapping
    public void addNewCompany(Company company) {
        log.info("Saving new company {} to the database", company.getName());
        companyRepository.save(company);
    }

    @DeleteMapping
    public void deleteCompany(long id) {
        if(!companyRepository.existsById(id)) throw new IllegalStateException("Company does not exist (id: " +id+ ")");
        companyRepository.deleteById(id);
    }

    @PutMapping
    @Transactional
    public void updateCompany(long id, String name, String address, String BTWnr, String description) {
        if(!companyRepository.existsById(id)) throw new IllegalStateException("Company does not exist (id: " + id + ")");
        Company company = companyRepository.getById(id);
        if(name != null && name.length()>0) company.setName(name);
        if(address != null && address.length()>0) company.setAddress(address);
        if(BTWnr != null && BTWnr.length()>0) company.setBTWnr(BTWnr);
        if(description != null && description.length()>0) company.setDescription(description);
    }


}
