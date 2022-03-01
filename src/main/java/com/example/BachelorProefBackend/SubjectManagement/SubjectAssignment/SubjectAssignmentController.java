package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="subjectManagement/subjectAssignment")
public class SubjectAssignmentController {
    private final SubjectAssignmentService saService;

    @Autowired
    public SubjectAssignmentController(SubjectAssignmentService saService){this.saService = saService;}

    //GET
    @GetMapping
    public List<SubjectAssignment> getAllSubjectAssignments() {return saService.getAllSubjectAssignments();}
    @GetMapping(path="{saId}")
    public List<SubjectAssignment> getSubjectAssignmentsById(@PathVariable("saId") long saId){
        return saService.getSubjectAssignmentById(saId);
    }

    //POST
    @PostMapping
    public void addNewSubjectAssignment(@RequestBody SubjectAssignment sa){
        saService.addNewSubjectAssignment(sa);
    }

    //DELETE
    @DeleteMapping(path="{saId}")
    public void deleteSubjectAssignment(@PathVariable("saId") long id){
        saService.deleteSubjectAssignment(id);
    }

    //PUT
    @PutMapping(path="{saId}")
    public void updateSubjectAssignment(@PathVariable("saId") long id,
                                        @RequestParam(required = false) long subjectId,
                                        @RequestParam(required = false) long studentId,
                                        @RequestParam(required = false) long student2Id){
        saService.updateSubjectAssignment(id, subjectId, studentId, student2Id);
    }




}
