package com.example.BachelorProefBackend.UserManagement.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.BachelorProefBackend.SubjectManagement.Subject.Subject;
import com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment.SubjectAssignmentService;
import com.example.BachelorProefBackend.UserManagement.Role.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path="userManagement/users")
public class UserController {
    private final UserService userService;
    private final SubjectAssignmentService saService;

    @Autowired //instantie van userService automatisch aangemaakt en in deze constructor gestoken (Dependency injection)
    public UserController(UserService userService, SubjectAssignmentService saService) {
        this.userService = userService;
        this.saService = saService;
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

    @GetMapping(path="{userId}/subject")
    public List<Subject> getSubjectByUserId(@PathVariable("userId") long id){
        return saService.getSubjectByUserId(id);
    }

    //POST
    @PostMapping
    public void addNewUser(@RequestBody User_entity user) {
        userService.addNewUser(user);
    }

    //DELETE
    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
    }

    //PUT
    @PutMapping(path="{userId}")
    public void updateUser(@PathVariable("userId") Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        userService.updateUser(id, name, email);
    }

    //AUTHENTICATION
    @PostMapping(path="/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }





}

@Data //getters and setters
class RoleToUserForm{
    private String email;
    private String roleName;
}