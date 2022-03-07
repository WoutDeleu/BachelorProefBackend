package com.example.BachelorProefBackend.UserManagement.User;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.UserManagement.Role.Role;
import com.example.BachelorProefBackend.UserManagement.Role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import javax.transaction.Transactional;
import java.util.*;

@Service //Specifieke versie van @Component: deze klasse wordt gebruikt als Bean
@Transactional
@Slf4j //logging
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //GET
    @GetMapping
    public List<User_entity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping
    public User_entity getUserById(long user_id) {
        return userRepository.findById(user_id);
    }

    @GetMapping
    public User_entity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @GetMapping
    @CrossOrigin(origins ="http://localhost:3000")
    public List<User_entity> getAllStudents(){
        long roleId = roleRepository.findByName("ROLE_STUDENT").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }
    @GetMapping
    public List<User_entity> getAllAdministrators() {
        long roleId = roleRepository.findByName("ROLE_ADMIN").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }
    @GetMapping
    public List<User_entity> getAllPromotors() {
        long roleId = roleRepository.findByName("ROLE_PROMOTOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }
    @GetMapping
    public List<User_entity> getAllCoordinators() {
        long roleId = roleRepository.findByName("ROLE_COORDINATOR").getId();
        return userRepository.findUser_entityByRolesId(roleId);
    }
    @GetMapping
    public List<Subject> getPreferredSubjects(long id){
        if(userRepository.existsById(id))
            return new ArrayList<Subject>(userRepository.findById(id).getPreferredSubjects());
        else throw new RuntimeException("User not found");
    }
    @GetMapping
    public List<User_entity> getUsers(String id, String type) {
        if(id.equals("null") && type.equals("null")) return userRepository.findAll();
        else if (type.equals("null") && !id.equals("null")) return userRepository.findAllById(Collections.singleton(Long.parseLong(id)));
        else if (id.equals("null") && !type.equals("null")) {
            if(type.equals("administrator")) return getAllCoordinators();
            else if(type.equals("student")) return getAllStudents();
            else if (type.equals("promotors")) return getAllPromotors();
            else if (type.equals("coordinator")) return getAllCoordinators();
            else return null;
        }
        else {
            return null;
        }
    }




    //DELETE
    public void deleteUser(long id) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " +id+ ")");
        userRepository.deleteById(id);
    }

    //POST
    public void addNewUser(User_entity user) {
        log.info("Saving new user {} to the database", user.getFirstname());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }



    //PUT
    public void updateUser(long id, String firstName, String email) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id + ")");
        User_entity user = userRepository.getById(id);
        if(firstName != null && firstName.length()>0 && !Objects.equals(user.getFirstname(), firstName)) user.setFirstname(firstName);
        if(email != null && email.length()>0 && !Objects.equals(user.getEmail(), email)) user.setFirstname(email);
    }

    public void addNewPreferredSubject(long uid, Subject subject){
        //TODO logic to see if this is before the required date
        User_entity user = userRepository.findById(uid);
        user.getPreferredSubjects().add(subject);
        log.info("Added subject {} to user {}", subject.getName(), user.getFirstname());
    }

    //AUTHENTICATION
    public User_entity getUser(String email){
        return userRepository.findByEmail(email);
    }


    public void addRoleToUser(String email, String roleName){
        log.info("Adding role {} to user {}", roleName, email);
        User_entity user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    //Tell spring what users it has available
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User_entity user = userRepository.findByEmail(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else{
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
