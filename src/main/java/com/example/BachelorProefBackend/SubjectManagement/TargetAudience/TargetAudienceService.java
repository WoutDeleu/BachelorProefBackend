package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Education.Education;
import com.example.BachelorProefBackend.SubjectManagement.Education.EducationRepository;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.FacultyRepository;
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

    @GetMapping
    public List<TargetAudience> getAllTargetAudiences() {return targetAudienceRepository.findAll();}

    @GetMapping
    public List<TargetAudience> getAllByFaculty(Faculty faculty){return targetAudienceRepository.findAllByFaculty(faculty);}

    @GetMapping
    public List<TargetAudience> getAllByEducation(Education education){return targetAudienceRepository.findAllByEducation(education);}

    @GetMapping
    public List<TargetAudience> getAllByCampus(Campus campus){return targetAudienceRepository.findAllByCampus(campus);}

    @GetMapping
    public List<TargetAudience> getAllByCampusId(long id){return targetAudienceRepository.findAllByCampusId(id);}

    @PostMapping
    public void addNewTargetAudience(TargetAudience targetAudience) {
        targetAudienceRepository.save(targetAudience);
    }

    @DeleteMapping
    public void deleteTargetAudience(long id){
        targetAudienceRepository.deleteById(id);
    }

    @PutMapping
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
        for (TargetAudience t : targets){
            if(!t.getFaculty().equals(faculty)){
                targets.remove(t);
            }
            if(!t.getEducation().equals(education)){
                targets.remove(t);
            }
        }
        return targets.size()>0;
    }

    public TargetAudience getByAllIds(long facultyId, long educationId, long campusId){
        Education education = educationRepository.getById(educationId);
        Faculty faculty = facultyRepository.getById(facultyId);
        List<TargetAudience> targets = getAllByCampusId(campusId);
        for (TargetAudience t : targets){
            if(!t.getFaculty().equals(faculty)){
                targets.remove(t);
            }
            if(!t.getEducation().equals(education)){
                targets.remove(t);
            }
        }
        return targets.get(0);
    }
}
