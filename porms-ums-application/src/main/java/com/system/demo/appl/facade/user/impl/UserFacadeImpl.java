package com.system.demo.appl.facade.user.impl;


import com.system.demo.appl.facade.user.UserFacade;
import com.system.demo.appl.model.user.User;
import com.system.demo.data.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * An implementation class of the User Facade.
 */

public class UserFacadeImpl implements UserFacade {

        /**
         * The logger for this class.
         */
        public static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class);

        private UserDao userDao;

        private User[] userList;

        /**
         * Constructs a new UserFacadeImpl with the provided UserDao.
         *
         * @param userDao The data access object for user operations.
         */
        public UserFacadeImpl(UserDao userDao) {
            this.userDao = userDao;
        }


    @Override
    public User saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Override
    public User updatePassword(User user) {
        return userDao.updatePassword(user);
    }

    @Override
    public boolean forgotPassword(String username, String securityQuestionAnswer, String newPassword) {
        String getPassword = userDao.getPasswordByUsername(username);
        if(getPassword != null){
            if(securityQuestionAnswer.equalsIgnoreCase("markian")){
                userDao.forgotPassword(username,newPassword);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByUserId(String userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public boolean addUser(User user) {
        boolean result = false;
        try {
            User targetUser = getUserByUserId(user.getUserId());
            if(targetUser != null) {
                throw new Exception("User to add already exists. ");
            }
            result = userDao.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = false;
        try {
            User targetUser = getUserByUserId(user.getUserId());
            if (targetUser == null) {
                throw new Exception("Student to update not found. ");
            }
            result = userDao.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }


}
