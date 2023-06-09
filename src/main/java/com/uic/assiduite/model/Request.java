package com.uic.assiduite.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author engome
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "justifications")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private int status;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Utilisateurs student;
}
