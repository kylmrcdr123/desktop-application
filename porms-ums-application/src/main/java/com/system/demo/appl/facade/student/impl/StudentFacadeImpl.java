package com.system.demo.appl.facade.student.impl;


import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.model.student.Student;
import com.system.demo.data.student.dao.StudentDao;

import java.util.List;

/**
 * An implementation class of the StudentFacade.
 */
public class StudentFacadeImpl implements StudentFacade {
    private StudentDao studentDao;

    /**
     * This creates a new StudentFacadeImpl
     * @param studentDao this helps for managing student data.
     */
    public StudentFacadeImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> getAllStudents() { return studentDao.getAllStudents(); }

    @Override
    public Student getStudentByNumber(String student_id) {
        return studentDao.getStudentByNumber(student_id);
    }

    @Override
    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    @Override
    public boolean addStudent(Student student) {
        boolean result = false;
        try {
            Student targetStudent = getStudentByNumber(student.getStudentId());
            if(targetStudent != null) {
                throw new Exception("Student to add already exists. ");
            }
            result = studentDao.addStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateStudent(Student student) {
        boolean result = false;
        try {
            Student targetStudent = getStudentByNumber(student.getStudentId());
            if (targetStudent == null) {
                throw new Exception("Student to update not found. ");
            }
            result = studentDao.updateStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public Student findStudentByEmail(String email) {
        return  studentDao.findStudentByEmail(email);
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        return studentDao.getStudentByStudentNumber(studentNumber);
    }



}
