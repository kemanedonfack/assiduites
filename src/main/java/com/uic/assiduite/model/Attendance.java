package com.uic.assiduite.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "assiduites")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date today;
    @NotEmpty
    private String periode;
    private String startTime;
    private String endTime;
    private int timeStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateurs users;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Utilisateurs student;

}
