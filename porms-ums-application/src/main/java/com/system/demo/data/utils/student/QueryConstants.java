package com.system.demo.data.utils.student;

/**
 * Inside the QueryConstants class contains constants representing SQL queries for managing employee information.
 */

public class QueryConstants {
    /**
     * SQL query to retrieves all students from the database.
     */
    public static final String GET_ALL_STUDENTS_STATEMENT = "SELECT * FROM STUDENT";

    /**
     * SQL query to retrieves a student by their Student Number from the database.
     */
    public static final String GET_STUDENT_BY_STUDENT_ID_STATEMENT = "SELECT * FROM STUDENT WHERE STUDENT_NUMBER = ?";

    public static final String GET_STUDENT_BY_STUDENT_STATEMENT = "SELECT * FROM STUDENT WHERE ID = ?";


    /**
     * SQL query that adds a new student to the database.
     */
    public static final String ADD_STUDENT_STATEMENT = "INSERT INTO STUDENT (student_number, last_name, first_name,  middle_name, sex, birthdate, birthplace, religion, email, address, contact_number, citizenship, civil_status, section_section_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to updates an existing student in the database.
     */
    public static final String UPDATE_STATEMENT = "UPDATE student" +
            " SET last_name = ?, first_name = ?,  middle_name = ?, sex = ?, birthdate = ?, birthplace = ?, religion = ?, email = ?, address = ?, contact_number = ?, citizenship = ?, civil_status = ?, section_section_id = ?"
            + "  WHERE student_number = ?";
    /**
     * SQL query to find an existing email of the student in the database.
     */
    public static final String FIND_STUDENT_BY_EMAIL_STATEMENT = "SELECT * FROM STUDENT WHERE email = ?";
}

