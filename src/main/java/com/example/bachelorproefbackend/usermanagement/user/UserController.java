package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectService;
import com.example.bachelorproefbackend.usermanagement.filestorage.ResponseMessage;
import com.example.bachelorproefbackend.usermanagement.filestorage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;


@Slf4j
@RestController
@RequestMapping(path="userManagement/users")
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final StorageService storageService;

    @Autowired
    public UserController(UserService userService, SubjectService subjectService, StorageService storageService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.storageService = storageService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path="{userId}")
    public UserEntity getUserById(@PathVariable("userId") long id, Authentication authentication) {
        return userService.getUserById(id, authentication);
    }

    @GetMapping(path="{userId}/preferredSubjects")
    public Collection<Subject> getPreferredSubjectsByUserById(@PathVariable("userId") long id, Authentication authentication) {
        return userService.getPreferredSubjectsByUserId(id, authentication);
    }

    @GetMapping(path="student")
    public List<UserEntity> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping(path="administrator")
    public List<UserEntity> getAllAdministrators() {
        return userService.getAllAdministrators();
    }

    @GetMapping(path="promotor")
    public List<UserEntity> getAllPromotors() {
        return userService.getAllPromotors();
    }

    @GetMapping(path="coordinator")
    public List<UserEntity> getAllCoordinators() {
        return userService.getAllCoordinators();
    }

    @GetMapping(path="contact")
    public List<UserEntity> getAllContacts() {
        return userService.getAllContacts();
    }

    @GetMapping(path="student/{userId}/preferredSubjects")
    public List<Subject> getPreferredSubjects(@PathVariable("userId") long id) {return userService.getPreferredSubjects(id);}

    @GetMapping(path="stats")
    public UserData getUserData() {
        return userService.getUserData();
    }

    @PostMapping
    public void addNewUser(@RequestParam String firstName, String lastName, String email, String telNr, String password) {
        userService.addNewUser(new UserEntity(firstName, lastName, email, telNr, password));
    }

    @PostMapping(path="batch")
    public ResponseEntity<ResponseMessage> addNewUserBatch(@RequestParam("file") MultipartFile file){
        String message;
        try {
            storageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            userService.addNewUserBatch(); // Creating users from the file
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping(path="student/addPreferredSubject")
    public void addNewPreferredSubject(@RequestParam long userId, long subjectId, Authentication authentication){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addNewPreferredSubject(userId, subject, authentication);
    }

    @PostMapping(path="addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestParam String email, String roleName){
        userService.addRoleToUser(email, roleName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
    }

    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") long id,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String telNr,
                           @RequestParam(required = false) String password) {
        userService.updateUser(id, firstName, lastName, email, telNr, password);
    }

    @PostMapping(path="student/addTargetAudience")
    public void addTargetAudience(@RequestParam long userId, long facultyId, long educationId, long campusId){
        userService.addTargetAudience(userId, facultyId, educationId, campusId);
    }

    @PutMapping(path="student/addFinalSubject")
    public void addFinalSubject(@RequestParam long userId, long subjectId){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addFinalSubject(userId, subject);
    }





}