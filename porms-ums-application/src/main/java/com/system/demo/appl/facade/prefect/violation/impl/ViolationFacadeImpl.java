package com.system.demo.appl.facade.prefect.violation.impl;


import com.system.demo.appl.facade.prefect.violation.ViolationFacade;
import com.system.demo.appl.model.student.Student;
import com.system.demo.appl.model.violation.Violation;
import com.system.demo.data.prefect.violation.ViolationDao;

import java.util.List;

/**
 * An implementation class of the Offence Facade.
 */

public class ViolationFacadeImpl implements ViolationFacade {
    private ViolationDao violationDao;

    /**
     * Constructs a new ViolationFacadeImpl with the provided ViolationDao.
     *
     * @param violationDao The data access object for managing violation data.
     */
    public ViolationFacadeImpl(ViolationDao violationDao) {
        this.violationDao = violationDao;
    }

    /**
     * Retrieves all violations from the database.
     *
     * @return A list of all violations.
     * @throws RuntimeException If an error occurs while retrieving violations.
     */
    public List<Violation> getAllViolation() throws RuntimeException {
        try {
            return violationDao.getAllViolation();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }

    @Override
    public Violation getViolationByID(int id) {
        return violationDao.getViolationByID(id);
    }

    @Override
    public List<Violation> getAllViolationByStudent(Student studentId) {
        try {
            return violationDao.getAllViolationByStudent(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Violation: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean updateViolation(Violation violation) throws RuntimeException{
        boolean result = false;
        try {
            Violation targetViolation = getViolationByID(violation.getId());
            if (targetViolation == null) {
                throw new Exception("Violation to update not found.");
            }
            result = violationDao.updateViolation(violation);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                throw new RuntimeException(errorMessage);
            } else {
                throw new RuntimeException("An error occurred while updating the Violation information.");
            }
        }
        return result;
    }


    @Override
    public boolean addViolation(Violation violation) throws RuntimeException {
        try {
            return violationDao.addViolation(violation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add Violation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Violation> getViolationByStudentName(String lastName, String firstName) {
        return violationDao.findByStudentName(lastName, firstName);
    }
}