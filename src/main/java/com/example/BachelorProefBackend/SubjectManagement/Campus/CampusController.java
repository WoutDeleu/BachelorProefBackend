package com.example.BachelorProefBackend.SubjectManagement.Campus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/campus")
public class CampusController {
    private final CampusService campusService;

    @Autowired
    public CampusController (CampusService campusService){
        this.campusService = campusService;
    }

    @GetMapping
    public List<Campus> getAllCampuses() {return campusService.getAllCampuses();}

    @PostMapping
    public void addNewCampus(@RequestParam String name, String address){
        campusService.addNewCampus(new Campus(name, address));
    }

    @DeleteMapping(path="{campusId}")
    public void deleteCampus(@PathVariable("campusId") long id){
        campusService.deleteCampus(id);
    }

    @PutMapping(path="{campusId}")
    public void updateCampus(@PathVariable("campusId") long id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String address){
        campusService.updateCampus(id, name, address);
    }

}
