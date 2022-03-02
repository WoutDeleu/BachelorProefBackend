package com.example.BachelorProefBackend.UserManagement.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="userManagement/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    //GET
    @GetMapping
    public List<Role> getAllRoles() {return roleService.getAllRoles();}

    //POST
    @PostMapping
    public void addNewRole(@RequestBody Role role){roleService.addNewRole(role);}
}
