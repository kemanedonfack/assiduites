package com.uic.assiduite.controller;

import com.google.zxing.WriterException;
import com.uic.assiduite.model.AuthRequest;
import com.uic.assiduite.model.Utilisateurs;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UtilisateurControllerTest {

    @InjectMocks
    private UtilisateurController utilisateurController;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    public void testGetAllUsers_ShouldReturnAllUsers() throws WriterException, IOException {
        // Arrange
        List<Utilisateurs> mockUsers = Arrays.asList(new Utilisateurs(), new Utilisateurs());

        when(utilisateurService.getAllUsers()).thenReturn(mockUsers);

        // Act
        List<Utilisateurs> result = utilisateurController.getAllUsers();

        // Assert
        assertEquals(mockUsers.size(), result.size());
    }*/

    @Test
    public void testGetUserById_WithValidId_ShouldReturnUser() {
        // Arrange
        int id = 1;
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setId(id);
        Optional<Utilisateurs> optionalUser = Optional.of(mockUser);

        when(utilisateurService.getUserById(id)).thenReturn(optionalUser);

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.getUserById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    public void testGetUserById_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        int id = 1;
        Optional<Utilisateurs> optionalUser = Optional.empty();

        when(utilisateurService.getUserById(id)).thenReturn(optionalUser);

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.getUserById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testLogin_WithValidCredentials_ShouldReturnUser() {
        // Arrange
        AuthRequest mockRequest = new AuthRequest("test@example.com", "password");
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setEmail(mockRequest.getEmail());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(utilisateurService.getUserByEmail(mockRequest.getEmail())).thenReturn(mockUser);

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.login(mockRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    public void testLogin_WithInvalidCredentials_ShouldReturnUnauthorized() {
        // Arrange
        AuthRequest mockRequest = new AuthRequest("test@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.login(mockRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateUser_ShouldReturnCreatedUser() {
        // Arrange
        Utilisateurs mockUser = new Utilisateurs();

        when(utilisateurService.createUser(mockUser)).thenReturn(mockUser);

        // Act
        Utilisateurs result = utilisateurController.createUser(mockUser);

        // Assert
        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    public void testUpdateUser_WithValidId_ShouldReturnUpdatedUser() {
        // Arrange
        int id = 1;
        Utilisateurs mockUser = new Utilisateurs();
        mockUser.setId(id);
        Optional<Utilisateurs> optionalUser = Optional.of(mockUser);

        when(utilisateurService.updateUser(id, mockUser)).thenReturn(optionalUser);

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.updateUser(id, mockUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    public void testUpdateUser_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        int id = 1;
        Utilisateurs mockUser = new Utilisateurs();
        Optional<Utilisateurs> optionalUser = Optional.empty();

        when(utilisateurService.updateUser(id, mockUser)).thenReturn(optionalUser);

        // Act
        ResponseEntity<Utilisateurs> response = utilisateurController.updateUser(id, mockUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteUser_WithExistingId_ShouldReturnNoContent() {
        // Arrange
        int id = 1;

        when(utilisateurService.deleteUser(id)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = utilisateurController.deleteUser(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteUser_WithNonExistingId_ShouldReturnNotFound() {
        // Arrange
        int id = 1;

        when(utilisateurService.deleteUser(id)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = utilisateurController.deleteUser(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

