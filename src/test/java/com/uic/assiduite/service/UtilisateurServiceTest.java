package com.uic.assiduite.service;

import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<Utilisateurs> usersList = new ArrayList<>();
        when(utilisateurRepository.findAll()).thenReturn(usersList);

        // Act
        List<Utilisateurs> result = utilisateurService.getAllUsers();

        // Assert
        assertEquals(usersList, result);
        verify(utilisateurRepository, times(1)).findAll();
    }

    @Test
    void getEtudiants_ReturnsListOfEtudiants() {
        // Arrange
        List<Utilisateurs> etudiantsList = new ArrayList<>();
        when(utilisateurRepository.getEtudiants()).thenReturn(etudiantsList);

        // Act
        List<Utilisateurs> result = utilisateurService.getEtudiants();

        // Assert
        assertEquals(etudiantsList, result);
        verify(utilisateurRepository, times(1)).getEtudiants();
    }

    @Test
    void getAdministrateurs_ReturnsListOfAdministrateurs() {
        // Arrange
        List<Utilisateurs> administrateursList = new ArrayList<>();
        when(utilisateurRepository.getAdministrateurs()).thenReturn(administrateursList);

        // Act
        List<Utilisateurs> result = utilisateurService.getAdministrateurs();

        // Assert
        assertEquals(administrateursList, result);
        verify(utilisateurRepository, times(1)).getAdministrateurs();
    }

    @Test
    void getEnseignants_ReturnsListOfEnseignants() {
        // Arrange
        List<Utilisateurs> enseignantsList = new ArrayList<>();
        when(utilisateurRepository.getEnseignants()).thenReturn(enseignantsList);

        // Act
        List<Utilisateurs> result = utilisateurService.getEnseignants();

        // Assert
        assertEquals(enseignantsList, result);
        verify(utilisateurRepository, times(1)).getEnseignants();
    }

    @Test
    void getUserById_ReturnsOptionalUser() {
        // Arrange
        int userId = 1;
        Utilisateurs user = new Utilisateurs();
        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<Utilisateurs> result = utilisateurService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(utilisateurRepository, times(1)).findById(userId);
    }

    @Test
    void getUserByEmail_ReturnsUser() {
        // Arrange
        String email = "test@example.com";
        Utilisateurs user = new Utilisateurs();
        when(utilisateurRepository.findUtilisateursByEmail(email)).thenReturn(user);

        // Act
        Utilisateurs result = utilisateurService.getUserByEmail(email);

        // Assert
        assertEquals(user, result);
        verify(utilisateurRepository, times(1)).findUtilisateursByEmail(email);
    }

    @Test
    void getUserByMatricule_ReturnsUser() {
        // Arrange
        String matricule = "123456";
        Utilisateurs user = new Utilisateurs();
        when(utilisateurRepository.findUtilisateursByMatricule(matricule)).thenReturn(user);

        // Act
        Utilisateurs result = utilisateurService.getUserByMatricule(matricule);

        // Assert
        assertEquals(user, result);
        verify(utilisateurRepository, times(1)).findUtilisateursByMatricule(matricule);
    }

 /*   @Test
    void createUser_ReturnsSavedUser() {
        // Arrange
        Utilisateurs userToSave = new Utilisateurs();
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(userToSave.getPassword())).thenReturn(encodedPassword);
        when(utilisateurRepository.save(userToSave)).thenReturn(userToSave);

        // Act
        Utilisateurs savedUser = utilisateurService.createUser(userToSave);

        // Assert
        assertEquals(userToSave, savedUser);
        assertEquals(encodedPassword, savedUser.getPassword());
        verify(passwordEncoder, times(1)).encode(userToSave.getPassword());
        verify(utilisateurRepository, times(1)).save(userToSave);
    }*/

    @Test
    void updateUser_ReturnsUpdatedUser() {
        // Arrange
        int userId = 1;
        Utilisateurs userToUpdate = new Utilisateurs();
        when(utilisateurRepository.existsById(userId)).thenReturn(true);
        when(utilisateurRepository.save(userToUpdate)).thenReturn(userToUpdate);

        // Act
        Optional<Utilisateurs> updatedUser = utilisateurService.updateUser(userId, userToUpdate);

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(userToUpdate, updatedUser.get());
        assertEquals(userId, updatedUser.get().getId());
        verify(utilisateurRepository, times(1)).existsById(userId);
        verify(utilisateurRepository, times(1)).save(userToUpdate);
    }

    @Test
    void updateUser_ReturnsEmptyOptionalIfUserDoesNotExist() {
        // Arrange
        int userId = 1;
        Utilisateurs userToUpdate = new Utilisateurs();
        when(utilisateurRepository.existsById(userId)).thenReturn(false);

        // Act
        Optional<Utilisateurs> updatedUser = utilisateurService.updateUser(userId, userToUpdate);

        // Assert
        assertFalse(updatedUser.isPresent());
        verify(utilisateurRepository, times(1)).existsById(userId);
        verify(utilisateurRepository, never()).save(userToUpdate);
    }

    @Test
    void deleteUser_ReturnsTrueIfUserExists() {
        // Arrange
        int userId = 1;
        when(utilisateurRepository.existsById(userId)).thenReturn(true);

        // Act
        boolean result = utilisateurService.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(utilisateurRepository, times(1)).existsById(userId);
        verify(utilisateurRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_ReturnsFalseIfUserDoesNotExist() {
        // Arrange
        int userId = 1;
        when(utilisateurRepository.existsById(userId)).thenReturn(false);

        // Act
        boolean result = utilisateurService.deleteUser(userId);

        // Assert
        assertFalse(result);
        verify(utilisateurRepository, times(1)).existsById(userId);
        verify(utilisateurRepository, never()).deleteById(userId);
    }

    @Test
    void counEtudiant_ReturnsEtudiantCount() {
        // Arrange
        long etudiantCount = 10;
        when(utilisateurRepository.counEtudiant()).thenReturn(etudiantCount);

        // Act
        long result = utilisateurService.counEtudiant();

        // Assert
        assertEquals(etudiantCount, result);
        verify(utilisateurRepository, times(1)).counEtudiant();
    }

    @Test
    void countEnseignant_ReturnsEnseignantCount() {
        // Arrange
        long enseignantCount = 5;
        when(utilisateurRepository.countEnseignant()).thenReturn(enseignantCount);

        // Act
        long result = utilisateurService.countEnseignant();

        // Assert
        assertEquals(enseignantCount, result);
        verify(utilisateurRepository, times(1)).countEnseignant();
    }

    @Test
    void countAdministrateur_ReturnsAdministrateurCount() {
        // Arrange
        long administrateurCount = 2;
        when(utilisateurRepository.countAdministrateur()).thenReturn(administrateurCount);

        // Act
        long result = utilisateurService.countAdministrateur();

        // Assert
        assertEquals(administrateurCount, result);
        verify(utilisateurRepository, times(1)).countAdministrateur();
    }
}

