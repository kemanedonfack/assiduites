package com.uic.assiduite.repository;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AssiduiteRepository extends JpaRepository<Assiduites, Integer> {
    boolean existsByDateJourAndPeriode(LocalDate dateJour, String periode);
    Optional<Assiduites> findByUtilisateursAndDateJourAndPeriode(Utilisateurs utilisateur, LocalDate dateJour, String periode);
    Optional<Assiduites> findByUtilisateursAndDateJour(Utilisateurs utilisateur, LocalDate dateJour);
    @Query("SELECT a FROM Assiduites a WHERE a.utilisateurs.filieres.code = :filiereCode AND a.dateJour = :dateJour")
    List<Assiduites> findAssiduitesByFiliereAndDate(@Param("filiereCode") String filiereCode, @Param("dateJour") LocalDate dateJour);
    List<Assiduites> findTop3AssiduitesByDateJour(LocalDate dateJour);
}
