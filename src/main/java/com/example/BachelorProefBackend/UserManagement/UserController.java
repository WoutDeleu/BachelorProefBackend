package com.example.BachelorProefBackend.UserManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="userManagement/users")
public class UserController {
    private final UserService userService;

    @Autowired //instantie van userService automatisch aangemaakt en in deze constructor gestoken (Dependency injection)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get
    @GetMapping
    public List<User_entity> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping(path="{userId}")
    public List<User_entity> getUserById(@PathVariable("userId") Long user_id) {
        return userService.getUserById(user_id);
    }
    @GetMapping(path="student")
    public List<User_entity> getAllStudents() {
        return userService.getAllStudents();
    }
    @GetMapping(path="administrator")
    public List<User_entity> getAllAdministrators() {
        return userService.getAllAdministrators();
    }
    @GetMapping(path="promotor")
    public List<User_entity> getAllPromotors() {
        return userService.getAllPromotors();
    }
    @GetMapping(path="coordinator")
    public List<User_entity> getAllCoordinators() {
        return userService.getAllCoordinators();
    }


    //Post
    @PostMapping
    public void addNewUser(@RequestBody User_entity user) {
        userService.addNewUser(user);
    }

    //Delete
    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
    }

    //Put
    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        userService.updateUser(userId, name, email);

    }


}
