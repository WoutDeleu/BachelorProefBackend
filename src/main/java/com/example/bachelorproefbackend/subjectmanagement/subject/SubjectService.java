package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.configuration.exceptions.InputNotValidException;
import com.example.bachelorproefbackend.configuration.exceptions.NotAllowedException;
import com.example.bachelorproefbackend.configuration.timing.Timing;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationRepository;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreference;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreferenceRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final EducationRepository educationRepository;
    private final SubjectPreferenceRepository subjectPreferenceRepository;
    private final TargetAudienceService targetAudienceService;
    private final FacultyRepository facultyRepository;


    @Autowired
    public SubjectService(SubjectRepository subjectRepository, SubjectPreferenceRepository subjectPreferenceRepository, EducationRepository educationRepository, FacultyRepository facultyRepository, TargetAudienceService targetAudienceService, UserService userService, RoleRepository roleRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.targetAudienceService = targetAudienceService;
        this.subjectPreferenceRepository = subjectPreferenceRepository;
        this.educationRepository = educationRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<Subject> getAllSubjects(Authentication authentication) {
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        Role student = roleRepository.findByName("ROLE_STUDENT");
        if(activeUser.getRoles().contains(admin) || activeUser.getRoles().contains(coordinator)){
            return subjectRepository.findAll();
        }
        else {
            if(activeUser.getRoles().contains(student) && activeUser.getTargetAudience()!=null)
                return subjectRepository.findAllByTargetAudiencesContainsAndAndApproved(activeUser.getTargetAudience(), true);
            else
                return subjectRepository.findAllByApproved(true);
        }
    }

    public Collection<SubjectPreference> getPreferredStudents(long subjectId) {
        return subjectPreferenceRepository.findBySubjectId(subjectId);
    }

    public List<Subject> getMySubjects(Authentication authentication) {
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role promotor = roleRepository.findByName("ROLE_PROMOTOR");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        if(activeUser.getRoles().contains(promotor)){
            return subjectRepository.findAllByPromotor(activeUser);
        }
        else if(activeUser.getRoles().contains(contact)){
            if (activeUser.getCompany() == null) return null;
            return subjectRepository.findAllByCompany(activeUser.getCompany());
        }
        else {
            return subjectRepository.findAll();
        }
    }

    public List<Subject> getAllNonApprovedSubjects() {return subjectRepository.findAllByApproved(false);}

    public Subject getSubjectById(long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public SubjectData getSubjectData() {
        SubjectData result = new SubjectData();
        result.setTotalAmount((int) subjectRepository.count());
        result.setNrOfNonApproved(subjectRepository.countSubjectsByApproved(false));
        return result;
    }

    public void deleteSubject(long subjectId){
        if(!subjectRepository.existsById(subjectId)) throw new IllegalStateException("Subject does not exist (id: "+subjectId+")");
        Subject subject = subjectRepository.findById(subjectId);
        subject.setCompany(null);
        subject.setPromotor(null);
        subjectRepository.deleteById(subjectId);
    }


    public long addNewSubject(Subject subject, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        Role student = roleRepository.findByName("ROLE_STUDENT");
        if(activeUser.getRoles().contains(contact)){
            subject.setCompany(activeUser.getCompany());
        }
        if(activeUser.getRoles().contains(student)){
            subject.addTargetAudience(activeUser.getTargetAudience());
        }
        subjectRepository.save(subject);
        return subject.getId();
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
        Subject subject = subjectRepository.getById(subjectId);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        // Only coordinator and admin can access all subjects, others only their own
        if(activeUser.getRoles().contains(admin) // admin
                || activeUser.getRoles().contains(coordinator) // coordinator
                || activeUser.getFinalSubject().equals(subject) // student
                || activeUser.getSubjects().contains(subject) //promotor
                || activeUser.getCompany().equals(subject.getCompany()) // contact
        ) {
            log.info("Adding company {} to subject {}", company.getName(), subject.getName());
            if (subject.getCompany() != null) {
                throw new InputNotValidException("Subject already has a company: " + subject.getCompany().getName());
            } else {
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

    public void addTargetAudience (long subjectId, int [] facultyIds, int [] educationIds, int [] campusIds, Authentication authentication){
        if(!Timing.getInstance().isBeforeDeadlineAddingSubjects()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndAddingSubjects());
        }
        UserEntity activeUser = userService.getUserByEmail(authentication.getName());
        Subject subject = subjectRepository.getById(subjectId);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role student = roleRepository.findByName("ROLE_STUDENT");
        Role promotor = roleRepository.findByName("ROLE_PROMOTOR");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        // Only coordinator and admin can access all subjects, others only their own
        boolean access = false;
        if(activeUser.getRoles().contains(admin) || activeUser.getRoles().contains(coordinator)) access = true;// admin
        if(activeUser.getRoles().contains(student) && activeUser.getFinalSubject() != null){
            if (activeUser.getFinalSubject().equals(subject)) access = true;
        }
        if(activeUser.getRoles().contains(promotor) && activeUser.getSubjects() != null){
            if(activeUser.getSubjects().contains(subject)) access = true;
        }
        if(activeUser.getRoles().contains(contact) && activeUser.getCompany() != null && subject.getCompany() != null){
            if(activeUser.getCompany().equals(subject.getCompany())) access = true;
        }
        if(access){
            List<TargetAudience> targets = new ArrayList<>();
            if(facultyIds[0]==0) {
                throw new InputNotValidException("Faculty id cannot be equal to 0");
            }
            else if(educationIds[0]==0 && campusIds[0]==0){
                log.info("add all the targetAudiences related to the faculties");
                // add all the targetAudiences related to the faculties
                for (long fid : facultyIds){
                    targets = targetAudienceService.getAllByFacultyId(fid);
                }
            }
            else if(campusIds[0]==0){
                log.info("add all the targetAudiences related to the education");
                // add all the targetAudiences related to the education
                for (long eid : educationIds){
                    targets = targetAudienceService.getAllByEducationId(eid);
                }
            }
            else {
                log.info("add all the targetAudiences related to the campus AND faculty AND education");
                for (long cid : campusIds) {
                    targets = targetAudienceService.getAllByCampusId(cid);
                    ArrayList<Faculty> faculties = new ArrayList<>();
                    ArrayList<Education> educations = new ArrayList<>();
                    ArrayList<TargetAudience> toRemove = new ArrayList<>();
                    for (long fid : facultyIds) {
                        Faculty faculty = facultyRepository.getById(fid);
                        faculties.add(faculty);
                    }
                    for (long eid : educationIds) {
                        Education education = educationRepository.getById(eid);
                        educations.add(education);
                    }

                    for (TargetAudience t : targets) {
                        if (!faculties.contains(t.getFaculty()) || !educations.contains(t.getEducation())) {
                            toRemove.add(t);
                        }
                    }
                    for (TargetAudience t : toRemove) {
                        targets.remove(t);
                    }
                }
            }
            subject.clearTargetAudience();
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
