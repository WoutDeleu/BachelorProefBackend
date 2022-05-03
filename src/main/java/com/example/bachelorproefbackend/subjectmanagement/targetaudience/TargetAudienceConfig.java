package com.example.bachelorproefbackend.subjectmanagement.targetaudience;

import com.example.bachelorproefbackend.subjectmanagement.campus.Campus;
import com.example.bachelorproefbackend.subjectmanagement.campus.CampusService;
import com.example.bachelorproefbackend.subjectmanagement.education.Education;
import com.example.bachelorproefbackend.subjectmanagement.education.EducationService;
import com.example.bachelorproefbackend.subjectmanagement.faculty.Faculty;
import com.example.bachelorproefbackend.subjectmanagement.faculty.FacultyService;
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
            Faculty recht = new Faculty("Rechtsgeleerdheid en Criminologische wetenschappen");
            facultyService.addNewFaculty(inding);
            facultyService.addNewFaculty(recht);
            facultyService.addNewFaculty(geneeskunde);

            Education indIngEICT = new Education("Master in de industriële wetenschappen: Elektronica-ICT");
            Education indIngCH = new Education("Master in de industriële wetenschappen: Chemie");
            Education indIngBC = new Education("Master in de industriële wetenschappen: Bio-chemie");
            Education indIngEM = new Education("Master in de industriële wetenschappen: Elektromechanica");
            Education indIngBK = new Education("Master in de industriële wetenschappen: Bouwkunde");
            Education indIngEN = new Education("Master in de industriële wetenschappen: Energie");
            Education indIngKV = new Education("Master in de industriële wetenschappen: Kunststofverwerking");

            Education bioinf = new Education("Bio-informatica");
            Education geneeskundeEducation = new Education("Geneeskunde");
            Education logo = new Education("Logopedische en audiologische wetenschappen");
            Education tand = new Education("Tandheelkunde");

            Education rechtEducation = new Education("Master in de rechten");
            Education crimi = new Education("Master in de criminologische wetenschappen");
            Education edumaat = new Education("Educatieve master in de maatschappijwetenschappen");

            educationService.addNewEducation(indIngEICT);
            educationService.addNewEducation(indIngCH);
            educationService.addNewEducation(indIngBK);
            educationService.addNewEducation(indIngEM);
            educationService.addNewEducation(indIngEN);

            educationService.addNewEducation(bioinf);
            educationService.addNewEducation(geneeskundeEducation);
            educationService.addNewEducation(logo);
            educationService.addNewEducation(tand);

            educationService.addNewEducation(rechtEducation);
            educationService.addNewEducation(crimi);
            educationService.addNewEducation(edumaat);


            Campus geel = new Campus("Campus Geel", "Kleinhoefstraat 4, 2440 Geel");
            Campus brugge = new Campus("Campus Brugge", "Spoorwegstraat 12, 8200 Brugge");
            Campus deNayer = new Campus("Campus De Nayer", "Jan Pieter de Nayerlaan 5, 2860 Sint-Katelijne-Waver");
            Campus technologiecampus = new Campus("Technologiecampus Gent", "Gebroeders de Smetstraat 1, 9000 Gent");
            Campus groepT = new Campus("Campus Groep T", "Andreas Vesaliusstraat 13, 3000 Leuven");

            Campus gasthuisberg = new Campus("Campus Gasthuisberg ON2", "Herestraat 49, 3001 Heverlee");
            Campus kulak = new Campus("Campus Kulak Kortrijk", "Etienne Sabbelaan 53, 8500 Kortrijk");

            Campus rechtenCampus = new Campus("Campus rechten Leuven", "Tiensestraat 41, 3000 Leuven");

            campusService.addNewCampus(technologiecampus);
            campusService.addNewCampus(brugge);
            campusService.addNewCampus(geel);
            campusService.addNewCampus(deNayer);
            campusService.addNewCampus(groepT);

            campusService.addNewCampus(gasthuisberg);
            campusService.addNewCampus(rechtenCampus);

            targetAudienceService.addNewTargetAudience(new TargetAudience(groepT, inding, indIngEICT));
            targetAudienceService.addNewTargetAudience(new TargetAudience(groepT, inding, indIngEM));
            targetAudienceService.addNewTargetAudience(new TargetAudience(groepT, inding, indIngCH));
            targetAudienceService.addNewTargetAudience(new TargetAudience(groepT, inding, indIngBC));
            targetAudienceService.addNewTargetAudience(new TargetAudience(deNayer, inding, indIngCH));

            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngEICT));
            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngBC));
            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngBK));
            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngCH));
            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngEM));
            targetAudienceService.addNewTargetAudience(new TargetAudience(technologiecampus, inding, indIngEN));

            targetAudienceService.addNewTargetAudience(new TargetAudience(brugge, inding, indIngBK));
            targetAudienceService.addNewTargetAudience(new TargetAudience(brugge, inding, indIngEM));
            targetAudienceService.addNewTargetAudience(new TargetAudience(brugge, inding, indIngEICT));
            targetAudienceService.addNewTargetAudience(new TargetAudience(brugge, inding, indIngEN));
            targetAudienceService.addNewTargetAudience(new TargetAudience(brugge, inding, indIngKV));

            targetAudienceService.addNewTargetAudience(new TargetAudience(geel, inding, indIngEM));
            targetAudienceService.addNewTargetAudience(new TargetAudience(geel, inding, indIngEICT));
            targetAudienceService.addNewTargetAudience(new TargetAudience(geel, inding, indIngEN));


            targetAudienceService.addNewTargetAudience(new TargetAudience(gasthuisberg, geneeskunde, geneeskundeEducation));
            targetAudienceService.addNewTargetAudience(new TargetAudience(kulak, geneeskunde, geneeskundeEducation));
            targetAudienceService.addNewTargetAudience(new TargetAudience(gasthuisberg, geneeskunde, logo));
            targetAudienceService.addNewTargetAudience(new TargetAudience(gasthuisberg, geneeskunde, tand));
            targetAudienceService.addNewTargetAudience(new TargetAudience(gasthuisberg, geneeskunde, bioinf));

            targetAudienceService.addNewTargetAudience(new TargetAudience(rechtenCampus, recht, crimi));
            targetAudienceService.addNewTargetAudience(new TargetAudience(rechtenCampus, recht, rechtEducation));
            targetAudienceService.addNewTargetAudience(new TargetAudience(rechtenCampus, recht, edumaat));

        };
    }
}
