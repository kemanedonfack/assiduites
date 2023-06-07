package com.uic.assiduite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "assiduites")
public class Assiduites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateJour;
    private String periode;
    private int nbreHeure = 0;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateurs utilisateurs;
}
