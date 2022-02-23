package com.example.BachelorProefBackend.UserManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User_entity> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User_entity user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id+ ")");
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, String firstName, String email) {
        if(!userRepository.existsById(id)) throw new IllegalStateException("User does not exist (id: " + id + ")");
        User_entity user = userRepository.getById(id);
        if(firstName != null && firstName.length()>0 && !Objects.equals(user.getFirstname(), firstName)) user.setFirstname(firstName);
        if(email != null && email.length()>0 && !Objects.equals(user.getEmail(), email)) user.setFirstname(email);

    }
}
