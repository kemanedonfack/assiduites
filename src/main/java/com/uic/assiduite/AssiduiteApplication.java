package com.uic.assiduite;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.AssiduiteService;
import com.uic.assiduite.service.FiliereService;
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
	@Autowired
	private FiliereService filiereService;

	@Autowired
	private AssiduiteService assiduiteService;

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


		Filieres filieres = new Filieres(1, "MP5SIGL","génie logiciel");
		Filieres filieres2 = new Filieres(2, "MP4SIGL","génie logiciel");
		filiereService.createFiliere(filieres);
		filiereService.createFiliere(filieres2);


		Utilisateurs utilisateur = new Utilisateurs(1, "IUC11", "Admin", "Admin", "admin@gmail.com", "admin", administrateur, null);
		Utilisateurs utilisateur2 = new Utilisateurs(2, "IUC10", "Donfack", "Kemane", "kemane@gmail.com", "kemane", etudiant, filieres);
		Utilisateurs utilisateur3 = new Utilisateurs(3, "IUC12", "Eboa", "Julie", "julie@gmail.com", "julie", etudiant, filieres2);
		utilisateurService.createUser(utilisateur);
		utilisateurService.createUser(utilisateur2);
		utilisateurService.createUser(utilisateur3);

	}

}
