package com.example.BachelorProefBackend.UserManagement.User;

import com.example.BachelorProefBackend.UserManagement.Role.Role;
import com.example.BachelorProefBackend.UserManagement.Role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    //Methodes als find, findAll(), delete, ... allemaal beschikbaar doordat repository een interface is.

    @GetMapping
    public List<User_entity> getUserById(long user_id) {
        return userRepository.findAllById(Collections.singleton(user_id));
    }

    @GetMapping
    public User_entity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User_entity> getAllStudents() {
        return userRepository.getAllStudents();
    }
    public List<User_entity> getAllAdministrators() {
        return userRepository.getAllAdministrators();
    }
    public List<User_entity> getAllPromotors() {
        return userRepository.getAllPromotors();
    }
    public List<User_entity> getAllCoordinators() {
        return userRepository.getAllCoordinators();
    }

    public List<User_entity> getUsers(String id, String type) {
        if(id.equals("null") && type.equals("null")) return userRepository.findAll();
        else if (type.equals("null") && !id.equals("null")) return userRepository.findAllById(Collections.singleton(Long.parseLong(id)));
        else if (id.equals("null") && !type.equals("null")) {
            if(type.equals("administrator")) return userRepository.getAllCoordinators();
            else if(type.equals("student")) return userRepository.getAllStudents();
            else if (type.equals("promotors")) return userRepository.getAllPromotors();
            else if (type.equals("coordinator")) return userRepository.getAllCoordinators();
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
