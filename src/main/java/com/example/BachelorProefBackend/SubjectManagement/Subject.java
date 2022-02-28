package com.example.BachelorProefBackend.SubjectManagement;
import javax.persistence.*;

@Entity
@Table
public class Subject {
    @Id
    @SequenceGenerator(name="subject_sequence", sequenceName = "subject_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
    private long id;
    private String name;
    private String description;
    private long companyId;
    private long targetAudienceId;
    private long tagId;

    public Subject() { }

    public Subject(String name, String description, long companyId, long targetAudienceId, long tagId) {
        this.name = name;
        this.description = description;
        this.companyId = companyId;
        this.targetAudienceId = targetAudienceId;
        this.tagId = tagId;
    }

    public long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {
        return description;
    }
    public long getCompanyId() {
        return companyId;
    }
    public long getTargetAudienceId() {
        return targetAudienceId;
    }
    public long getTagId() {
        return tagId;
    }

    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
    public void setTargetAudienceId(long targetAudienceId) {
        this.targetAudienceId = targetAudienceId;
    }
    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='"+name+
                ", description='" + description + '\'' +
                ", companyId=" + companyId +
                ", targetAudienceId=" + targetAudienceId +
                ", tagId=" + tagId +
                '}';
    }
}
