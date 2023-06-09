package com.uic.assiduite.service;

import com.uic.assiduite.model.Attendance;
import com.uic.assiduite.model.Utilisateurs;
import com.uic.assiduite.repository.AttendanceRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author engome
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UtilisateurService utilisateurService;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceByStudent(int student_id) {
        return attendanceRepository.findAttendanceByStudent(student_id);
    }

    public Attendance createAttendance(String student_id, Attendance a) {
        Utilisateurs user = utilisateurService.getUserByMatricule(student_id);
        a.setStudent(user);
        a.setTimeStatus(0);
        a.setToday(new Date());
        return attendanceRepository.save(a);
    }

    public Attendance updateAttendance(String student_id, Attendance att) {
        Utilisateurs user = utilisateurService.getUserByMatricule(student_id);
        Attendance attendanceToUpdate = attendanceRepository.findAttendanceByStudent(user.getId());
        if(attendanceToUpdate.getTimeStatus() == 0){
            attendanceToUpdate.setEndTime(att.getEndTime());
            attendanceToUpdate.setTimeStatus(1);
        }
        return attendanceRepository.save(attendanceToUpdate);
    }
}
