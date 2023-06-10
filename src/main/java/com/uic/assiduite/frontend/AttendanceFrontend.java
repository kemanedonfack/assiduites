package com.uic.assiduite.frontend;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.model.Roles;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.service.AssiduiteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.uic.assiduite.service.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 *
 * @author engome
 */
@Controller
public class AttendanceFrontend {

    @Autowired
    private AssiduiteService assiduiteService;
    @Autowired
    private FiliereService filiereService;

    @GetMapping("/attendances")
    public String showAttendance(Model model){
        List<Assiduites> assiduites = assiduiteService.getAllAssiduites();
        model.addAttribute("listassiduites", assiduites);
        List<Filieres> filieres = filiereService.getAllFilieres();
        model.addAttribute("listfiliere", filieres);
        return "attendances";
    }

    @PostMapping("/attendances/filter")
    public String registration(HttpServletRequest request, HttpServletResponse response, Model model){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = request.getParameter("date");
        String filiereCode = request.getParameter("filiereCode");
        LocalDate currentDate = LocalDate.parse(date, formatter);

        List<Assiduites> listassiduites = assiduiteService.getAssiduitesByFiliereAndDate(filiereCode, currentDate);

        model.addAttribute("listassiduites", listassiduites);
        List<Filieres> filieres = filiereService.getAllFilieres();
        model.addAttribute("listfiliere", filieres);
        return "attendances";
    }
}
