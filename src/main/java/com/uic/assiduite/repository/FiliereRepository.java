package com.uic.assiduite.repository;

import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FiliereRepository extends JpaRepository<Filieres, Integer> {

    Filieres findFilieresByCode(String code);

    @Query("SELECT COUNT(f) FROM Filieres f")
    Long countFiliere();
}

