package com.uic.assiduite.frontend;

import com.uic.assiduite.model.Attendance;
import com.uic.assiduite.service.AttendanceService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private AttendanceService attendanceService;

    @GetMapping("/attendance")
    public String showAttendance(Model model){
        List<Attendance> attendances = attendanceService.getAllAttendance();
        model.addAttribute("attendance", attendances);
        return "attendances/attendance";
    }
    
    @PostMapping("/attendance/save")
    public String saveAttendance(HttpServletRequest request, HttpServletResponse response, @Valid @ModelAttribute("attendance") Attendance attendance, BindingResult result, Model model){
        String matricule = request.getParameter("matricule");
        String decodeMatricule = new String(Base64.decodeBase64(matricule.getBytes()));
        attendanceService.createAttendance(decodeMatricule, attendance);
        return "redirect:/attendances/attendance?success";
    }
}
