package com.example.bachelorproefbackend.configuration.timing;

import com.example.bachelorproefbackend.configuration.exceptions.NotAllowedException;
import com.example.bachelorproefbackend.usermanagement.role.Role;
import com.example.bachelorproefbackend.usermanagement.role.RoleRepository;
import com.example.bachelorproefbackend.usermanagement.user.UserEntity;
import com.example.bachelorproefbackend.usermanagement.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Slf4j
@Service
@Transactional
public class TimingService {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public TimingService(UserService userService, RoleRepository roleRepository){
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public Timing getTimingData(){
        return Timing.getInstance();
    }

    public void setTimingData(LocalDate endAddingSubjects,
                              LocalDate endPreferredSubjects,
                              LocalDate endFinalAllocation,
                              Authentication authentication){
        Timing timing = Timing.getInstance();
        if(endAddingSubjects==null) endAddingSubjects=timing.getEndAddingSubjects();
        if(endPreferredSubjects==null) endAddingSubjects=timing.getEndPreferredSubjects();
        if(endFinalAllocation==null) endFinalAllocation=timing.getEndFinalAllocation();
        if(endAddingSubjects.isAfter(endPreferredSubjects) || endPreferredSubjects.isAfter(endFinalAllocation)){
            throw new NotAllowedException("The order of the dates is not correct.");
        }
        timing.setEndAddingSubjects(endAddingSubjects);
        timing.setEndPreferredSubjects(endPreferredSubjects);
        timing.setEndFinalAllocation(endFinalAllocation);
    }

}
