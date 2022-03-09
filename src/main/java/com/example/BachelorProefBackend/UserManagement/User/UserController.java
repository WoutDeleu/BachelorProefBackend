package com.example.BachelorProefBackend.UserManagement.User;

import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.SubjectManagement.Subject.SubjectService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping(path="userManagement/users")
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;

    @Autowired //instantie van userService automatisch aangemaakt en in deze constructor gestoken (Dependency injection)
    public UserController(UserService userService, SubjectService subjectService) {
        this.userService = userService;
        this.subjectService = subjectService;
    }

    //GET
    @GetMapping(path="{userId}")
    public User_entity getUserById(@PathVariable("userId") Long user_id) {return userService.getUserById(user_id);}
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
    @CrossOrigin(origins ="http://localhost:3000")
    public List<User_entity> getUsers(@RequestParam(defaultValue = "null") String id, @RequestParam(defaultValue = "null") String type) {
        return userService.getUsers(id,type);
    }
    @GetMapping(path="student/{userId}/preferredSubjects")
    public List<Subject> getPreferredSubjects(@PathVariable("userId") long id) {return userService.getPreferredSubjects(id);}

    //POST
    @PostMapping
    public void addNewUser(@RequestParam String firstname, String lastname, String email, String telNr, String password) {
        userService.addNewUser(new User_entity(firstname, lastname, email, telNr, password));
    }

    //DELETE
    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
    }

    //PUT
    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") long id,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String telNr,
                           @RequestParam(required = false) String password) {
        userService.updateUser(id, firstName, lastName, email, telNr, password);
    }
    @PostMapping(path="student/addPreferredSubject")
    public void addNewPreferredSubject(@RequestParam long userId, long subjectId){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addNewPreferredSubject(userId, subject);
    }

    //AUTHENTICATION
    @PostMapping(path="addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestParam String email, String roleName){
        userService.addRoleToUser(email, roleName);
        return ResponseEntity.ok().build();
    }



}