package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.campus.CampusRepository;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationRepository;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import com.example.bachelorproefbackend.subjectmanagement.tag.Tag;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudienceService;
import com.example.bachelorproefbackend.usermanagement.company.Company;
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
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final TargetAudienceService targetAudienceService;
    private final FacultyRepository facultyRepository;
    private final EducationRepository educationRepository;
    private final CampusRepository campusRepository;


    @Autowired
    public SubjectService(SubjectRepository subjectRepository, CampusRepository campusRepository, EducationRepository educationRepository, FacultyRepository facultyRepository, TargetAudienceService targetAudienceService, UserService userService, RoleRepository roleRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.targetAudienceService = targetAudienceService;
        this.facultyRepository = facultyRepository;
        this.educationRepository = educationRepository;
        this.campusRepository = campusRepository;
    }




    public List<Subject> getAllSubjects() {return subjectRepository.findAll();}



    public List<Subject> getAllRelatedSubjects(Authentication authentication) {
        // TODO optimaliseerbaar?
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        List<Subject> subjects = subjectRepository.findAll();
        List<Subject> toRemove = new ArrayList<>();
        if(activeUser.getTargetAudience()==null){
            throw new RuntimeException("The user must have a targetAudience for this function.");
        }
        for (Subject s : subjects){
            if(!s.getTargetAudiences().contains(activeUser.getTargetAudience()) || s.getTargetAudiences().isEmpty()){
                toRemove.add(s);
            }
        }
        for (Subject s : toRemove){
            subjects.remove(s);
        }
        return subjects;
    }

    public Subject getSubjectById(long subject_id) {
        return subjectRepository.findById(subject_id);
    }


    public void deleteSubject(long id){
        if(!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: "+id+")");
        subjectRepository.deleteById(id);
    }


    public void addNewSubject(Subject subject, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        if(activeUser.getRoles().contains(contact)){
            //TODO Find a better solution for this
            subject.setCompany(activeUser.getFinalSubject().getCompany());
        }
        subjectRepository.save(subject);
    }


    public void updateSubject(long id, String name, String description, int nrOfStudents) {
        if (!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: " + id + ")");
        Subject subject = subjectRepository.getById(id);
        if(name != null && name.length()>0 && !Objects.equals(subject.getName(), name)) subject.setName(name);
        if(description != null && description.length()>0 && !Objects.equals(subject.getDescription(), description)) subject.setDescription(description);
        if(nrOfStudents==0) throw new RuntimeException("Number of students can not be equal to zero");
        else if (nrOfStudents>2) throw new RuntimeException("Number of students can not be over 2");
        else subject.setNrOfStudents(nrOfStudents);
    }


    public void addCompany(long subjectId, Company company, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Subject subject = subjectRepository.findById(subjectId);
        if(activeUser.getRoles().contains(admin) || company.getContacts().contains(activeUser)){
            log.info("Adding company {} to subject {}", company.getName(), subject.getName());
            if(subject.getCompany() != null){
                throw new RuntimeException("Subject already has a company: "+subject.getCompany().getName());
            }
            else{
                subject.setCompany(company);
            }
        }
    }


    public void addPromotor(long subjectId, UserEntity promotor, Authentication authentication){
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role promotorROLE = roleRepository.findByName("ROLE_PROMOTOR");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        Subject subject = subjectRepository.findById(subjectId);
        if(activeUser.getRoles().contains(admin) || activeUser.getRoles().contains(coordinator) || activeUser.getFinalSubject().equals(subject) || activeUser.equals(promotor)){
            if(promotor.getRoles().contains(promotorROLE)){
                if(subject.getPromotor() != null){
                    throw new RuntimeException("Subject already has a promotor: "+subject.getPromotor().getFirstName());
                }
                else{
                    log.info("Adding promotor {} to subject {}", promotor.getFirstName(), subject.getName());
                    subject.setPromotor(promotor);
                }
            }
            else{throw new RuntimeException("Only users with role: promotor can be added");}
        }
        else{throw new RuntimeException("Student can only add to his own final subject, Promotor can only add himself");}
    }


    public void addTag(long subjectId, Tag tag, UserEntity activeUser){
        Subject subject = subjectRepository.findById(subjectId);
        Role student = roleRepository.findByName("ROLE_STUDENT");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        if(activeUser.getRoles().contains(student) || activeUser.getRoles().contains(contact)){
            if(!subject.equals(activeUser.getFinalSubject()))
                throw new RuntimeException("Student and Contact can only add to their finalSubject.");
        }
        log.info("Adding tag {} to subject {}", tag.getName(), subject.getName());
        subject.addTag(tag);
    }


    public void addTargetAudience (long subjectId, long facultyId, long educationId, long campusId){
        Subject subject = subjectRepository.findById(subjectId);
        Faculty faculty = facultyRepository.getById(facultyId);
        List<TargetAudience> targets;
        if(facultyId==0){
            throw new RuntimeException("Faculty id cannot be equal to 0");
        }
        else if(educationId==0 && campusId==0){
            // add all the targetAudiences related to the faculty
            targets = targetAudienceService.getAllByFaculty(faculty);
        }
        else if(campusId==0){
            // add all the targetAudiences related to the education
            Education education = educationRepository.getById(educationId);
            targets = targetAudienceService.getAllByEducation(education);
        }
        else {
            // add all the targetAudiences related to the campus AND faculty
            Campus campus = campusRepository.getById(campusId);
            targets = targetAudienceService.getAllByCampus(campus);
            for(TargetAudience t : targets){
                if(!t.getFaculty().equals(faculty)){
                    targets.remove(t);
                }
            }
        }
        for(TargetAudience t : targets){
            if(targetAudienceService.exists(facultyId, educationId, campusId)){
                subject.addTargetAudience(t);
            }
            else {
                throw new RuntimeException("TargetAudience does not exist.");
            }
        }
    }

}
