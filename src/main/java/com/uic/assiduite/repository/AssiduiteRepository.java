package com.uic.assiduite.repository;

import com.uic.assiduite.model.Assiduites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssiduiteRepository extends JpaRepository<Assiduites, Integer> {
}
