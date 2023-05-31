package com.uic.assiduite.service;


import com.uic.assiduite.model.Roles;
import com.uic.assiduite.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Roles> getRoleById(int id) {
        return roleRepository.findById(id);
    }

    public Roles createRole(Roles role) {
        return roleRepository.save(role);
    }

    public Roles getRoleByName(String nom){
        return roleRepository.findRolesByNom(nom);
    }

}
