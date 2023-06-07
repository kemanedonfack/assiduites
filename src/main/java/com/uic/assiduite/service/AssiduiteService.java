package com.uic.assiduite.service;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.AssiduiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AssiduiteService {

    @Autowired
    private AssiduiteRepository assiduiteRepository;

    public Assiduites save(Assiduites assiduites) {

        return assiduiteRepository.save(assiduites);
    }

    public boolean isDayInit(LocalDate dateJour, String periode){
        return assiduiteRepository.existsByDateJourAndPeriode(dateJour, periode);
    }

    public List<Assiduites> getAssiduites() {
        return assiduiteRepository.findAll();
    }

    public Optional<Assiduites> getAssiduiteByUtilisateurPeriodeDate(Utilisateurs utilisateur, LocalDate dateJour, String periode){
        return assiduiteRepository.findByUtilisateursAndDateJourAndPeriode(utilisateur, dateJour, periode);
    }

}
