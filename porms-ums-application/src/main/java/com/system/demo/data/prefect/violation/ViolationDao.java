package com.system.demo.data.prefect.violation;


import com.system.demo.appl.model.student.Student;
import com.system.demo.appl.model.violation.Violation;

import java.util.List;

/**
 * This is the interface for the ViolationDao
 */
public interface ViolationDao {
    /**
     * This retrieves all Violation from the database
     * @return list of all the Violation
     */
    List<Violation> getAllViolation();

    /**
     * This retrieves all Violation from the database with a specific student ID
     * @param studentId is the id of the Student that has a Violation Record
     * @return list of all the Violation
     */
    List<Violation> getAllViolationByStudent(Student studentId);

    /**
     * This gets a Violation from the database with a specific ID
     * @param id is the id of the Violation
     * @return the Violation with specific ID
     */
    Violation getViolationByID(int id);

    /**
     * This updates a Violation in the database
     * @param violation is the Violation to update
     * @return true if update of Violation is successful
     */
    boolean updateViolation(Violation violation);

    /**
     * This adds a Violation in the database
     * @param violation is the Violation to add
     * @return true if adding of Violation is successful
     */
    boolean addViolation(Violation violation);

    List<Violation> findByStudentName(String lastName, String firstName);

}
