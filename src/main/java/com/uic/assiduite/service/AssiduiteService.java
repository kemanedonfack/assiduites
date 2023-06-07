package com.uic.assiduite.service;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.AssiduiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssiduiteService {

    @Autowired
    private AssiduiteRepository assiduiteRepository;

    public Assiduites save(Assiduites assiduites) {

        return assiduiteRepository.save(assiduites);
    }

}
