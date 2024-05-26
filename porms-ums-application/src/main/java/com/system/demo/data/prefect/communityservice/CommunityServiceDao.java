package com.system.demo.data.prefect.communityservice;

import com.system.demo.appl.model.communityservice.CommunityService;
import com.system.demo.appl.model.student.Student;

import java.util.List;

/**
 * Interface for accessing and managing community service records in the database.
 */
public interface CommunityServiceDao {

    /**
     * Retrieves all community service records from the database.
     *
     * @return A list of all community service records.
     */
    List<CommunityService> getAllCs();

    /**
     * Retrieves all community service records from the database associated with a specific student.
     *
     * @param studentId The ID of the student.
     * @return A list of all community service records associated with the specific student.
     */
    List<CommunityService> getAllCsByStudent(Student studentId);

    /**
     * Retrieves a specific community service record by its ID from the database.
     *
     * @param id The ID of the community service record to retrieve.
     * @return The community service record corresponding to the given ID, or null if not found.
     */
    CommunityService getCsById(int id);

    /**
     * Records a new community service entry into the database.
     *
     * @param cs The community service record to insert.
     * @return True if the insertion is successful, false otherwise.
     */
    boolean renderCs(CommunityService cs);

    /**
     * Retrieves a community service by the username of the user associated with it.
     *
     * @param username The username of the user associated with the community service.
     * @return The community service associated with the specified username, or null if not found.
     */
    CommunityService getCommunityServiceByUsername(String username);

    /**
     * Retrieves community services by the last name and first name of the student associated with them.
     *
     * @param lastName  The last name of the student.
     * @param firstName The first name of the student.
     * @return A list of community services associated with the specified student name, or an empty list if not found.
     */
    List<CommunityService> findByStudentName(String lastName, String firstName);
}
