package com.uic.assiduite;

import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.RoleService;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
public class AssiduiteApplication implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UtilisateurService utilisateurService;

	public static void main(String[] args) {
		SpringApplication.run(AssiduiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Roles etudiant = new Roles(1, "ETUDIANT");
		Roles enseignant = new Roles(2, "ENSEIGNANT");
		Roles administrateur = new Roles(3, "ADMINISTRATEUR");

		roleService.createRole(etudiant);
		roleService.createRole(enseignant);
		roleService.createRole(administrateur);

		Utilisateurs utilisateur = new Utilisateurs(1, "A4AHJ", "Admin", "Admin", "admin@gmail.com", "admin", administrateur);
		utilisateurService.createUser(utilisateur);

	}

}
