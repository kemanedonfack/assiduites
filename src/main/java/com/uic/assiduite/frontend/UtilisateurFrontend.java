package com.uic.assiduite.frontend;

import com.uic.assiduite.dto.UtilisateurDto;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.CustomUserDetailsService;
import com.uic.assiduite.service.RoleService;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/index")
    public String home(Model model) {
        List<Utilisateurs> utilisateurs = utilisateurService.getAllUsers();
        model.addAttribute("utilisateurs", utilisateurs);
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UtilisateurDto utilisateur = new UtilisateurDto();
        model.addAttribute("utilisateur", utilisateur);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("utilisateur") Utilisateurs utilisateur, BindingResult result, Model model){
        Utilisateurs existingUser = utilisateurService.getUserByEmail(utilisateur.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("utilisateur", utilisateur);
            return "/register";
        }

        utilisateurService.createUser(utilisateur);
        return "redirect:/register?success";
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

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable int id, Model model) {
        Optional<Utilisateurs> optionalUser = utilisateurService.getUserById(id);
        if (optionalUser.isPresent()) {
            Utilisateurs user = optionalUser.get();
            List<Roles> roles = roleService.getAllRoles();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "edit-user";
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("utilisateurs")
    public String createUser(Utilisateurs user) {
        utilisateurService.createUser(user);
        return "redirect:/index";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, Utilisateurs user) {
        Optional<Utilisateurs> optionalUser = utilisateurService.updateUser(id, user);
        return optionalUser.map(u -> "redirect:/index").orElse("redirect:/index");
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        utilisateurService.deleteUser(id);
        return "redirect:/users";
    }
    
}
