package com.example.bachelorproefbackend.configuration.timing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @PostMapping
    public void setTimingData(@RequestParam(required = false) String endAddingSubjects,
                              @RequestParam(required = false) String endPreferredSubjects,
                              @RequestParam(required = false) String endFinalAllocation,
                              Authentication authentication) {
        timingService.setTimingData(stringToDate(endAddingSubjects), stringToDate(endPreferredSubjects), stringToDate(endFinalAllocation), authentication);
    }

    public LocalDate stringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

}
