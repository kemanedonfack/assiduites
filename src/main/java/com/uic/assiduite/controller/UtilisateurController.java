package com.uic.assiduite.controller;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.AuthRequest;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.AssiduiteService;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AssiduiteService assiduiteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public List<Utilisateurs> getAllUsers() {
        return utilisateurService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateurs> getUserById(@PathVariable int id) {
        Optional<Utilisateurs> optionalUser = utilisateurService.getUserById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Utilisateurs> login(@RequestBody AuthRequest request) {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Utilisateurs utilisateurs = utilisateurService.getUserByEmail(request.getEmail());

                return new  ResponseEntity<>(utilisateurs, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping
    public Utilisateurs createUser(@RequestBody Utilisateurs user) {
        return utilisateurService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateurs> updateUser(@PathVariable int id, @RequestBody Utilisateurs user) {
        Optional<Utilisateurs> optionalUser = utilisateurService.updateUser(id, user);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = utilisateurService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/present/{matricule}")
    public ResponseEntity<Void> setPresent(@PathVariable String matricule) {
        Optional<Utilisateurs> getUser = utilisateurService.getUserByMatricule(matricule);

        if (getUser.isPresent()){
            LocalDateTime now = LocalDateTime.now();
            LocalDate currentDate = now.toLocalDate();
            //LocalTime test = LocalTime.from(LocalDateTime.of(2023, 02, 13, 10, 02,22));
            // Appeler la fonction pour déterminer la période à laquelle la date appartient
            String periode = determinePeriode(now.toLocalTime());

            // Faire quelque chose avec la période (par exemple, l'afficher)
            System.out.println("Période : " + periode+ " Date :"+currentDate);

            Assiduites assiduites = new Assiduites();
            assiduites.setPeriode(periode);
            assiduites.setDateJour(currentDate);
            assiduites.setUtilisateurs(getUser.get());
            assiduiteService.save(assiduites);

            return ResponseEntity.ok().build();
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
