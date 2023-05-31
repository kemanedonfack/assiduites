package com.uic.assiduite.service;

import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateurs utilisateur = utilisateurRepository.findUtilisateursByEmail(email);
        if (utilisateur != null) {

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));

            return new org.springframework.security.core.userdetails.User(utilisateur.getEmail(), utilisateur.getPassword(), authorities);

        }else {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

    }

}


