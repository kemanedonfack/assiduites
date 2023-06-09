package com.uic.assiduite.controller;

import com.uic.assiduite.model.Attendance;
import com.uic.assiduite.service.AttendanceService;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author engome
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    
    @GetMapping
    public List<Attendance> getAllAttendance(){
        return attendanceService.getAllAttendance();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceByStudent(@PathVariable int id){
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(id));
    }
    
    @PostMapping("/save/{matricule}")
    public Attendance createAttendance(@PathVariable String matricule, @RequestBody Attendance att){
        String decodeMatricule = new String(Base64.decodeBase64(matricule.getBytes()));
        return attendanceService.createAttendance(decodeMatricule,att);
    }
    
    @PutMapping("/save/{matricule}")
    public ResponseEntity<Attendance> updateUser(@PathVariable String matricule, @RequestBody Attendance att) {
        return ResponseEntity.ok(attendanceService.updateAttendance(matricule, att));
    }
}
