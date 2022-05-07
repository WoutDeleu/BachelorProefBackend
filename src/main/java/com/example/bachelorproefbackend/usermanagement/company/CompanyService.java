package com.example.bachelorproefbackend.usermanagement.company;

import com.example.bachelorproefbackend.configuration.exceptions.InputNotValidException;
import com.example.bachelorproefbackend.configuration.exceptions.NotAllowedException;
import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectRepository;
import com.example.bachelorproefbackend.usermanagement.role.Role;
import com.example.bachelorproefbackend.usermanagement.role.RoleRepository;
import com.example.bachelorproefbackend.usermanagement.user.UserService;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;
    private static final String ADMIN = "ROLE_ADMIN";


    @Autowired
    public CompanyService(CompanyRepository companyRepository, UserService userService, RoleRepository roleRepository, SubjectRepository subjectRepository){
        this.companyRepository = companyRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Company> getAllCompanies(Authentication authentication) {
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        if(activeUser.getRoles().contains(admin)){
            return companyRepository.findAll();
        }
        else {
            return companyRepository.findAllApproved();
        }
    }

    public List<Company> getAllNonApprovedCompanies() {return companyRepository.findAllNonApproved();}

    public List<Company> getAllApprovedCompanies() {return companyRepository.findAllApproved();}

    public Company getCompanyById(long id) {return companyRepository.findById(id);}

    public List<Subject> getCompanySubjectsById(long id) {return subjectRepository.findAllByCompany_Id(id);}

    public void addNewCompany(Company company) {
        log.info("Saving new company {} to the database", company.getName());
        companyRepository.save(company);
    }

    public void addNewContact(long id, UserEntity user, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        Company company = companyRepository.getById(id);
        if(activeUser.getRoles().contains(admin) || company.getContacts().contains(activeUser) || company.getContacts().isEmpty()){
            if(user.getRoles().contains(contact)){
                log.info("Adding new contact {} to company {}", user.getFirstName(), company.getName());
                company.addContact(user);
            }
            else{
                throw new InputNotValidException("Only users with contact role can be added.");
            }
        }
        else {
            throw new NotAllowedException("Only contacts can add contacts to their own company");
        }

    }

    public void addNewSubject(long id, Subject subject, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        Company company = companyRepository.getById(id);
        if(activeUser.getRoles().contains(admin) || company.getContacts().contains(activeUser)){
            log.info("Adding new subject {} to company {}", subject.getName(), company.getName());
            company.addSubject(subject);
        }
        else {
            throw new NotAllowedException("Only contacts can add subjects to their own company");
        }
    }

    public void deleteCompany(long id, Authentication authentication) {
        if(!companyRepository.existsById(id)) throw new IllegalStateException("Company does not exist (id: " +id+ ")");
        // Only admin and companies have access to the URL
        UserEntity user = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        Company company = companyRepository.findById(id);
        if(user.getRoles().contains(admin) || company.getContacts().contains(user))
            companyRepository.deleteById(id);
        else
            throw new RuntimeException("Companies can only removed by their own contacts.");

    }

    public void updateCompany(long id, String name, String address, String btwNr, String description, Authentication authentication) {
        if(!companyRepository.existsById(id)) throw new IllegalStateException("Company does not exist (id: " + id + ")");
        // Only admin and companies have access to the URL
        UserEntity user = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        Company company = companyRepository.findById(id);
        if(user.getRoles().contains(admin) || company.getContacts().contains(user)){
            if(name != null && name.length()>0) company.setName(name);
            if(address != null && address.length()>0) company.setAddress(address);
            if(btwNr != null && btwNr.length()>0) company.setBtwNr(btwNr);
            if(description != null && description.length()>0) company.setDescription(description);
        }
        else {
            throw new RuntimeException("Companies can only removed by their own contacts.");
        }

    }

    public void setApproved(long id, boolean approved) {
        if(!companyRepository.existsById(id)) throw new InputNotValidException("Company does not exist (id: " + id + ")");
        Company company = companyRepository.getById(id);
        company.setApproved(approved);
    }


}
