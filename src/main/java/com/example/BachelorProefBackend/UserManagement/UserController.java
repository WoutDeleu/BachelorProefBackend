package com.example.BachelorProefBackend.UserManagement;

import org.apache.catalina.User;
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

    //GET
    //@GetMapping
    //public List<User_entity> getAllUsers() {
    //    return userService.getAllUsers();
    //}
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

    //Mapping based on URL query example
    @GetMapping
    @ResponseBody
    public List<User_entity> getUsers(@RequestParam(defaultValue = "null") String id, @RequestParam(defaultValue = "null") String type) {
        return userService.getUsers(id,type);
    }

    //POST
    @PostMapping
    public void addNewUser(@RequestBody User_entity user) {
        userService.addNewUser(user);
    }

    //DELETE
    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }

    //PUT
    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        userService.updateUser(id, name, email);
    }
}
