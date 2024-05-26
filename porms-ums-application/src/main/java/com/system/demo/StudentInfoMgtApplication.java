package com.system.demo;

import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.facade.student.impl.StudentFacadeImpl;
import com.system.demo.data.student.dao.StudentDao;
import com.system.demo.data.student.dao.impl.StudentDaoImpl;

/**
 * This class is StudentInfoMgtApplication. This class represents the student information for website.
 * */
public class StudentInfoMgtApplication {
    private StudentFacade studentFacade;
    /**
     * This creates a new com.student.information.management.StudentInfoMgtApplication
     * @return the studentFacade this helps for managing student data.
     */
    public StudentInfoMgtApplication() {
        StudentDao studentDaoImpl = new StudentDaoImpl();
        this.studentFacade = new StudentFacadeImpl(studentDaoImpl);
    }
    /**
     * This gets the Student Facade.
     * @return the student facade.
     */
    public StudentFacade getStudentFacade() {
        return studentFacade;
    }
}