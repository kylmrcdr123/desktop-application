package com.system.demo.data.prefect.violation.dao.impl;


import com.system.demo.EmployeeInfoMgtApplication;
import com.system.demo.StudentInfoMgtApplication;
import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.facade.prefect.offense.OffenseFacade;
import com.system.demo.appl.facade.prefect.offense.impl.OffenseFacadeImpl;
import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.model.employee.Employee;
import com.system.demo.appl.model.offense.Offense;
import com.system.demo.appl.model.student.Student;
import com.system.demo.appl.model.violation.Violation;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.prefect.offense.OffenseDao;
import com.system.demo.data.prefect.offense.dao.impl.OffenseDaoImpl;
import com.system.demo.data.prefect.violation.ViolationDao;
import com.system.demo.data.utils.prefect.QueryConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.system.demo.data.utils.prefect.QueryConstants.*;

/**
 * This is an implementation class of the ViolationDao
 */
public class ViolationDaoImpl implements ViolationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViolationDaoImpl.class);

    @Override
    public Violation getViolationByID(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_VIOLATION_BY_ID_STATEMENT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int studentId = rs.getInt("student_id");

                    StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                    StudentFacade studentFacade = app.getStudentFacade();
                    Student student = studentFacade.getStudentById(studentId);

                    int offenseId = rs.getInt("offense_id");
                    OffenseDao offenseDao = new OffenseDaoImpl();
                    OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                    Offense offense = offenseFacade.getOffenseByID(offenseId);

                    int warningNumber = rs.getInt("warning_number");
                    int csHours = rs.getInt("cs_hours");
                    String disciplinaryAction = rs.getString("disciplinary_action");
                    Timestamp dateOfNotice = rs.getTimestamp("date_of_notice");

                    int approvedById = rs.getInt("approved_by_id");
                    EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                    EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                    Employee approvedBy = employeeFacade.getById(approvedById);

                    Violation violation = new Violation(id, student, offense, csHours, warningNumber, disciplinaryAction, dateOfNotice, approvedBy, student.getLastName() + ", " + student.getFirstName(), offenseId, approvedById, studentId);
                    return violation;
                } else {
                    LOGGER.warn("No violation found with ID: " + id);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving violation with ID " + id + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Violation not found.");
        return null;
    }

    @Override
    public boolean updateViolation(Violation violation) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_VIOLATION_STATEMENT)) {

            Offense offense = violation.getOffense();
            Student student = violation.getStudent();
            Employee employee = violation.getApprovedBy();

            stmt.setInt(1, student.getId());
            stmt.setInt(2, offense.getId());
            stmt.setInt(3, violation.getWarningNum());
            stmt.setInt(4, violation.getCommServHours());
            stmt.setString(5, violation.getDisciplinaryAction());
            stmt.setTimestamp(6, violation.getDateOfNotice());
            stmt.setInt(7, employee.getId());
            stmt.setInt(8, violation.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.warn("Error updating Violation with ID " + violation.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Violation> getAllViolation() {
        List<Violation> violations = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(GET_ALL_VIOLATION_STATEMENT);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Violation violation = new Violation();
                violation.setId(resultSet.getInt("id"));

                StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                StudentFacade studentFacade = app.getStudentFacade();
                Student student = studentFacade.getStudentById(resultSet.getInt("student_id"));
                violation.setStudent(student);

                OffenseDao offenseDao = new OffenseDaoImpl();
                OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                Offense offense = offenseFacade.getOffenseByID(resultSet.getInt("offense_id"));
                violation.setOffense(offense);

                violation.setWarningNum(resultSet.getInt("warning_number"));
                violation.setCommServHours(resultSet.getInt("cs_hours"));
                violation.setDisciplinaryAction(resultSet.getString("disciplinary_action"));
                violation.setDateOfNotice(resultSet.getTimestamp("date_of_notice"));

                EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                Employee employee = employeeFacade.getById(resultSet.getInt("approved_by_id"));
                violation.setApprovedBy(employee);

                violations.add(violation);
            }
            LOGGER.info("Violation retrieved successfully.");
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving all Violation: " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Violation database is empty.");
        return violations;
    }

    @Override
    public List<Violation> getAllViolationByStudent(Student student) {
        List<Violation> violations = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(GET_ALL_VIOLATION_BY_STUDENT_ID_STATEMENT)) {

            statement.setInt(1, student.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Violation violation = new Violation();
                    violation.setId(resultSet.getInt("id"));

                    // Set student
                    violation.setStudent(student);

                    // Retrieve offense
                    OffenseDao offenseDao = new OffenseDaoImpl();
                    OffenseFacade offenseFacade = new OffenseFacadeImpl(offenseDao);
                    Offense offense = offenseFacade.getOffenseByID(resultSet.getInt("offense_id"));
                    violation.setOffense(offense);

                    violation.setWarningNum(resultSet.getInt("warning_number"));
                    violation.setCommServHours(resultSet.getInt("cs_hours"));
                    violation.setDisciplinaryAction(resultSet.getString("disciplinary_action"));
                    violation.setDateOfNotice(resultSet.getTimestamp("date_of_notice"));

                    // Retrieve approved by employee
                    EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
                    EmployeeFacade employeeFacade = appl.getEmployeeFacade();
                    Employee employee = employeeFacade.getById(resultSet.getInt("approved_by_id"));
                    violation.setApprovedBy(employee);

                    violations.add(violation);
                }
                LOGGER.info("Violations retrieved successfully.");
            } catch (SQLException ex) {
                LOGGER.warn("Error retrieving all violations: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error preparing statement: " + ex.getMessage());
            ex.printStackTrace();
        }
        return violations;
    }




    @Override
    public boolean addViolation(Violation violation) {
        if (violation == null || violation.getOffense() == null || violation.getStudent() == null) {
            LOGGER.warn("Invalid violation object: " + violation);
            return false;
        }

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_VIOLATION_STATEMENT)) {

            Offense offense = violation.getOffense();
            Student student = violation.getStudent();
            Employee employee = violation.getApprovedBy();

            stmt.setInt(1, student.getId());
            stmt.setInt(2, offense.getId());
            stmt.setInt(3, violation.getWarningNum());
            stmt.setInt(4, violation.getCommServHours());
            stmt.setString(5, violation.getDisciplinaryAction());
            stmt.setTimestamp(6, violation.getDateOfNotice());
            stmt.setInt(7, employee.getId());

            int affectedRows = stmt.executeUpdate();
            //return affectedRows > 0;
            return true;
        } catch (SQLException ex) {
            LOGGER.warn("Error adding violation: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Violation> findByStudentName(String lastName, String firstName) {
        List<Violation> violations = new ArrayList<>();
        String query = QueryConstants.FIND_VIOLATION_BY_STUDENT_NAME_STATEMENT;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Violation violation = new Violation();

                    violation.setCommServHours(rs.getInt("CS_HOURS"));
                    violation.setWarningNum(rs.getInt("WARNING_NUMBER"));
                    violation.setApprovedById(rs.getInt("APPROVED_BY_ID"));
                    violation.setDateOfNotice(rs.getTimestamp("DATE_OF_NOTICE"));
                    violation.setId(rs.getInt("ID"));
                    violation.setOffenseId(rs.getInt("OFFENSE_ID"));
                    violation.setStudentId(rs.getInt("STUDENT_ID"));
                    violation.setDisciplinaryAction(rs.getString("DISCIPLINARY_ACTION"));

                    violation.setStudentName(rs.getString("student_last_name") + ", " + rs.getString("student_first_name"));

                    Student student = new Student();
                    student.setId(rs.getInt("STUDENT_ID"));
                    student.setFirstName(rs.getString("student_first_name"));
                    student.setLastName(rs.getString("student_last_name"));

                    violation.setStudent(student);

                    violations.add(violation);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving violations by student name: " + ex.getMessage());
            ex.printStackTrace();
        }
        return violations;
    }
}
