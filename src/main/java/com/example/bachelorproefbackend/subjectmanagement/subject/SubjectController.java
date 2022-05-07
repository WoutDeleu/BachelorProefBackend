package com.example.bachelorproefbackend.subjectmanagement.subject;

import com.example.bachelorproefbackend.configuration.exceptions.InputNotValidException;
import com.example.bachelorproefbackend.subjectmanagement.tag.Tag;
import com.example.bachelorproefbackend.subjectmanagement.tag.TagRepository;
import com.example.bachelorproefbackend.subjectmanagement.tag.TagService;
import com.example.bachelorproefbackend.usermanagement.company.Company;
import com.example.bachelorproefbackend.usermanagement.company.CompanyService;
import com.example.bachelorproefbackend.usermanagement.filestorage.FileStorageService;
import com.example.bachelorproefbackend.usermanagement.filestorage.ResponseMessage;
import com.example.bachelorproefbackend.usermanagement.user.UserRepository;
import com.example.bachelorproefbackend.usermanagement.user.UserService;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final CompanyService companyService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final FileStorageService storageService;

    @Autowired
    public SubjectController(SubjectService subjectService, TagRepository tagRepository, FileStorageService storageService, CompanyService companyService, UserService userService, TagService tagService, UserRepository userRepository) {
        this.subjectService = subjectService;
        this.companyService = companyService;
        this.userService = userService;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.tagRepository = tagRepository;
    }

    public UserEntity getUserObject(Authentication authentication){
        return userService.getUserByEmail(authentication.getName());
    }

    @GetMapping
    public List<Subject> getAllSubjects(Authentication authentication) {return subjectService.getAllSubjects(authentication);}

    @GetMapping(path = "nonApproved")
    public List<Subject> getAllNonApprovedSubjects() {return subjectService.getAllNonApprovedSubjects();}

    @GetMapping(path="byTargetAudience")
    public List<Subject> getAllRelatedSubjects(Authentication authentication) {return subjectService.getAllRelatedSubjects(authentication);}

    @GetMapping(path="{subjectId}")
    public Subject getSubjectById(@PathVariable("subjectId") Long subjectId){
        return subjectService.getSubjectById(subjectId);
    }

    @GetMapping(path = "stats")
    public SubjectData getSubjectData() {
        return subjectService.getSubjectData();
    }

    @GetMapping(path="{subjectId}/pdf")
    public File getSubjectPdf(@PathVariable("subjectId") Long subjectId){
        try  {
            File resource = new ClassPathResource("uploads/PdfSubject2.pdf").getFile();
            return resource;
        }
        catch (IOException e){
            throw new IllegalStateException("weet ik veel"+e);
        }
    }

    @PostMapping
    public long addNewSubject(@RequestParam String name, String description, int nrOfStudents, String [] tagNames, Authentication authentication) {
        Tag [] tags;
        if(tagNames==null){
            tags = new Tag[0];
        }
        else{
            tags = new Tag[tagNames.length];
            for(int i = 0; i<tagNames.length; i++){
                Tag tag;
                if(tagService.existsTagByName(tagNames[i]))
                    tag = tagService.getTagByName(tagNames[i]);
                else
                    tag = new Tag(tagNames[i]);
                tags[i] = tag;
            }
        }
        return subjectService.addNewSubject(new Subject(name, description, nrOfStudents, tags), authentication);
    }

    @PostMapping(path="uploadPdf")
    public ResponseEntity<ResponseMessage> addPdfToSubject(@RequestParam("file") MultipartFile file, long subjectId){
        String message = "";
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            if(subject != null){
                storageService.savePdf(file, subjectId); // allows only pdf
                message = "Uploaded the pdf successfully: " + file.getOriginalFilename();
                subject.setHasPdf(true);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            else{
                throw new InputNotValidException("Subject does not exist.");
            }
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping(path="{subjectId}")
    public void deleteSubject(@PathVariable("subjectId") long id) {subjectService.deleteSubject(id);}

    @PutMapping(path="{subjectId}")
    public void updateSubject(@PathVariable("subjectId") long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) int nrOfStudents) {
        subjectService.updateSubject(id, name, description, nrOfStudents);
    }

    @PutMapping(path="{subjectId}/setApproved")
    public void updateSubject(@PathVariable("subjectId") long id, @RequestParam boolean approved){
        subjectService.setApproved(id, approved);
    }

    @PutMapping(path="{subjectId}/addCompany")
    public void addCompany(@PathVariable("subjectId") long subjectId, @RequestParam long companyId, Authentication authentication){
        Company company = companyService.getCompanyById(companyId);
        subjectService.addCompany(subjectId, company, authentication);
    }

    @PutMapping(path="{subjectId}/addPromotor")
    public void addPromotor(@PathVariable("subjectId") long subjectId, @RequestParam long promotorId, Authentication authentication){
        UserEntity promotor = userRepository.findById(promotorId);
        subjectService.addPromotor(subjectId, promotor, authentication);
    }

    @PutMapping(path="{subjectId}/addTag")
    public void addTag(@PathVariable("subjectId") long subjectId, @RequestParam String [] tagNames, Authentication authentication){
        Tag [] tags = new Tag[tagNames.length];
        for(int i = 0; i<tagNames.length; i++){
            Tag t;
            if(tagRepository.existsTagByName(tagNames[i]))
                t = tagRepository.getTagByName(tagNames[i]);
            else
                t = new Tag(tagNames[i]);
            tags[i] = t;
        }
        subjectService.addTag(subjectId, tags, getUserObject(authentication));
    }

    @PostMapping(path="{subjectId}/addTargetAudience")
    public void addTargetAudience(@PathVariable("subjectId") long subjectId, @RequestParam int [] facultyIds,int [] educationIds, int [] campusIds, Authentication authentication){
        log.info("The eagle has landed.");
        log.info("lalal");
        subjectService.addTargetAudience(subjectId, facultyIds, educationIds, campusIds, authentication);
    }



}
