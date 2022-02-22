package com.example.BachelorProefBackend.UserManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UserService {

    @GetMapping
    public List<User> getUsers() {
        return List.of();
    }
}
