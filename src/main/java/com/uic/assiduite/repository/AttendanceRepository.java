package com.uic.assiduite.repository;

import com.uic.assiduite.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author engome
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Attendance findAttendanceByStudent(int student_id);
}
