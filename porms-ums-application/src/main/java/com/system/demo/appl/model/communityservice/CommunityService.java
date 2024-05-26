package com.system.demo.appl.model.communityservice;

import com.system.demo.appl.model.student.Student;

import java.sql.Timestamp;

/**
 * Represents a record of community service rendered by a student.
 */
public class CommunityService {
    private int id;
    private Student student;
    private int studentId;
    private String studentName;
    private Timestamp date_rendered;
    private int hours_completed;

    /**
     * Constructs a new CommunityService object with default values.
     */
    public CommunityService() {

    }

    /**
     * Constructs a new CommunityService object.
     *
     * @param id             The unique identifier of the community service record.
     * @param student        The student who rendered the service.
     * @param studentId      The ID of the student who rendered the service.
     * @param date_rendered  The timestamp indicating the date and time of service.
     * @param hours_completed The number of hours of community service rendered.
     */
    public CommunityService(int id, Student student, int studentId, Timestamp date_rendered, int hours_completed) {
        this.id = id;
        this.student = student;
        this.studentId = studentId;
        this.date_rendered = date_rendered;
        this.hours_completed = hours_completed;
    }

    /**
     * Retrieves the unique identifier of the community service record.
     *
     * @return The unique identifier of the community service record.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the community service record.
     *
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the student who rendered the community service.
     *
     * @return The student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student who rendered the community service.
     *
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Retrieves the ID of the student who rendered the community service.
     *
     * @return The ID of the student.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the ID of the student who rendered the community service.
     *
     * @param studentId The ID of the student to set.
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Retrieves the name of the student who rendered the community service.
     *
     * @return The name of the student.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the name of the student who rendered the community service.
     *
     * @param studentName The name of the student to set.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Retrieves the timestamp of when the community service was rendered.
     *
     * @return The timestamp indicating the date and time.
     */
    public Timestamp getDate_rendered() {
        return date_rendered;
    }

    /**
     * Sets the timestamp indicating the date and time when the community service was rendered.
     *
     * @param date_rendered The timestamp to set.
     */
    public void setDate_rendered(Timestamp date_rendered) {
        this.date_rendered = date_rendered;
    }

    /**
     * Retrieves the number of hours of community service rendered.
     *
     * @return The number of hours rendered.
     */
    public int getHours_completed() {
        return hours_completed;
    }

    /**
     * Sets the number of hours of community service rendered.
     *
     * @param hours_completed The number of hours to set.
     */
    public void setHours_completed(int hours_completed) {
        this.hours_completed = hours_completed;
    }
}
