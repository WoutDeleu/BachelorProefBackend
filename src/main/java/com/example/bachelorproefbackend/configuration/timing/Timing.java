package com.example.bachelorproefbackend.configuration.timing;

import com.example.bachelorproefbackend.configuration.exceptions.ResourceNotFoundException;

import java.time.LocalDate;


//Only one instance of this class allowed
public class Timing {
    private static LocalDate endAddingSubjects;
    private static LocalDate endPreferredSubjects;
    private static LocalDate endFinalAllocation;

    private static final Timing instance = new Timing();
    private Timing() {}//constructor private
    public static Timing getInstance() {return instance;}

    public LocalDate getEndAddingSubjects() {
        return endAddingSubjects;
    }
    public LocalDate getEndPreferredSubjects() {
        return endPreferredSubjects;
    }
    public LocalDate getEndFinalAllocation() {
        return endFinalAllocation;
    }

    public void setEndAddingSubjects(LocalDate endAddingSubjects) {
        Timing.endAddingSubjects = endAddingSubjects;
    }
    public void setEndPreferredSubjects(LocalDate endPreferredSubjects) {
        Timing.endPreferredSubjects = endPreferredSubjects;
    }
    public void setEndFinalAllocation(LocalDate endFinalAllocation) {
        Timing.endFinalAllocation = endFinalAllocation;
    }

    public boolean isBeforeDeadlineAddingSubjects(){
        if(endAddingSubjects == null) return true;
        return LocalDate.now().isBefore(endAddingSubjects);
    }

    public boolean isBeforeDeadlinePreferredSubjects(){
        if(endPreferredSubjects == null) return true;
        return LocalDate.now().isBefore(endPreferredSubjects);
    }

    public boolean isBeforeDeadlineFinalAllocation(){
        if(endFinalAllocation == null) return true;
        return LocalDate.now().isBefore(endFinalAllocation);
    }

    /* PRODUCTION CODE
    public boolean isBeforeDeadlineAddingSubjects(){
        if(endAddingSubjects == null) throw new ResourceNotFoundException("The deadlines must be set before checking if this function is allowed.");
        return LocalDate.now().isBefore(endAddingSubjects);
    }

    public boolean isBeforeDeadlinePreferredSubjects(){
        if(endPreferredSubjects == null) throw new ResourceNotFoundException("The deadlines must be set before checking if this function is allowed.");
        return LocalDate.now().isBefore(endPreferredSubjects);
    }

    public boolean isBeforeDeadlineFinalAllocation(){
        if(endFinalAllocation == null) throw new ResourceNotFoundException("The deadlines must be set before checking if this function is allowed.");
        return LocalDate.now().isBefore(endFinalAllocation);
    }
     */
}
