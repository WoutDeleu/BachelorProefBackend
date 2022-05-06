package com.example.bachelorproefbackend.subjectmanagement.campus;

import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudience;
import com.example.bachelorproefbackend.subjectmanagement.targetaudience.TargetAudienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CampusService {

    private final CampusRepository campusRepository;
    private final TargetAudienceService targetAudienceService;
    
    @Autowired
    public CampusService(CampusRepository campusRepository, TargetAudienceService targetAudienceService){
        this.campusRepository = campusRepository;
        this.targetAudienceService = targetAudienceService;
    }

    public List<Campus> getAllCampuses() {return campusRepository.findAll();}

    public List<Campus> getAllCampusesByFaculties(Faculty [] faculties){
        List<Campus> result = new ArrayList<>();
        for (Faculty f : faculties){
            List<TargetAudience> targets = targetAudienceService.getAllByFaculty(f);
            for (TargetAudience t : targets) {
                result.add(t.getCampus());
            }
        }
        return result;
    }

    public List<Campus> getAllCampusesByEducations(Education [] educations){
        List<Campus> result = new ArrayList<>();
        for (Education e : educations){
            List<TargetAudience> targets = targetAudienceService.getAllByEducation(e);
            for (TargetAudience t : targets) {
                result.add(t.getCampus());
            }
        }
        return result;
    }

    public void addNewCampus(Campus campus) {
        campusRepository.save(campus);
    }

    public void deleteCampus(long id){
        campusRepository.deleteById(id);
    }

    public void updateCampus(long id, String name, String address){
        if(!campusRepository.existsById(id)) throw new IllegalStateException("Campus does not exist (id: "+id+")");
        Campus campus = campusRepository.getById(id);
        if(name != null && name.length()>0) campus.setName(name);
        if(address != null && address.length()>0) campus.setAddress(address);
    }

}
