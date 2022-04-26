package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.configuration.exceptions.InputNotValidException;
import com.example.bachelorproefbackend.configuration.exceptions.NotAllowedException;
import com.example.bachelorproefbackend.configuration.timing.Timing;
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


    @Autowired
    public SubjectService(SubjectRepository subjectRepository, FacultyRepository facultyRepository, TargetAudienceService targetAudienceService, UserService userService, RoleRepository roleRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.targetAudienceService = targetAudienceService;
        this.facultyRepository = facultyRepository;
    }

    public List<Subject> getAllSubjects() {return subjectRepository.findAll();}

    public List<Subject> getAllNonApprovedSubjects() {return subjectRepository.findAllByApproved(false);}

    public List<Subject> getAllRelatedSubjects(Authentication authentication) {
        // TODO optimaliseerbaar?
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        List<Subject> subjects = subjectRepository.findAll();
        List<Subject> toRemove = new ArrayList<>();
        if(activeUser.getTargetAudience()==null){
            throw new InputNotValidException("The user must have a targetAudience for this function.");
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

    public Subject getSubjectById(long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public SubjectData getSubjectData() {
        SubjectData result = new SubjectData();
        result.setTotalAmount((int) subjectRepository.count());
        result.setNrOfNonApproved(subjectRepository.countSubjectsByApproved(false));
        return result;
    }

    public void deleteSubject(long id){
        if(!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: "+id+")");
        subjectRepository.deleteById(id);
    }


    public void addNewSubject(Subject subject, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        if(activeUser.getRoles().contains(contact)){
            subject.setCompany(activeUser.getCompany());
        }
        subjectRepository.save(subject);
    }


    public void updateSubject(long id, String name, String description, int nrOfStudents) {
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        if (!subjectRepository.existsById(id)) throw new IllegalStateException("Subject does not exist (id: " + id + ")");
        Subject subject = subjectRepository.getById(id);
        if(name != null && name.length()>0 && !Objects.equals(subject.getName(), name)) subject.setName(name);
        if(description != null && description.length()>0 && !Objects.equals(subject.getDescription(), description)) subject.setDescription(description);
        if(nrOfStudents==0) throw new InputNotValidException("Number of students can not be equal to zero");
        else if (nrOfStudents>2) throw new InputNotValidException("Number of students can not be over 2");
        else subject.setNrOfStudents(nrOfStudents);
    }


    public void addCompany(long subjectId, Company company, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Subject subject = subjectRepository.findById(subjectId);
        if(activeUser.getRoles().contains(admin) || company.getContacts().contains(activeUser)){
            log.info("Adding company {} to subject {}", company.getName(), subject.getName());
            if(subject.getCompany() != null){
                throw new InputNotValidException("Subject already has a company: "+subject.getCompany().getName());
            }
            else{
                subject.setCompany(company);
            }
        }
    }

    public void addPromotor(long subjectId, UserEntity promotor, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role promotorROLE = roleRepository.findByName("ROLE_PROMOTOR");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        Subject subject = subjectRepository.findById(subjectId);
        if(activeUser.getRoles().contains(admin) || activeUser.getRoles().contains(coordinator) || activeUser.getFinalSubject().equals(subject) || activeUser.equals(promotor)){
            if(promotor.getRoles().contains(promotorROLE)){
                if(subject.getPromotor() != null){
                    throw new InputNotValidException("Subject already has a promotor: "+subject.getPromotor().getFirstName());
                }
                else{
                    log.info("Adding promotor {} to subject {}", promotor.getFirstName(), subject.getName());
                    subject.setPromotor(promotor);
                }
            }
            else{throw new InputNotValidException("Only users with role: promotor can be added");}
        }
        else{throw new NotAllowedException("Student can only add to his own final subject, Promotor can only add himself");}
    }


    public void addTag(long subjectId, Tag [] tags, UserEntity activeUser){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        Subject subject = subjectRepository.findById(subjectId);
        Role student = roleRepository.findByName("ROLE_STUDENT");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        if((activeUser.getRoles().contains(student) || activeUser.getRoles().contains(contact)) && !subject.equals(activeUser.getFinalSubject())){
            throw new NotAllowedException("Student and Contact can only add to their finalSubject.");
        }
        for (Tag tag : tags){
            log.info("Adding tag {} to subject {}", tag.getName(), subject.getName());
            subject.addTag(tag);
        }

    }


    public void addTargetAudience (long subjectId, long [] facultyIds, long [] educationIds, long [] campusIds, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Subject subject = subjectRepository.getById(subjectId);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        // Only coordinator and admin can access all subjects, others only their own
        if(activeUser.getRoles().contains(admin) // admin
                || activeUser.getRoles().contains(coordinator) // coordinator
                || activeUser.getFinalSubject().equals(subject) // student
                || activeUser.getSubjects().contains(subject) //promotor
                || activeUser.getCompany().equals(subject.getCompany()) // contact
         ){
            List<TargetAudience> targets = new ArrayList<>();
            if(facultyIds[0]==0) {
                throw new InputNotValidException("Faculty id cannot be equal to 0");
            }
            else if(educationIds[0]==0 && campusIds[0]==0){
                // add all the targetAudiences related to the faculties
                for (long fid : facultyIds){
                    targets = targetAudienceService.getAllByFacultyId(fid);
                }
            }
            else if(campusIds[0]==0){
                // add all the targetAudiences related to the education
                for (long eid : educationIds){
                    targets = targetAudienceService.getAllByEducationId(eid);
                }
            }
            else {
                // add all the targetAudiences related to the campus AND faculty
                for (long cid : campusIds) {
                    targets = targetAudienceService.getAllByCampusId(cid);
                    ArrayList<Faculty> faculties = new ArrayList<>();
                    for (long fid : facultyIds) {
                        Faculty faculty = facultyRepository.getById(fid);
                        faculties.add(faculty);
                    }
                    for (TargetAudience t : targets) {
                        if (!faculties.contains(t.getFaculty())) {
                            targets.remove(t);
                        }
                    }
                }
            }
            for (TargetAudience t : targets) {
                subject.addTargetAudience(t);
            }
        }
        else {
            throw new NotAllowedException("Student, promotor and contact can only add to their own subject.");
        }
    }

    public void setApproved(long id, boolean approved){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        if(!subjectRepository.existsById(id)) throw new InputNotValidException("Subject does not exist (id: " + id + ")");
        Subject subject = subjectRepository.getById(id);
        subject.setApproved(approved);
    }


}
