package com.uic.assiduite.service;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.AssiduiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssiduiteServiceTest {

    @Mock
    private AssiduiteRepository assiduiteRepository;

    @InjectMocks
    private AssiduiteService assiduiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Assiduites_ReturnsSavedAssiduites() {
        // Arrange
        Assiduites assiduitesToSave = new Assiduites();
        when(assiduiteRepository.save(assiduitesToSave)).thenReturn(assiduitesToSave);

        // Act
        Assiduites savedAssiduites = assiduiteService.save(assiduitesToSave);

        // Assert
        assertEquals(assiduitesToSave, savedAssiduites);
        verify(assiduiteRepository, times(1)).save(assiduitesToSave);
    }

    @Test
    void isDayInit_ExistingAssiduites_ReturnsTrue() {
        // Arrange
        LocalDate dateJour = LocalDate.now();
        String periode = "morning";
        when(assiduiteRepository.existsByDateJourAndPeriode(dateJour, periode)).thenReturn(true);

        // Act
        boolean result = assiduiteService.isDayInit(dateJour, periode);

        // Assert
        assertTrue(result);
        verify(assiduiteRepository, times(1)).existsByDateJourAndPeriode(dateJour, periode);
    }

    @Test
    void isDayInit_NonExistingAssiduites_ReturnsFalse() {
        // Arrange
        LocalDate dateJour = LocalDate.now();
        String periode = "morning";
        when(assiduiteRepository.existsByDateJourAndPeriode(dateJour, periode)).thenReturn(false);

        // Act
        boolean result = assiduiteService.isDayInit(dateJour, periode);

        // Assert
        assertFalse(result);
        verify(assiduiteRepository, times(1)).existsByDateJourAndPeriode(dateJour, periode);
    }

    @Test
    void getAllAssiduites_ReturnsListOfAssiduites() {
        // Arrange
        List<Assiduites> assiduitesList = new ArrayList<>();
        when(assiduiteRepository.findAll()).thenReturn(assiduitesList);

        // Act
        List<Assiduites> result = assiduiteService.getAllAssiduites();

        // Assert
        assertEquals(assiduitesList, result);
        verify(assiduiteRepository, times(1)).findAll();
    }

    @Test
    void getAssiduiteByUtilisateurPeriodeDate_ReturnsOptionalOfAssiduites() {
        // Arrange
        Utilisateurs utilisateur = new Utilisateurs();
        LocalDate dateJour = LocalDate.now();
        String periode = "morning";
        Optional<Assiduites> assiduitesOptional = Optional.of(new Assiduites());
        when(assiduiteRepository.findByUtilisateursAndDateJourAndPeriode(utilisateur, dateJour, periode)).thenReturn(assiduitesOptional);

        // Act
        Optional<Assiduites> result = assiduiteService.getAssiduiteByUtilisateurPeriodeDate(utilisateur, dateJour, periode);

        // Assert
        assertEquals(assiduitesOptional, result);
        verify(assiduiteRepository, times(1)).findByUtilisateursAndDateJourAndPeriode(utilisateur, dateJour, periode);
    }

    // Ajoutez d'autres tests pour les autres méthodes de la classe AssiduiteService
}
