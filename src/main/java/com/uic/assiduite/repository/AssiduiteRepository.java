package com.uic.assiduite.repository;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface AssiduiteRepository extends JpaRepository<Assiduites, Integer> {
    boolean existsByDateJourAndPeriode(LocalDate dateJour, String periode);
    Optional<Assiduites> findByUtilisateursAndDateJourAndPeriode(Utilisateurs utilisateur, LocalDate dateJour, String periode);
    Optional<Assiduites> findByUtilisateursAndDateJour(Utilisateurs utilisateur, LocalDate dateJour);

}
