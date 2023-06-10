package com.uic.assiduite.service;

import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.RoleRepository;
import com.uic.assiduite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateurs> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    public List<Utilisateurs> getEtudiants() {
        return utilisateurRepository.getEtudiants();
    }

    public Optional<Utilisateurs> getUserById(int id) {
        return utilisateurRepository.findById(id);
    }
    
    public Utilisateurs getUtilisateurById(int id) {
        return utilisateurRepository.findUtilisateursById(id);
    }

    public Utilisateurs getUserByEmail(String email) {
        return utilisateurRepository.findUtilisateursByEmail(email);
    }

    public Utilisateurs getUserByMatricule(String matricule){
        return utilisateurRepository.findUtilisateursByMatricule(matricule);
    }

    public Utilisateurs createUser(Utilisateurs user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return utilisateurRepository.save(user);
    }

    public Optional<Utilisateurs> updateUser(int id, Utilisateurs user) {
        if (!utilisateurRepository.existsById(id)) {
            return Optional.empty();
        }
        user.setId(id);
        return Optional.of(utilisateurRepository.save(user));
    }

    public boolean deleteUser(int id) {
        if (!utilisateurRepository.existsById(id)) {
            return false;
        }
        utilisateurRepository.deleteById(id);
        return true;
    }
}
