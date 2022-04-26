package com.example.bachelorproefbackend.configuration.timing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path="timing")
public class TimingController {
    private final TimingService timingService;

    @Autowired
    public TimingController (TimingService timingService){
        this.timingService = timingService;
    }

    @GetMapping
    public Timing getTimingData() {
        return timingService.getTimingData();
    }

    @PutMapping
    public void setTimingData(@RequestParam(required = false) LocalDate endAddingSubjects,
                              @RequestParam(required = false) LocalDate endPreferredSubjects,
                              @RequestParam(required = false) LocalDate endFinalAllocation,
                              Authentication authentication) {
        timingService.setTimingData(endAddingSubjects, endPreferredSubjects, endFinalAllocation, authentication);
    }

}
