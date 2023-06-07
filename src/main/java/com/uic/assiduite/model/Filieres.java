package com.uic.assiduite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "filieres")
public class Filieres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String code;

    private String libelle;
}
