package com.example.BachelorProefBackend.UserManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
}
