package com.example.BachelorProefBackend.SubjectManagement.Campus;

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
public class CampusService {

    private final CampusRepository campusRepository;
    
    @Autowired
    public CampusService(CampusRepository campusRepository){
        this.campusRepository = campusRepository;
    }

    @GetMapping
    public List<Campus> getAllCampuses() {return campusRepository.findAll();}

    @PostMapping
    public void addNewCampus(Campus campus) {
        campusRepository.save(campus);
    }

    @DeleteMapping
    public void deleteCampus(long id){
        campusRepository.deleteById(id);
    }

    @PutMapping
    public void updateCampus(long id, String name, String address){
        if(!campusRepository.existsById(id)) throw new IllegalStateException("Campus does not exist (id: "+id+")");
        Campus campus = campusRepository.getById(id);
        if(name != null && name.length()>0) campus.setName(name);
        if(address != null && address.length()>0) campus.setAddress(address);
    }

}
