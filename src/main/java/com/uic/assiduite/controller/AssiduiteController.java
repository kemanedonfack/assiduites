package com.uic.assiduite.controller;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.AssiduiteService;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/assiduites")
public class AssiduiteController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AssiduiteService assiduiteService;


    @GetMapping("/initday")
    public ResponseEntity<Void> initDay() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        String periode = determinePeriode(now.toLocalTime());

        if (assiduiteService.isDayInit(currentDate, periode)) {
            // Une entrée existe déjà pour la date actuelle,
            // vous pouvez décider ici d'ignorer l'opération ou de lancer une exception
            return ResponseEntity.ok().build();
        }

        List<Utilisateurs> listEtudiant = utilisateurService.getEtudiants();
        for (Utilisateurs utilisateur : listEtudiant) {
            Assiduites assiduites = new Assiduites();
            assiduites.setPeriode(periode);
            assiduites.setDateJour(now.toLocalDate());
            assiduites.setNbreHeure(3);
            assiduites.setUtilisateurs(utilisateur);
            assiduiteService.save(assiduites);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/present/{matricule}")
    public ResponseEntity<Void> setPresent(@PathVariable String matricule) {
        Optional<Utilisateurs> getUser = utilisateurService.getUserByMatricule(matricule);

        if (getUser.isPresent()){

            LocalDate currentDate = LocalDate.now();
            LocalDateTime now = LocalDateTime.now();
            String periode = determinePeriode(now.toLocalTime());

            Optional<Assiduites> assiduites = assiduiteService.getAssiduiteByUtilisateurPeriodeDate(getUser.get(), currentDate, periode);
            if (assiduites.isPresent()){
                assiduites.get().setNbreHeure(0);
                assiduiteService.save(assiduites.get());
                return ResponseEntity.ok().build();
            } else {

                return ResponseEntity.notFound().build();
            }
        }else {

            return ResponseEntity.notFound().build();
        }

    }

    private String determinePeriode(LocalTime currentTime) {
        LocalTime startMorning = LocalTime.of(9, 0);
        LocalTime endMorning = LocalTime.of(12, 0);
        LocalTime startAfternoon = LocalTime.of(13, 0);
        LocalTime endAfternoon = LocalTime.of(16, 0);

        if (currentTime.isAfter(startMorning) && currentTime.isBefore(endMorning)) {
            return "9h - 12h";
        } else if (currentTime.isAfter(startAfternoon) && currentTime.isBefore(endAfternoon)) {
            return "13h - 16h";
        } else {
            return "Autre période";
        }
    }

}
