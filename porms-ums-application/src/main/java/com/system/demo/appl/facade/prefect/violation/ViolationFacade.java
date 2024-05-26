package com.system.demo.appl.facade.prefect.violation;


import com.system.demo.appl.model.student.Student;
import com.system.demo.appl.model.violation.Violation;

import java.util.List;

/**
 * An interface to the Violation Facade.
 */

public interface ViolationFacade {

    /**
     * Retrieves all Violation from the database.
     *
     * @return list of all Violation.
     * */
    List<Violation> getAllViolation();

    /**
     * Retrieves a Violation from the database with specified id.
     *
     * @param id the id of the Violation.
     * @return the Violation.
     * */
    Violation getViolationByID(int id);

    /**
     * Retrieves all Violation from the database with specified student id.
     *
     * @param studentId the id of the students.
     * @return the Violation.
     * */
    List<Violation> getAllViolationByStudent(Student studentId);

    /**
     * Updates a Student in the database.
     *
     * @param violation  Violation to update.
     * @return true if update is successful.
     * */
   boolean updateViolation(Violation violation);

    /**
     * Adds a Student to the database.
     *
     * @param violation Violation to add.
     * @return true if adding is successful.
     * */
    boolean addViolation(Violation violation);

    List<Violation> getViolationByStudentName(String lastName, String firstName);



}