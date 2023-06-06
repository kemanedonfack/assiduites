package com.uic.assiduite.repository;

import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateurs, Integer> {

    Utilisateurs findUtilisateursByEmail(String email);
}
