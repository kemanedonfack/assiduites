package com.uic.assiduite.service;

import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.repository.FiliereRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FiliereServiceTest {

    @Mock
    private FiliereRepository filiereRepository;

    @InjectMocks
    private FiliereService filiereService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFilieres_ReturnsListOfFilieres() {
        // Arrange
        List<Filieres> filieresList = new ArrayList<>();
        when(filiereRepository.findAll()).thenReturn(filieresList);

        // Act
        List<Filieres> result = filiereService.getAllFilieres();

        // Assert
        assertEquals(filieresList, result);
        verify(filiereRepository, times(1)).findAll();
    }

    @Test
    void createFiliere_ReturnsSavedFiliere() {
        // Arrange
        Filieres filiereToSave = new Filieres();
        when(filiereRepository.save(filiereToSave)).thenReturn(filiereToSave);

        // Act
        Filieres savedFiliere = filiereService.createFiliere(filiereToSave);

        // Assert
        assertEquals(filiereToSave, savedFiliere);
        verify(filiereRepository, times(1)).save(filiereToSave);
    }

    @Test
    void getFiliereByCode_ReturnsFiliere() {
        // Arrange
        String code = "ABC";
        Filieres filiere = new Filieres();
        when(filiereRepository.findFilieresByCode(code)).thenReturn(filiere);

        // Act
        Filieres result = filiereService.getFiliereByCode(code);

        // Assert
        assertEquals(filiere, result);
        verify(filiereRepository, times(1)).findFilieresByCode(code);
    }

    @Test
    void countFiliere_ReturnsFiliereCount() {
        // Arrange
        long filiereCount = 10;
        when(filiereRepository.countFiliere()).thenReturn(filiereCount);

        // Act
        long result = filiereService.countFiliere();

        // Assert
        assertEquals(filiereCount, result);
        verify(filiereRepository, times(1)).countFiliere();
    }

    // Ajoutez d'autres tests pour les autres méthodes de la classe FiliereService
}
