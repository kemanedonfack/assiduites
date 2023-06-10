package com.uic.assiduite.service;

import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    public List<Filieres> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filieres createFiliere(Filieres filieres) {
        return filiereRepository.save(filieres);
    }

    public Filieres getFiliereByCode(String code){
        return filiereRepository.findFilieresByCode(code);
    }

    public Long countFiliere(){
        return filiereRepository.countFiliere();
    }
}

