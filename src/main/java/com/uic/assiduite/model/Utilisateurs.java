package com.uic.assiduite.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisateurs")
public class Utilisateurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String matricule;
    @NotEmpty
    private String nom;
    @NotEmpty
    private String prenom;
    @Email
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;
}
