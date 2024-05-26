package com.system.demo.data.student.dao.impl;


import com.system.demo.appl.model.student.Student;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.student.dao.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.system.demo.data.utils.student.QueryConstants.*;


/**
 * An implementation class of the Student Data Access Object.
 *
 * */
public class StudentDaoImpl implements StudentDao {

    public static Logger LOGGER = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_ALL_STUDENTS_STATEMENT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(setStudent(rs));
            }
            return students;

        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Student database is empty.");
        return students;
    }

    @Override
    public Student getStudentByNumber(String studentId) {

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_STUDENT_BY_STUDENT_ID_STATEMENT);
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return setStudent(rs);
            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving Student with Student Number " + studentId + ": " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Student not found.");
        return null;
    }

    @Override
    public Student getStudentById(int id) {

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_STUDENT_BY_STUDENT_STATEMENT);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return setStudent(rs);
            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving Student with ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Student not found.");
        return null;
    }

    @Override
    public boolean addStudent(Student student) {

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement statement = con.prepareStatement(ADD_STUDENT_STATEMENT);
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getFirstName());
            statement.setString(4, student.getMiddleName());
            statement.setString(5, student.getSex());
            statement.setTimestamp(6, student.getBirthday());
            statement.setString(7, student.getBirthplace());
            statement.setString(8, student.getReligion());
            statement.setString(9, student.getEmail());
            statement.setString(10, student.getAddress());
            statement.setString(11, student.getContactNumber());
            statement.setString(12, student.getCitizenship());
            statement.setString(13, student.getCivilStatus());
            statement.setInt(14, student.getSectionId());
            int result = statement.executeUpdate();
            return result == 1 ? true : false;

        } catch (Exception e) {
            LOGGER.error("Error adding student failed " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> addStudents(ResultSet rs) {
        return addStudents(rs);
    }

    private Student setStudent(ResultSet rs) {
        try {
            Student student = new Student();
            student.setStudentId(rs.getString("student_number"));
            student.setLastName(rs.getString("last_name"));
            student.setFirstName(rs.getString("first_name"));
            student.setMiddleName(rs.getString("middle_name"));
            student.setSex(rs.getString("sex"));
            student.setBirthday(rs.getTimestamp("birthdate"));
            student.setBirthplace(rs.getString("birthplace"));
            student.setReligion(rs.getString("religion"));
            student.setEmail(rs.getString("email"));
            student.setAddress(rs.getString("address"));
            student.setContactNumber(rs.getString("contact_number"));
            student.setCitizenship(rs.getString("citizenship"));
            student.setCivilStatus(rs.getString("civil_status"));
            student.setSectionId(rs.getInt("section_section_id"));
            student.setId(rs.getInt("id"));

            return student;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("set Student failed.");
        return setStudent(rs);
    }

    @Override
    public boolean updateStudent(Student student) {

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement statement = con.prepareStatement(UPDATE_STATEMENT);
            statement.setString(1, student.getLastName());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getMiddleName());
            statement.setString(4, student.getSex());
            statement.setTimestamp(5, student.getBirthday());
            statement.setString(6, student.getBirthplace());
            statement.setString(7, student.getReligion());
            statement.setString(8, student.getEmail());
            statement.setString(9, student.getAddress());
            statement.setString(10, student.getContactNumber());
            statement.setString(11, student.getCitizenship());
            statement.setString(12, student.getCivilStatus());
            statement.setInt(13, student.getSectionId());
            statement.setString(14, student.getStudentId());
            int result = statement.executeUpdate();

            return result == 1;
        } catch (Exception e) {
            LOGGER.error("Error updating user with ID " + student.getStudentId() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student findStudentByEmail(String email) {
        Student student = null;
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement statement = con.prepareStatement(FIND_STUDENT_BY_EMAIL_STATEMENT);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                student = setStudent(rs);
            }
        } catch (Exception e) {
            LOGGER.error("Error finding student by email {}: {}", email, e.getMessage());
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_STUDENT_BY_STUDENT_ID_STATEMENT);
            stmt.setString(1, studentNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return setStudent(rs);
            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving Student with Student Number " + studentNumber + ": " + e.getMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Student not found.");
        return null;
    }

}
