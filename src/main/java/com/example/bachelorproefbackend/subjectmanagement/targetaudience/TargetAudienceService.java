package com.example.bachelorproefbackend.subjectmanagement.targetaudience;

import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationRepository;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TargetAudienceService {

    private final TargetAudienceRepository targetAudienceRepository;
    private final FacultyRepository facultyRepository;
    private final EducationRepository educationRepository;

    @Autowired
    public TargetAudienceService(TargetAudienceRepository targetAudienceRepository, FacultyRepository facultyRepository, EducationRepository educationRepository){
        this.targetAudienceRepository = targetAudienceRepository;
        this.facultyRepository = facultyRepository;
        this.educationRepository = educationRepository;
    }

    public List<TargetAudience> getAllTargetAudiences() {return targetAudienceRepository.findAll();}

    public List<TargetAudience> getAllByFaculty(Faculty faculty){return targetAudienceRepository.findAllByFaculty(faculty);}
    public List<TargetAudience> getAllByFacultyId(long facultyId){return targetAudienceRepository.findAllByFacultyId(facultyId);}

    public List<TargetAudience> getAllByEducation(Education education){return targetAudienceRepository.findAllByEducation(education);}
    public List<TargetAudience> getAllByEducationId(long educationId){return targetAudienceRepository.findAllByEducationId(educationId);}

    public List<TargetAudience> getAllByCampus(Campus campus){return targetAudienceRepository.findAllByCampus(campus);}
    public List<TargetAudience> getAllByCampusId(long campusId){return targetAudienceRepository.findAllByCampusId(campusId);}

    public void addNewTargetAudience(TargetAudience targetAudience) {
        targetAudienceRepository.save(targetAudience);
    }

    public void deleteTargetAudience(long id){
        targetAudienceRepository.deleteById(id);
    }

    public void updateTargetAudience(long id, Campus campus, Faculty faculty){
        if(!targetAudienceRepository.existsById(id)) throw new IllegalStateException("TargetAudience does not exist (id: "+id+")");
        TargetAudience targetAudience = targetAudienceRepository.getById(id);
        if(campus != null) targetAudience.setCampus(campus);
        if(faculty != null) targetAudience.setFaculty(faculty);
    }

    public boolean exists(long facultyId, long educationId, long campusId){
        Education education = educationRepository.getById(educationId);
        Faculty faculty = facultyRepository.getById(facultyId);
        List<TargetAudience> targets = getAllByCampusId(campusId);
        List<TargetAudience> targetsToRemove = new ArrayList<>();
        for (TargetAudience t : targets){
            if((!t.getFaculty().equals(faculty)) || (!t.getEducation().equals(education))){
                targetsToRemove.add(t);
            }
        }
        for (TargetAudience t : targetsToRemove){
            targets.remove(t);
        }
        return targets.size()>0;
    }

    public TargetAudience getByAllIds(long facultyId, long educationId, long campusId){
        Education education = educationRepository.getById(educationId);
        Faculty faculty = facultyRepository.getById(facultyId);
        List<TargetAudience> targets = getAllByCampusId(campusId);
        List<TargetAudience> targetsToRemove = new ArrayList<>();
        for (TargetAudience t : targets){
            if((!t.getFaculty().equals(faculty)) || (!t.getEducation().equals(education))){
                targetsToRemove.add(t);
            }
        }
        for (TargetAudience t : targetsToRemove){
            targets.remove(t);
        }
        return targets.get(0);
    }
}
