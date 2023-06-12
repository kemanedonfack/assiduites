package com.uic.assiduite.controller;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.AssiduiteService;
import com.uic.assiduite.service.UtilisateurService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssiduiteControllerTest {

    @InjectMocks
    private AssiduiteController assiduiteController;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private AssiduiteService assiduiteService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    public void testInitDay_WithValidData_ShouldReturnOk() {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        String periode = "9h - 12h";
        List<Utilisateurs> mockEtudiants = Arrays.asList(new Utilisateurs(), new Utilisateurs());

        when(utilisateurService.getEtudiants()).thenReturn(mockEtudiants);
        when(assiduiteService.isDayInit(currentDate, periode)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = assiduiteController.initDay();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

   /* @Test
    public void testSetPresent_WithValidMatricule_ShouldReturnOk() {
        // Arrange
        String matricule = "123456";
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setMatricule(matricule);
        LocalDate currentDate = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        String periode = "9h - 12h";
        Optional<Assiduites> mockAssiduites = Optional.of(new Assiduites());

        when(utilisateurService.getUserByMatricule(matricule)).thenReturn(mockUser);
        when(assiduiteService.getAssiduiteByUtilisateurPeriodeDate(mockUser, currentDate, periode)).thenReturn(mockAssiduites);

        // Act
        ResponseEntity<Void> response = assiduiteController.setPresent(matricule);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    public void testGetAssiduite_WithValidMatriculeAndDate_ShouldReturnAssiduite() {
        // Arrange
        String matricule = "123456";
        String dateString = "2023-06-12";
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setMatricule(matricule);
        LocalDate currentDate = LocalDate.parse(dateString);
        Optional<Assiduites> mockAssiduites = Optional.of(new Assiduites());

        when(utilisateurService.getUserByMatricule(matricule)).thenReturn(mockUser);
        when(assiduiteService.getAssiduiteByUtilisateurDate(mockUser, currentDate)).thenReturn(mockAssiduites);

        // Act
        ResponseEntity<Assiduites> response = assiduiteController.getAssiduite(matricule, dateString);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAssiduite_WithInvalidMatricule_ShouldReturnNotFound() {
        // Arrange
        String matricule = "123456";
        String dateString = "2023-06-12";
        Utilisateurs mockUser = null;

        when(utilisateurService.getUserByMatricule(matricule)).thenReturn(mockUser);

        // Act
        ResponseEntity<Assiduites> response = assiduiteController.getAssiduite(matricule, dateString);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAssiduite_WithValidMatriculeAndInvalidDate_ShouldReturnNotFound() {
        // Arrange
        String matricule = "123456";
        String dateString = "2023-06-12";
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setMatricule(matricule);
        LocalDate currentDate = LocalDate.parse(dateString);
        Optional<Assiduites> mockAssiduites = Optional.empty();

        when(utilisateurService.getUserByMatricule(matricule)).thenReturn(mockUser);
        when(assiduiteService.getAssiduiteByUtilisateurDate(mockUser, currentDate)).thenReturn(mockAssiduites);

        // Act
        ResponseEntity<Assiduites> response = assiduiteController.getAssiduite(matricule, dateString);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

