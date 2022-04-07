package com.example.BachelorProefBackend.SubjectManagement.TargetAudience;

import com.example.BachelorProefBackend.SubjectManagement.Campus.Campus;
import com.example.BachelorProefBackend.SubjectManagement.Campus.CampusService;
import com.example.BachelorProefBackend.SubjectManagement.Education.Education;
import com.example.BachelorProefBackend.SubjectManagement.Education.EducationService;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.Faculty;
import com.example.BachelorProefBackend.SubjectManagement.Faculty.FacultyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TargetAudienceConfig {

    @Bean
    CommandLineRunner commandLineRunner1(CampusService campusService, FacultyService facultyService, TargetAudienceService targetAudienceService, EducationService educationService){
        return args -> {
            Faculty inding = new Faculty("Industriële Ingenieurswetenschappen");
            Faculty geneeskunde = new Faculty("Geneeskunde");
            facultyService.addNewFaculty(inding);
            facultyService.addNewFaculty(geneeskunde);

            Education indIngElict = new Education("Master in de industriële wetenschappen: Elektronica-ICT");
            Education indIngChemie = new Education("Master in de industriële wetenschappen: chemie");
            Education bioinf = new Education("Master in de bio-informatica");
            educationService.addNewEducation(indIngElict);
            educationService.addNewEducation(indIngChemie);
            educationService.addNewEducation(bioinf);

            Campus technologiecampus = new Campus("Technologiecampus", "Gebroeders de Smetstraat 1, 9000 Gent");
            Campus groepT = new Campus("Groep T", "Andreas Vesaliusstraat 13, 3000 Leuven");
            Campus gasthuisberg = new Campus("Campus Gasthuisberg ON2", "Herestraat 49, 3001 Heverlee");
            campusService.addNewCampus(technologiecampus);
            campusService.addNewCampus(groepT);
            campusService.addNewCampus(gasthuisberg);


            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngElict));
            targetAudienceService.addNewTargetAudience(new TargetAudience(groepT, inding, indIngChemie));
            targetAudienceService.addNewTargetAudience(new TargetAudience(gasthuisberg, geneeskunde, bioinf));

        };
    }
}
