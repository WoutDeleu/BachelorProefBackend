package com.example.BachelorProefBackend.UserManagement;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service //Specifieke versie van @Component: deze klasse wordt gebruikt als Bean
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //GET
    @GetMapping
    public List<User_entity> getAllUsers() {
        return userRepository.findAll();
    }
    //Methodes als find, findAll(), delete, ... allemaal beschikbaar doordat repository een interface is.

    @GetMapping
    public List<User_entity> getUserById(Long user_id) {
        return userRepository.findAllById(Collections.singleton(user_id));
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

    /*
    public List<User_entity> getUsers(long id, String type) {
        if(id == 0 && type == null)
    }
    */


    //DELETE
    public void deleteUser(long id) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " +id+ ")");
        userRepository.deleteById(id);
    }

    //POST
    public void addNewUser(User_entity user) {
        userRepository.save(user);
    }

    //PUT
    @Transactional
    public void updateUser(long id, String firstName, String email) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id + ")");
        User_entity user = userRepository.getById(id);
        if(firstName != null && firstName.length()>0 && !Objects.equals(user.getFirstname(), firstName)) user.setFirstname(firstName);
        if(email != null && email.length()>0 && !Objects.equals(user.getEmail(), email)) user.setFirstname(email);
    }


}
