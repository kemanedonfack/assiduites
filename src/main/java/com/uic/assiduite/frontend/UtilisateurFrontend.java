package com.uic.assiduite.frontend;

import com.uic.assiduite.dto.UtilisateurDto;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.CustomUserDetailsService;
import com.uic.assiduite.service.RoleService;
import com.uic.assiduite.service.UtilisateurService;
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
        Roles role = roleService.getRoleByName(roleName);
        utilisateur.setRole(role);

        utilisateurService.createUser(utilisateur);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<Utilisateurs> users = utilisateurService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    
    @GetMapping("/add-user")
    public String showUserForm(Model model){
        List<Roles> listRoles = roleService.getAllRoles();
        model.addAttribute("listroles", listRoles);
        return "add-user";
    }

    @GetMapping("/edit-user/{id}")
    public String showEditForm(@PathVariable int id, Model model){
        Utilisateurs user = utilisateurService.getUtilisateurById(id);
        List<Roles> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "edit-user";
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
