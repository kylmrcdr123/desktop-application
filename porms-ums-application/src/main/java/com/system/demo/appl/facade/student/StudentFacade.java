/**
 * The com.system.demo.appl.facade.student package contains interfaces and classes
 * related to the student facade.
 */
package com.system.demo.appl.facade.student;

import com.system.demo.appl.model.student.Student;

import java.util.List;

/**
 * The StudentFacade interface defines methods for managing student data.
 */
public interface StudentFacade {

    /**
     * Retrieves all students from the database.
     *
     * @return A list of all students.
     */
    List<Student> getAllStudents();

    /**
     * Retrieves a student from the database with the specified student number.
     *
     * @param studentNumber The student number of the student to retrieve.
     * @return The student with the specified student number, or null if not found.
     */
    Student getStudentByNumber(String studentNumber);

    /**
     * Retrieves a student from the database with the specified ID.
     *
     * @param id The ID of the student to retrieve.
     * @return The student with the specified ID, or null if not found.
     */
    Student getStudentById(int id);

    /**
     * Retrieves a student from the database with the specified student number.
     *
     * @param studentNumber The student number of the student to retrieve.
     * @return The student with the specified student number, or null if not found.
     */
    Student getStudentByStudentNumber(String studentNumber);

    /**
     * Adds a student to the database.
     *
     * @param student The student to add.
     * @return True if the addition of the student is successful, false otherwise.
     */
    boolean addStudent(Student student);

    /**
     * Updates a student in the database.
     *
     * @param student The student to update.
     * @return True if the update of the student is successful, false otherwise.
     */
    boolean updateStudent(Student student);

    /**
     * Finds a student in the database by email.
     *
     * @param email The email of the student to find.
     * @return The student with the specified email, or null if not found.
     */
    Student findStudentByEmail(String email);
}
