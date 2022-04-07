package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.authentication.InputNotValidException;
import com.example.bachelorproefbackend.authentication.NotAllowedException;
import com.example.bachelorproefbackend.authentication.ResourceNotFoundException;
import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudienceService;
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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.util.*;

@Service //Specifieke versie van @Component: deze klasse wordt gebruikt als Bean
@Transactional
@Slf4j //logging
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TargetAudienceService targetAudienceService;
    private static final String STUDENT = "ROLE_STUDENT";


    @Autowired
    public UserService(UserRepository userRepository, TargetAudienceService targetAudienceService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.targetAudienceService = targetAudienceService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
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

    @GetMapping
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @GetMapping
    public List<UserEntity> getAllStudents(){
        long roleId = roleRepository.findByName(STUDENT).getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    @GetMapping
    public List<UserEntity> getAllAdministrators() {
        long roleId = roleRepository.findByName("ROLE_ADMIN").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    @GetMapping
    public List<UserEntity> getAllPromotors() {
        long roleId = roleRepository.findByName("ROLE_PROMOTOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    @GetMapping
    public List<UserEntity> getAllCoordinators() {
        long roleId = roleRepository.findByName("ROLE_COORDINATOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }

    @GetMapping
    public List<Subject> getPreferredSubjects(long id){
        if(userRepository.existsById(id))
            return new ArrayList<>(userRepository.findById(id).getPreferredSubjects());
        else throw new RuntimeException("User not found");
    }

    @GetMapping
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

    @GetMapping
    public boolean isRole(String r, Authentication authentication){
        UserEntity activeUser = getUserByEmail(authentication.getName());
        Role role = roleRepository.findByName(r);
        return activeUser.getRoles().contains(role);
    }

    @DeleteMapping
    public void deleteUser(long id) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " +id+ ")");
        userRepository.deleteById(id);
    }

    @PostMapping
    public void addNewUser(UserEntity user) {
        log.info("Saving new user {} to the database", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addNewUserBatch(){
        String line;
        String [] parts;
        Scanner sc = null;
        try{
            sc = new Scanner(new FileReader("uploads/UserBatchInput.csv"));
            while (sc.hasNextLine()){
                line = sc.nextLine();
                if(line.length()>0){
                    parts = line.split(";");
                    UserEntity user = new UserEntity(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    addNewUser(user);
                }
            }
        }
        catch (Exception e){System.out.println("Error while reading file: "+e);}
        finally {if (sc!=null) sc.close();}
    }



    @PutMapping
    public void updateUser(long id, String firstname, String lastname, String email, String telNr, String password) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id + ")");
        UserEntity user = userRepository.getById(id);
        if(firstname != null && firstname.length()>0) user.setFirstName(firstname);
        if(lastname != null && lastname.length()>0) user.setLastName(lastname);
        if(email != null && email.length()>0) user.setEmail(email);
        if(telNr != null && telNr.length()>0) user.setTelNr(telNr);
        if(password != null && password.length()>0) user.setPassword(passwordEncoder.encode(password));
    }

    @PutMapping
    public void addNewPreferredSubject(long uid, Subject subject, Authentication authentication){
        //TODO logic to see if this is before the required date
        UserEntity activeUser = getUserByEmail(authentication.getName());
        UserEntity user = userRepository.findById(uid);
        Role student = roleRepository.findByName(STUDENT);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(activeUser.getRoles().contains(admin) || user.equals(activeUser)){
            if(user.getRoles().contains(student)){
                user.getPreferredSubjects().add(subject);
                log.info("Added subject {} to user {}", subject.getName(), user.getFirstName());
            }
            else{throw new RuntimeException("Can only add preferred subjects to student account");}
        }
        else{throw new RuntimeException("Student can only access his own account");}
    }

    @PutMapping
    public void addTargetAudience (long userId, long facultyId, long educationId, long campusId){
        UserEntity user = userRepository.findById(userId);
        Role student = roleRepository.findByName(STUDENT);
        if(!user.getRoles().contains(student)){
            throw new InputNotValidException("Can only add targetAudience to STUDENT_ROLE");
        }
        if(!targetAudienceService.exists(facultyId, educationId, campusId)){
            throw new ResourceNotFoundException("targetAudience does not exist.");
        }
        TargetAudience targetAudience = targetAudienceService.getByAllIds(facultyId, educationId, campusId);
        user.setTargetAudience(targetAudience);
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
