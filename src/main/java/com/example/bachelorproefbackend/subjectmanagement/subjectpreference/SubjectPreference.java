package com.example.bachelorproefbackend.subjectmanagement.subjectpreference;

import com.example.bachelorproefbackend.subjectmanagement.subject.Subject;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table
@Entity
@NoArgsConstructor
public class SubjectPreference {
    @Id
    @SequenceGenerator(name="preference_sequence", sequenceName = "preference_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preference_sequence")
    private long id;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private UserEntity student;
    private int index;

    public SubjectPreference(Subject subject, UserEntity student, int index) {
        this.subject = subject;
        this.student = student;
        this.index = index;
    }

    @Override
    public String toString() {
        return "SubjectPreference{" +
                "id=" + id +
                ", subject=" + subject +
                ", student=" + student +
                ", index=" + index +
                '}';
    }


}

