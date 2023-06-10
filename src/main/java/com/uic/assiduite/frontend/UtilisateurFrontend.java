package com.uic.assiduite.frontend;

import com.google.zxing.WriterException;
import com.uic.assiduite.configuration.QRCodeGenerator;
import com.uic.assiduite.dto.UtilisateurDto;
import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.CustomUserDetailsService;
import com.uic.assiduite.service.FiliereService;
import com.uic.assiduite.service.RoleService;
import com.uic.assiduite.service.UtilisateurService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UtilisateurFrontend {
    
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FiliereService filiereService;

    @GetMapping("/")
    public String home(){
        return "dashboard";
    }

    @GetMapping("/enseignants")
    public String enseignants(Model model){
        Utilisateurs utilisateur = new Utilisateurs();
        List<Utilisateurs> utilisateurs = utilisateurService.getEnseignants();
        model.addAttribute("listenseignants", utilisateurs);
        model.addAttribute("utilisateur", utilisateur);
        return "enseignants";
    }
    @GetMapping("/etudiants")
    public String etudiants(Model model) throws IOException, WriterException {
        Utilisateurs utilisateur = new Utilisateurs();
        List<Utilisateurs> utilisateurs = utilisateurService.getEtudiants();
        List<Filieres> filieres = filiereService.getAllFilieres();

        List<String> qrCodeBase64List = new ArrayList<>();
        for (Utilisateurs etudiant : utilisateurs) {
            byte[] qrCodeBytes = QRCodeGenerator.generateQRCodeImage(etudiant.getMatricule(), 300, 300);
            String qrCodeBase64 = Base64.encodeBase64String(qrCodeBytes);
            qrCodeBase64List.add(qrCodeBase64);
        }

        model.addAttribute("qrCodeBase64List", qrCodeBase64List);
        model.addAttribute("listfiliere", filieres);
        model.addAttribute("listetudiants", utilisateurs);
        model.addAttribute("utilisateur", utilisateur);
        return "etudiants";
    }
    @GetMapping("/administrateurs")
    public String administrateurs(Model model){
        Utilisateurs utilisateur = new Utilisateurs();
        List<Utilisateurs> utilisateurs = utilisateurService.getAdministrateurs();
        model.addAttribute("listadministrateurs", utilisateurs);
        model.addAttribute("utilisateur", utilisateur);
        return "administrateurs";
    }

    @GetMapping("/index")
    public String home(Model model) {
        List<Utilisateurs> utilisateurs = utilisateurService.getAllUsers();
        model.addAttribute("utilisateurs", utilisateurs);
        return "dashboard";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        List<Roles> listRoles = roleService.getAllRoles();
        Utilisateurs utilisateur = new Utilisateurs();
        model.addAttribute("listroles", listRoles);
        model.addAttribute("utilisateur", utilisateur);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(HttpServletRequest request, HttpServletResponse response, @Valid @ModelAttribute("utilisateur") Utilisateurs utilisateur, BindingResult result, Model model){
        Utilisateurs existingUser = utilisateurService.getUserByEmail(utilisateur.getEmail());
        String roleName = request.getParameter("roleName");
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("utilisateur", utilisateur);
            return "/register";
        }

        if (roleName.equals("ETUDIANT")){
            String filiereCode = request.getParameter("filiereCode");
            Filieres filieres = filiereService.getFiliereByCode(filiereCode);
            utilisateur.setFilieres(filieres);
        }
        Roles role = roleService.getRoleByName(roleName);
        utilisateur.setRole(role);

        utilisateurService.createUser(utilisateur);
        return "dashboard";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<Utilisateurs> users = utilisateurService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (Exception e) {
            return "login";
        }

        Utilisateurs utilisateurs = utilisateurService.getUserByEmail(email);

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(utilisateurs.getEmail());

        return "redirect:/index";
    }




}
