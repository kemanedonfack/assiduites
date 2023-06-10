package com.uic.assiduite.frontend;

import com.uic.assiduite.model.Assiduites;
import com.uic.assiduite.model.Attendance;
import com.uic.assiduite.model.Filieres;
import com.uic.assiduite.service.AssiduiteService;
import com.uic.assiduite.service.AttendanceService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.uic.assiduite.service.FiliereService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

}
