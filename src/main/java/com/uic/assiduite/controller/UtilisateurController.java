package com.uic.assiduite.controller;

import com.uic.assiduite.model.AuthRequest;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

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
}
