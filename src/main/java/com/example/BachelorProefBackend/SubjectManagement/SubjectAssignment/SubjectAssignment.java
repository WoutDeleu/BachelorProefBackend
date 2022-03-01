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

    public long getId() {return id;}
    public long getSubjectId() {
        return subjectId;
    }
    public long getStudentId() {
        return studentId;
    }
    public long getStudent2Id() {
        return student2Id;
    }

    public void setId(long id) {this.id = id;}
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
    public void setStudent2Id(long student2Id) {
        this.student2Id = student2Id;
    }

    @Override
    public String toString() {
        return "SubjectAssignment{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", studentId=" + studentId +
                ", student2Id=" + student2Id +
                '}';
    }
}
