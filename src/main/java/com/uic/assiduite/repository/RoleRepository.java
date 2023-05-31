package com.uic.assiduite.repository;

import com.uic.assiduite.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Roles findRolesByNom(String nom);
}
