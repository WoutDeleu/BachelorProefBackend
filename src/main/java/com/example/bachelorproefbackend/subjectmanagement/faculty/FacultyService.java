package com.example.bachelorproefbackend.subjectmanagement.faculty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public List<Faculty> getAllFaculties() {return facultyRepository.findAll();}

    @PostMapping
    public void addNewFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @DeleteMapping
    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);
    }

    @PutMapping
    public void updateFaculty(long id, String name){
        if(!facultyRepository.existsById(id)) throw new IllegalStateException("Faculty does not exist (id: "+id+")");
        Faculty faculty = facultyRepository.getById(id);
        if(name != null && name.length()>0) faculty.setName(name);
    }
}
