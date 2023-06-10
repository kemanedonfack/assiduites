package com.uic.assiduite.repository;

import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateurs, Integer> {

    Utilisateurs findUtilisateursByEmail(String email);
    
    Utilisateurs findUtilisateursByMatricule(String matricule);

    @Query("SELECT u FROM Utilisateurs u JOIN u.role r WHERE r.nom = 'ETUDIANT'")
    List<Utilisateurs> getEtudiants();

    @Query("SELECT u FROM Utilisateurs u JOIN u.role r WHERE r.nom = 'ADMINISTRATEUR'")
    List<Utilisateurs> getAdministrateurs();

    @Query("SELECT u FROM Utilisateurs u JOIN u.role r WHERE r.nom = 'ENSEIGNANT'")
    List<Utilisateurs> getEnseignants();

}
