package com.uic.assiduite.frontend;

import com.google.zxing.WriterException;
import com.opencsv.CSVWriter;
import com.uic.assiduite.configuration.QRCodeGenerator;
import com.uic.assiduite.dto.UtilisateurDto;
import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.*;
import jdk.jshell.execution.Util;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.time.LocalDate;
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
    @Autowired
    private AssiduiteService assiduiteService;


    @GetMapping("/")
    public String home( HttpSession session, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateurs utilisateurs = utilisateurService.getUserByEmail(email);

        LocalDate currentDate = LocalDate.now();
        List<Assiduites> listassiduites = assiduiteService.getAssiduitesDate(currentDate);
        List<Utilisateurs> listutilisateurs = utilisateurService.getAllUsers();

        String filieres = filiereService.countFiliere().toString();
        String etudiants = utilisateurService.counEtudiant().toString();
        String enseignants = utilisateurService.countEnseignant().toString();
        String administrateur = utilisateurService.countAdministrateur().toString();

        model.addAttribute("listutilisateurs", listutilisateurs);
        model.addAttribute("listassiduites", listassiduites);
        model.addAttribute("nbreFilieres", filieres);
        model.addAttribute("nbreEtudiants", etudiants);
        model.addAttribute("nbreEnseignants", enseignants);
        model.addAttribute("nbreAdministrateur", administrateur);
        session.setAttribute("nomUtilisateur", utilisateurs.getNom());
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Supprimez toutes les données de session liées à l'utilisateur connecté
        session.invalidate();

        // Redirigez vers la page de connexion ou une autre page appropriée
        return "redirect:/login";
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

    @GetMapping("/administrateurs/export")
    public void exportAdministrateurs(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"administrateurs.csv\"");

        List<Utilisateurs> administrateurs = utilisateurService.getAdministrateurs();// Remplacez par votre propre logique de récupération des administrateurs

        CSVWriter csvWriter = new CSVWriter(response.getWriter());

        // Écriture de l'en-tête du fichier CSV
        String[] header = {"Nom", "Prénom", "Matricule", "Email", "Password"};
        csvWriter.writeNext(header);

        // Écriture des données des administrateurs dans le fichier CSV
        for (Utilisateurs administrateur : administrateurs) {
            String[] data = {administrateur.getNom(), administrateur.getPrenom(), administrateur.getMatricule(), administrateur.getEmail(), administrateur.getPassword()};
            csvWriter.writeNext(data);
        }

        csvWriter.close();
    }

    @PostMapping("/administrateurs/import")
    public String importCSV(MultipartFile file) throws IOException {
        List<Utilisateurs> administrateurs = parseCSV(file);

        for (Utilisateurs utilisateur : administrateurs) {
            utilisateurService.createUser(utilisateur);
        }

        return "redirect:/administrateurs"; // Rediriger vers la page des administrateurs après l'importation
    }

    private List<Utilisateurs> parseCSV(MultipartFile file) throws IOException {
        List<Utilisateurs> administrateurs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Utilisateurs administrateur = new Utilisateurs();
                    administrateur.setNom(data[0]);
                    administrateur.setPrenom(data[1]);
                    administrateur.setMatricule(data[2]);
                    administrateur.setEmail(data[3]);
                    administrateur.setPassword(data[4]);

                    administrateurs.add(administrateur);
                }
            }
        }
        return administrateurs;
    }



}
