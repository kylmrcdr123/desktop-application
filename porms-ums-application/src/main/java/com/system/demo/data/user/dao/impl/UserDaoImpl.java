package com.system.demo.data.user.dao.impl;


import com.system.demo.appl.model.user.User;
import com.system.demo.data.connection.ConnectionHelper;
import com.system.demo.data.user.dao.UserDao;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.system.demo.data.utils.user.QueryConstant.*;


public class UserDaoImpl implements UserDao {


    public static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);


    @Override
    public User saveUser(User user) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_STATEMENT);

            // Set the parameters for the prepared statement

            preparedStatement.setInt(1, user.getIsActive());
            preparedStatement.setInt(2, user.getIsLocked());
            preparedStatement.setString(3, user.getUserId());
            preparedStatement.setTimestamp(4, new Timestamp(user.getJoinDate().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(user.getLastLoginDate().getTime()));
            preparedStatement.setString(6, user.getAuthorities());
            preparedStatement.setString(7, user.getOtp());
            preparedStatement.setString(8, user.getPassword());
            preparedStatement.setString(9, user.getRole());
            preparedStatement.setString(10, user.getUsername());


            preparedStatement.executeUpdate();

            LOGGER.debug("User saved successfully.");
        } catch (Exception e) {

            LOGGER.error("An SQL Exception occurred: " + e.getMessage());
        }

        return user;
    }


    @Override
    public long getMaxUserId() {

        try (Connection connection = ConnectionHelper.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_USER_ID_STATEMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOGGER.debug("MaxUser ID get successfully.");
                return resultSet.getLong("MAX_ID");
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Getting MaxUser ID failed.");
        return 0;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> login = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_ALL_LOGIN_STATEMENT);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                login.add(mapResultSetToUser(rs));
            }
            return login;

        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Student database is empty.");
        return login;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setIsActive(rs.getInt("IS_ACTIVE"));
        user.setIsLocked(rs.getInt("IS_LOCKED"));
        user.setId(rs.getLong("ID"));
        user.setJoinDate(rs.getTimestamp("JOIN_DATE"));
        user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
        user.setAuthorities(rs.getString("AUTHORITIES"));
        user.setOtp(rs.getString("OTP"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setRole(rs.getString("ROLE"));
        user.setUserId(rs.getString("USER_ID"));
        user.setUsername(rs.getString("USERNAME"));

        return user;
    }


    @Override
    public boolean addUser(User user) {
        boolean result = false;
        try (Connection connection = ConnectionHelper.getConnection()) {
            User targetUser = getUserByUserId(user.getUserId());
            if (targetUser != null) {
                throw new Exception("User to add already exists. ");
            }
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_STATEMENT);
            preparedStatement.setInt(1, user.getIsActive());
            preparedStatement.setInt(2, user.getIsLocked());
            preparedStatement.setTimestamp(3, user.getJoinDate());
            preparedStatement.setTimestamp(4, user.getLastLoginDate());
            preparedStatement.setString(5, user.getAuthorities());
            preparedStatement.setString(6, user.getOtp());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getRole());
            preparedStatement.setString(9, user.getUserId());
            preparedStatement.setString(10, user.getUsername());
            int rowsAffected = preparedStatement.executeUpdate();
            result = rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = false;
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_STATEMENT);
            preparedStatement.setInt(1, user.getIsActive());
            preparedStatement.setInt(2, user.getIsLocked());
            preparedStatement.setTimestamp(3, user.getJoinDate());
            preparedStatement.setTimestamp(4, user.getLastLoginDate());
            preparedStatement.setString(5, user.getAuthorities());
            preparedStatement.setString(6, user.getOtp());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getRole());
            preparedStatement.setString(9, user.getUserId());
            preparedStatement.setString(10, user.getUsername());
            preparedStatement.setLong(12, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            result = rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }



    @Override
    public User updatePassword(User user) {

        try (Connection connection = ConnectionHelper.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_STATEMENT);

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUsername());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.debug("Password update failed.");
            } else{
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System Alert");
                    alert.setHeaderText(null);  // Header text can be null if not needed
                    alert.setContentText("Password has been changed successfully");
                    alert.showAndWait();
                });
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Updating password failed.");
        return user;
    }

    @Override
    public String getPasswordByUsername(String username) {
        try (Connection connection = ConnectionHelper.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PASSWORD_BY_USERNAME_STATEMENT);

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    LOGGER.debug("Password get successfully.");
                    return resultSet.getString("password");
                }
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Getting username failed.");
        return null;

    }

    @Override
    public String forgotPassword(String username, String newPassword) {

        try (Connection connection = ConnectionHelper.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(FORGOT_PASSWORD_STATEMENT);

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.debug("Password update failed.");
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("System Alert");
                    alert.setHeaderText(null);  // Header text can be null if not needed
                    alert.setContentText("Password update failed");
                    alert.showAndWait();
                });
            }
            else{
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System Alert");
                    alert.setHeaderText(null);  // Header text can be null if not needed
                    alert.setContentText("Password has been changed successfully");
                    alert.showAndWait();
                });
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred." + e.getMessage());
        }
        LOGGER.debug("Updating password failed.");
        return null;
    }

    @Override
    public User getUserByUserId(String userId) {
        User user = null;
        try (Connection connection = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LOGIN_BY_ID_STATEMENT);
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapResultSetToUser(resultSet);
                }
            }
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred while getting user by user ID: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_USERS_BY_USERNAME_STATEMENT);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setIsActive(rs.getInt("IS_ACTIVE"));
                user.setIsLocked(rs.getInt("IS_LOCKED"));
                user.setJoinDate(rs.getTimestamp("JOIN_DATE"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setAuthorities(rs.getString("AUTHORITIES"));
                user.setOtp(rs.getString("OTP"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRole(rs.getString("ROLE"));
                user.setUserId(rs.getString("USER_ID"));

            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving user with User name " + username + ": " + e.getMessage());
            e.printStackTrace();
        }

        LOGGER.debug("Username not found.");
        return user;
    }



}
