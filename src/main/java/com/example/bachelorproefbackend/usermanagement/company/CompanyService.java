package com.example.bachelorproefbackend.usermanagement.company;


import com.example.bachelorproefbackend.configuration.email.EmailService;
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


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;
    private final EmailService emailService;
    private static final String ADMIN = "ROLE_ADMIN";


    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmailService emailService, UserService userService, RoleRepository roleRepository, SubjectRepository subjectRepository){
        this.companyRepository = companyRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
        this.emailService = emailService;
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

    public long addNewCompany(Company company) {
        log.info("Saving new company {} to the database", company.getName());
        companyRepository.save(company);
        return company.getId();
    }

    public void addNewContact(long id, UserEntity [] contacts, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName(ADMIN);
        Role contactROLE = roleRepository.findByName("ROLE_CONTACT");
        Company company = companyRepository.getById(id);
        boolean newCompany = company.getContacts().isEmpty();
        if(activeUser.getRoles().contains(admin) || company.getContacts().contains(activeUser) || newCompany){
            for (UserEntity user: contacts){
                if(user.getRoles().contains(contactROLE)){
                    log.info("Adding new contact {} to company {}", user.getFirstName(), company.getName());
                    //company.addContact(user);
                    user.setCompany(company);
                    if(company.isApproved()==false && newCompany){
                        String to = user.getEmail();
                        String subject = "New company registered";
                        String body = "Dear "+user.getFirstName()+", \n\n\n"+
                                "Thank you for registering your company "+company.getName()+" to our university.\n"+
                                "Your company information has been well received. We will take a look at it and approve you to use our mastertool asap.\n"+
                                "Once this step is completed, you will be able to add subjects for our students all over the country.\n\n"+
                                "For any questions you can contact admin@kuleuven.be.\n\n\n"+
                                "Kind regards\n"+
                                "The mastertool team";
                        //TODO enable
//                        emailService.sendEmail(to,subject,body);
                    }
                }
                else{
                    throw new InputNotValidException("Only users with contact role can be added.");
                }
            }
        }
        else {
            throw new NotAllowedException("Only contacts can add contacts to their own company");
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
        if (!companyRepository.existsById(id))
            throw new InputNotValidException("Company does not exist (id: " + id + ")");
        Company company = companyRepository.getById(id);
        company.setApproved(approved);
        ArrayList<UserEntity> contact = new ArrayList<>(company.getContacts());
        for (UserEntity user : contact) {
            String to = user.getEmail();
            String subject = "Company approved";
            String body = "Dear " + user.getFirstName() + ", \n\n\n" +
                    "Your company " + company.getName() + " has officially been approved.\n" +
                    "Your can now create subjects in our mastertool.\n\n\n" +
                    "Kind regards\n" +
                    "The mastertool team";
            emailService.sendEmail(to, subject, body);
        }
    }

}
