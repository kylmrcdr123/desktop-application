/**
 * The com.system.demo.appl.facade.prefect.communityservice package contains interfaces and classes
 * related to the prefect community service facade.
 */
package com.system.demo.appl.facade.prefect.communityservice;

import com.system.demo.appl.model.communityservice.CommunityService;
import com.system.demo.appl.model.student.Student;

import java.util.List;

/**
 * The CommunityServiceFacade interface defines methods for managing community service data.
 */
public interface CommunityServiceFacade {

    /**
     * Retrieves all community services from the database.
     *
     * @return A list of all community services.
     */
    List<CommunityService> getAllCs();

    /**
     * Retrieves a community service from the database with the specified ID.
     *
     * @param id The ID of the community service.
     * @return The community service with the specified ID, or null if not found.
     */
    CommunityService getCsById(int id);

    /**
     * Retrieves all community service records associated with a specific student.
     *
     * @param studentId The ID of the student.
     * @return A list of all community service records associated with the specific student.
     */
    List<CommunityService> getAllCsByStudent(Student studentId);

    /**
     * Renders a community service.
     *
     * @param cs The community service to render.
     * @return True if rendering is successful, false otherwise.
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
    List<CommunityService> getCommunityServicesByStudentName(String lastName, String firstName);
}
