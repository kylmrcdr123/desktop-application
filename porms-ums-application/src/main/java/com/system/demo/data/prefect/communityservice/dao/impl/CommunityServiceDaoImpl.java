package com.system.demo.data.prefect.communityservice.dao.impl;


import com.system.demo.StudentInfoMgtApplication;
import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.model.communityservice.CommunityService;
import com.system.demo.appl.model.student.Student;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.prefect.communityservice.CommunityServiceDao;
import com.system.demo.data.utils.prefect.QueryConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.system.demo.data.utils.prefect.QueryConstants.*;


/**
 * Implements the CommunityServiceDao interface to interact with the database for managing community service records.
 */

public class CommunityServiceDaoImpl implements CommunityServiceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityServiceDaoImpl.class);


    @Override
    public List<CommunityService> getAllCs() {
        List<CommunityService> communityServices = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(GET_ALL_CS_STATEMENT);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CommunityService communityService = new CommunityService();
                communityService.setId(resultSet.getInt("id"));

                StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                StudentFacade studentFacade = app.getStudentFacade();
                Student student = studentFacade.getStudentById(resultSet.getInt("student_id"));
                communityService.setStudent(student);

                communityService.setDate_rendered(resultSet.getTimestamp("date_rendered"));
                communityService.setHours_completed(resultSet.getInt("hours_completed"));
                communityServices.add(communityService);
            }
            if (communityServices.isEmpty()) {
                LOGGER.debug("Community Service database is empty.");
            } else {
                LOGGER.info("Community Service retrieved successfully.");
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving all community services: " + ex.getMessage());
            ex.printStackTrace();
        }
        return communityServices;
    }


    @Override
    public List<CommunityService> getAllCsByStudent(Student student) {
        List<CommunityService> communityServices = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(GET_ALL_CS_BY_STUDENT_ID_STATEMENT)) {

            statement.setInt(1, student.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CommunityService communityService = new CommunityService();
                    communityService.setId(resultSet.getInt("id"));

                    communityService.setStudent(student);

                    communityService.setDate_rendered(resultSet.getTimestamp("date_rendered"));
                    communityService.setHours_completed(resultSet.getInt("hours_completed"));
                    communityServices.add(communityService);
                }
                if (communityServices.isEmpty()) {
                    LOGGER.debug("Community Service database is empty.");
                } else {
                    LOGGER.info("Community Service retrieved successfully.");
                }
            } catch (SQLException ex) {
                LOGGER.warn("Error retrieving community services: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error preparing statement: " + ex.getMessage());
            ex.printStackTrace();
        }
        return communityServices;
    }

    @Override
    public CommunityService getCsById(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_CS_BY_ID_STATEMENT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idNum = rs.getInt("id");

                    StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                    StudentFacade studentFacade = app.getStudentFacade();
                    Student student = studentFacade.getStudentById(rs.getInt("student_id"));

                    Timestamp date_rendered = rs.getTimestamp("date_rendered");
                    int hours_completed = rs.getInt("hours_completed");

                    String studentName = student.getFirstName() + " " + student.getLastName();

                    return new CommunityService(idNum, student, student.getId(), date_rendered, hours_completed);

                } else {
                    LOGGER.info("No Community Service found with ID: " + id);
                    LOGGER.debug("Community Service not found.");
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving Community Service with ID " + id + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean renderCs(CommunityService cs) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(RENDER_CS_STATEMENT)) {
            stmt.setInt(1, cs.getStudent().getId());
            stmt.setTimestamp(2, cs.getDate_rendered());
            stmt.setInt(3, cs.getHours_completed());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            LOGGER.warn("Error rendering Community Service: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public CommunityService getCommunityServiceByUsername(String username) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_CS_BY_USERNAME_STATEMENT)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ID"); // Retrieve ID from ResultSet

                    int studentId = rs.getInt("STUDENT_ID");
                    StudentInfoMgtApplication app = new StudentInfoMgtApplication();
                    StudentFacade studentFacade = app.getStudentFacade();
                    Student student = studentFacade.getStudentById(studentId);

                    Timestamp date_rendered = rs.getTimestamp("DATE_RENDERED");
                    int hoursCompleted = rs.getInt("HOURS_COMPLETED");

                   CommunityService communityService = new CommunityService(id, student, studentId, date_rendered, hoursCompleted);
                    return communityService;
                } else {
                    LOGGER.warn("No violation found with ID: " + username);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving violation with ID " + username + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        LOGGER.debug("Violation not found.");
        return null;
    }

@Override
    public List<CommunityService> findByStudentName(String lastName, String firstName) {
        List<CommunityService> communityServices = new ArrayList<>();
        String query = QueryConstants.FIND_COMMUNITY_SERVICE_BY_STUDENT_NAME_STATEMENT;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CommunityService communityService = new CommunityService();

                    communityService.setId(rs.getInt("id"));
                    communityService.setDate_rendered(rs.getTimestamp("date_rendered"));
                    communityService.setHours_completed(rs.getInt("hours_completed"));
                    communityService.setStudentId(rs.getInt("student_id"));

                    communityService.setStudentName(rs.getString("student_last_name") + ", " + rs.getString("student_first_name"));

                    Student student = new Student();
                    student.setId(rs.getInt("STUDENT_ID"));
                    student.setFirstName(rs.getString("student_first_name"));
                    student.setLastName(rs.getString("student_last_name"));

                    communityService.setStudent(student);

                    communityServices.add(communityService);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn("Error retrieving community services by student name: " + ex.getMessage());
            ex.printStackTrace();
        }
        return communityServices;
    }
}




