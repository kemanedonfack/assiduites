package com.uic.assiduite;

import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.RoleService;
import com.uic.assiduite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		Roles role1 = new Roles(1, "ETUDIANT");
		Roles role2 = new Roles(2, "ENSEIGNANT");
		Roles role3 = new Roles(3, "ADMINISTRATEUR");

		roleService.createRole(role1);
		roleService.createRole(role2);
		roleService.createRole(role3);

		//Utilisateurs utilisateur = new Utilisateurs(1, "A4AHJ", "Donfack", "Kemane", "kemanedonfack5@gamil.com", "admin", "admin", role1);
		//utilisateurService.createUser(utilisateur);

	}

}
