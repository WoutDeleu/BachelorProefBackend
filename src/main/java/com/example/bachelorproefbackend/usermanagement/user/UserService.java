package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.configuration.exceptions.InputNotValidException;
import com.example.bachelorproefbackend.configuration.exceptions.NotAllowedException;
import com.example.bachelorproefbackend.configuration.exceptions.ResourceNotFoundException;
import com.example.bachelorproefbackend.configuration.timing.Timing;
import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.campus.CampusRepository;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationRepository;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreference;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreferenceRepository;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudienceService;
import com.example.bachelorproefbackend.usermanagement.company.CompanyRepository;
import com.example.bachelorproefbackend.usermanagement.role.Role;
import com.example.bachelorproefbackend.usermanagement.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.util.*;

@Service //Specifieke versie van @Component: deze klasse wordt gebruikt als Bean
@Transactional
@Slf4j //logging
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FacultyRepository facultyRepository;
    private final EducationRepository educationRepository;
    private final CampusRepository campusRepository;
    private final SubjectPreferenceRepository subjectPreferenceRepository;
    private final PasswordEncoder passwordEncoder;
    private final TargetAudienceService targetAudienceService;
    private final CompanyRepository companyRepository;
    private static final String STUDENT = "ROLE_STUDENT";


    @Autowired
    public UserService(UserRepository userRepository, SubjectPreferenceRepository subjectPreferenceRepository, FacultyRepository facultyRepository, EducationRepository educationRepository, CampusRepository campusRepository, CompanyRepository companyRepository, TargetAudienceService targetAudienceService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.facultyRepository = facultyRepository;
        this.educationRepository = educationRepository;
        this.campusRepository = campusRepository;
        this.companyRepository = companyRepository;
        this.subjectPreferenceRepository = subjectPreferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.targetAudienceService = targetAudienceService;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Long getOwnId(Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        return activeUser.getId();
    }

    public UserEntity getUserById(long userId, Authentication authentication) {
        UserEntity activeUser = getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        if(!activeUser.getRoles().contains(admin) && !activeUser.getRoles().contains(coordinator)){
            if(activeUser.getId()!=userId){
                throw new NotAllowedException("User can only access his own information");
            }
        }
        return userRepository.findById(userId);
    }

    public Collection<SubjectPreference> getPreferredSubjectsByUserId(long userId, Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        if(!activeUser.getRoles().contains(admin) && !activeUser.getRoles().contains(coordinator)){
            if(activeUser.getId()!=userId){
                throw new NotAllowedException("User can only access his own information");
            }
        }
        return userRepository.findById(userId).getPreferredSubjects();
    }

    public Collection<Subject> getFavouriteSubjectsByUserId(long userId, Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        if(!activeUser.getRoles().contains(admin) && !activeUser.getRoles().contains(coordinator)){
            if(activeUser.getId()!=userId){
                throw new NotAllowedException("User can only access his own information");
            }
        }
        return userRepository.findById(userId).getFavouriteSubjects();
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> getAllStudents(){
        long roleId = roleRepository.findByName(STUDENT).getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    public List<UserEntity> getAllAdministrators() {
        long roleId = roleRepository.findByName("ROLE_ADMIN").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    public List<UserEntity> getAllPromotors() {
        long roleId = roleRepository.findByName("ROLE_PROMOTOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    public List<UserEntity> getAllCoordinators() {
        long roleId = roleRepository.findByName("ROLE_COORDINATOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    public List<UserEntity> getAllContacts() {
        long roleId = roleRepository.findByName("ROLE_CONTACT").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    public List<UserEntity> getUsers(String id, String type) {
        if(id.equals("null") && type.equals("null")) return userRepository.findAll();
        else if (type.equals("null") && !id.equals("null")) return userRepository.findAllById(Collections.singleton(Long.parseLong(id)));
        else if (id.equals("null") && !type.equals("null")) {
            if(type.equals("administrator")) return getAllCoordinators();
            else if(type.equals("student")) return getAllStudents();
            else if (type.equals("promotors")) return getAllPromotors();
            else if (type.equals("coordinator")) return getAllCoordinators();
            else return null;
        }
        else {return null;}
    }

    public boolean isRole(String r, Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        Role role = roleRepository.findByName(r);
        return activeUser.getRoles().contains(role);
    }

    public UserData getUserData() {
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        Role promotor = roleRepository.findByName("ROLE_PROMOTOR");
        Role student = roleRepository.findByName("ROLE_STUDENT");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        UserData result = new UserData();
        result.setTotalAmount((int) userRepository.count());
        result.setNrOfAdmins(userRepository.countUserEntitiesByRolesContaining(admin));
        result.setNrOfCoordinators(userRepository.countUserEntitiesByRolesContaining(coordinator));
        result.setNrOfPromotors(userRepository.countUserEntitiesByRolesContaining(promotor));
        result.setNrOfStudents(userRepository.countUserEntitiesByRolesContaining(student));
        result.setNrOfContacts(userRepository.countUserEntitiesByRolesContaining(contact));
        result.setNrOfCompanies((int) companyRepository.count());
        result.setNrOfNonApprovedCompanies(companyRepository.countCompaniesByApproved(false));
        result.setNrOfStudentsWithFinalSubject(userRepository.countUserEntitiesByFinalSubjectIsNotNull());
        return result;
    }

    public void deleteUser(long id) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " +id+ ")");
        userRepository.deleteById(id);
    }

    public void addNewUser(UserEntity user) {
        log.info("Saving new user {} to the database", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addNewUserBatch(Authentication authentication){
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        Role coordinator = roleRepository.findByName("ROLE_COORDINATOR");
        Role promotor = roleRepository.findByName("ROLE_PROMOTOR");
        Role student = roleRepository.findByName("ROLE_STUDENT");
        Role contact = roleRepository.findByName("ROLE_CONTACT");
        Role [] roles = {admin, coordinator, promotor, student, contact};
        log.info("LALALALA");
        log.info(roles[0].toString());
        log.info(roles[1].toString());
        String line;
        String [] parts;
        Scanner sc = null;
        try{
            sc = new Scanner(new FileReader("uploads/UserBatchInput.csv"));
            while (sc.hasNextLine()){
                line = sc.nextLine();
                if(line.length()>0) {
                    parts = line.split(";");
                    // Adding user
                    UserEntity user;
                    if(!userRepository.existsByEmail(parts[2])) {
                        user = new UserEntity(parts[0], parts[1], parts[2], parts[3], parts[4]);
                        addNewUser(user);
                    }
                    else {
                        user = getUserByEmail(parts[2]);
                    }

                    // Adding roles
                    for (int i = 0; i < 5; i++) {
                        if (Boolean.parseBoolean(parts[i + 5])) {
                            user.addRole(roles[i]);
                        }
                    }

                    // Adding TargetAudience
                    if (parts.length > 10) {
                        Faculty faculty = facultyRepository.findByName(parts[10]);
                        Education education = educationRepository.findByName(parts[11]);
                        Campus campus = campusRepository.findByName(parts[12]);
                        if (faculty != null) {
                            long facultyId = faculty.getId();
                            long educationId;
                            long campusId;
                            if (education == null) educationId = 0;
                            else educationId = education.getId();
                            if (campus == null) campusId = 0;
                            else campusId = campus.getId();
                            addTargetAudience(user.getId(), facultyId, educationId, campusId, authentication);
                        }
                    }
                }
            }
        }
        catch (Exception e){System.out.println("Error while reading file: "+e);}
        finally {if (sc!=null) sc.close();}
    }

    public void updateUser(long id, String firstname, String lastname, String email, String telNr, String password) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id + ")");
        UserEntity user = userRepository.getById(id);
        if(firstname != null && firstname.length()>0) user.setFirstName(firstname);
        if(lastname != null && lastname.length()>0) user.setLastName(lastname);
        if(email != null && email.length()>0) user.setEmail(email);
        if(telNr != null && telNr.length()>0) user.setTelNr(telNr);
        if(password != null && password.length()>0) user.setPassword(passwordEncoder.encode(password));
    }

    public void addNewPreferredSubject(long userId, Subject subject, int index, Authentication authentication){
        // TODO enable
//        if(!Timing.getInstance().isBeforeDeadlinePreferredSubjects()){
//            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndPreferredSubjects());
//        }
        UserEntity activeUser = getUserByEmail(authentication.getName());
        UserEntity user = userRepository.findById(userId);
        Role student = roleRepository.findByName(STUDENT);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(activeUser.getRoles().contains(admin) || user.equals(activeUser)){
            if(user.getRoles().contains(student)){
                SubjectPreference subjectPreference = null;
                for(SubjectPreference sp : user.getPreferredSubjects()){
                    if(sp.getIndex()==index) subjectPreference=sp;
                }
                if(subjectPreference==null){
                    if(index<1 || index>3) throw new NotAllowedException("Only indices 1,2,3 are allowed.");
                    subjectPreference = new SubjectPreference(subject, user, index);
                    subjectPreferenceRepository.save(subjectPreference);
                    user.addSubjectPreference(subjectPreference);
                }
                else{
                    subjectPreference.setSubject(subject);
                }
                log.info("Added subject {} to user {}", subject.getName(), user.getFirstName());
            }
            else{throw new RuntimeException("Can only add preferred subjects to student account");}
        }
        else{throw new RuntimeException("Student can only access his own account");}
    }

    public void addNewFavouriteSubject(long userId, Subject subject, Authentication authentication){
        // TODO enable
//        if(!Timing.getInstance().isBeforeDeadlinePreferredSubjects()){
//            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndPreferredSubjects());
//        }
        UserEntity activeUser = getUserByEmail(authentication.getName());
        UserEntity user = userRepository.findById(userId);
        Role student = roleRepository.findByName(STUDENT);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(activeUser.getRoles().contains(admin) || user.equals(activeUser)){
            if(user.getRoles().contains(student)){
                user.addFavouriteSubject(subject);
                log.info("Added favourite subject {} to user {}", subject.getName(), user.getFirstName());
            }
            else{throw new RuntimeException("Can only add favourite subjects to student account");}
        }
        else{throw new RuntimeException("Student can only access his own account");}
    }

    public void addTargetAudience (long userId, long facultyId, long educationId, long campusId, Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        UserEntity user = userRepository.findById(userId);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(activeUser.getRoles().contains(admin) || user.equals(activeUser)) {
            Role student = roleRepository.findByName(STUDENT);
            if (!user.getRoles().contains(student)) {
                throw new InputNotValidException("Can only add targetAudience to STUDENT_ROLE");
            }
            if (!targetAudienceService.exists(facultyId, educationId, campusId)) {
                throw new ResourceNotFoundException("targetAudience does not exist.");
            }
            TargetAudience targetAudience = targetAudienceService.getByAllIds(facultyId, educationId, campusId);
            user.setTargetAudience(targetAudience);
        }
        else {
            throw new NotAllowedException("Students can only access their own TargetAudience.");
        }
    }

    public UserEntity getUser(String email){
        return userRepository.findByEmail(email);
    }

    public void addRoleToUser(String email, String roleName){
        log.info("Adding role {} to user {}", roleName, email);
        UserEntity user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }


    public void addFinalSubject(long userId, Subject subject){
        if(!Timing.getInstance().isBeforeDeadlineFinalAllocation()){
            throw new NotAllowedException("Too late for the deadline of "+Timing.getInstance().getEndFinalAllocation());
        }
        UserEntity user = userRepository.findById(userId);
        Role student = roleRepository.findByName(STUDENT);
        if(!(subject.getFinalStudents().size() < subject.getNrOfStudents())){
            throw new InputNotValidException("Subject already has maximum amount of students");
        }
        if(!user.getRoles().contains(student)){
            throw new InputNotValidException("Can only add final subject to students.");
        }
        user.setFinalSubject(subject);
        subject.addFinalStudent(user);
    }

    @Override
    //Tell spring what users it has available
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else{
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }






}
