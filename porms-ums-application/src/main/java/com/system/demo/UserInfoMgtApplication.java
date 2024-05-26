package com.system.demo;

import com.system.demo.appl.facade.user.UserFacade;
import com.system.demo.appl.facade.user.impl.UserFacadeImpl;
import com.system.demo.data.user.dao.UserDao;
import com.system.demo.data.user.dao.impl.UserDaoImpl;

public class UserInfoMgtApplication {
    private UserFacade userFacade;
    /**
     * This creates a new com.student.information.management.StudentInfoMgtApplication
     * @return the studentFacade this helps for managing student data.
     */
    public UserInfoMgtApplication() {
        UserDao userDaoImpl = new UserDaoImpl();
        this.userFacade = new UserFacadeImpl(userDaoImpl);
    }
    /**
     * This gets the Student Facade.
     * @return the student facade.
     */
    public UserFacade getUserFacade() {
        return userFacade;
    }
}
