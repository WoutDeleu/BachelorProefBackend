package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.subjectmanagement.subject.SubjectService;
import com.example.bachelorproefbackend.subjectmanagement.subjectpreference.SubjectPreference;
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

    @GetMapping(path="ownId")
    public Long getOwnId(Authentication authentication) {return userService.getOwnId(authentication);}

    @GetMapping(path="{userId}")
    public UserEntity getUserById(@PathVariable("userId") long id, Authentication authentication) {
        return userService.getUserById(id, authentication);
    }

    @GetMapping(path="{userId}/preferredSubjects")
    public Collection<SubjectPreference> getPreferredSubjectsByUserById(@PathVariable("userId") long userId, Authentication authentication) {
        return userService.getPreferredSubjectsByUserId(userId, authentication);
    }

    @GetMapping(path="{userId}/favouriteSubjects")
    public Collection<Subject> getFavouriteSubjectsByUserById(@PathVariable("userId") long id, Authentication authentication) {
        return userService.getFavouriteSubjectsByUserId(id, authentication);
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

    @GetMapping(path="stats")
    public UserData getUserData() {
        return userService.getUserData();
    }

    @PostMapping
    public void addNewUser(@RequestParam String firstName, String lastName, String email, String telNr, String password) {
        userService.addNewUser(new UserEntity(firstName, lastName, email, telNr, password));
    }

    @PostMapping(path="batch")
    public ResponseEntity<ResponseMessage> addNewUserBatch(@RequestParam("file") MultipartFile file, Authentication authentication){
        String message;
        try {
            storageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            userService.addNewUserBatch(authentication); // Creating users from the file
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping(path="student/addPreferredSubject")
    public void addNewPreferredSubject(@RequestParam long userId, long subjectId, int index, Authentication authentication){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addNewPreferredSubject(userId, subject, index, authentication);
    }

    @PostMapping(path="student/addFavouriteSubject")
    public void addNewFavouriteSubject(@RequestParam long userId, long subjectId, Authentication authentication){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addNewFavouriteSubject(userId, subject, authentication);
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
    public void addTargetAudience(@RequestParam long userId, long facultyId, long educationId, long campusId, Authentication authentication){
        userService.addTargetAudience(userId, facultyId, educationId, campusId, authentication);
    }

    @PutMapping(path="student/addFinalSubject")
    public void addFinalSubject(@RequestParam long userId, long subjectId){
        Subject subject = subjectService.getSubjectById(subjectId);
        userService.addFinalSubject(userId, subject);
    }





}