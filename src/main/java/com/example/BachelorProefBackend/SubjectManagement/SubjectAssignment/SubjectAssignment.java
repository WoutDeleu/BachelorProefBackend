package com.example.BachelorProefBackend.SubjectManagement.SubjectAssignment;

import javax.persistence.*;

@Entity
@Table
public class SubjectAssignment {
    @Id
    @SequenceGenerator(name="subjectAssignment_sequence", sequenceName = "subjectAssignment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectAssignment_sequence")
    private long id;
    private long subjectId;
    private long studentId;
    private long student2Id;

    public SubjectAssignment() { }

    public SubjectAssignment(long subjectId, long studentId, long student2Id) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.student2Id = student2Id;
    }
}
