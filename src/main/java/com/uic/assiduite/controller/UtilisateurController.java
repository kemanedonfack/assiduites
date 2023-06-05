package com.uic.assiduite.controller;

import com.google.zxing.WriterException;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.UtilisateurService;
import com.uic.assiduite.utils.QRCodeGenerator;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService UtilisateurService;

    @GetMapping
    public List<Utilisateurs> getAllUsers() throws WriterException, IOException {
        // Générer le qrcode pour les étudiants
        List<Utilisateurs> users = UtilisateurService.getAllUsers();
        for(Utilisateurs user : users){
            if("ETUDIANT".equals(user.getRole().getNom())){
                QRCodeGenerator.QRCodeGenerator(user);
            }
        }
        return UtilisateurService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateurs> getUserById(@PathVariable int id) {
        Optional<Utilisateurs> optionalUser = UtilisateurService.getUserById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateurs createUser(@RequestBody Utilisateurs user) {
        return UtilisateurService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateurs> updateUser(@PathVariable int id, @RequestBody Utilisateurs user) {
        Optional<Utilisateurs> optionalUser = UtilisateurService.updateUser(id, user);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = UtilisateurService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
