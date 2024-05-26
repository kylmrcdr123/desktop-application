package com.system.demo.controllers.modal;

import com.system.demo.appl.model.user.User;
import com.system.demo.data.user.dao.UserDao;
import com.system.demo.data.user.dao.impl.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Timestamp;

public class CreateAccController {

    @FXML
    private TextField idField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField entityIdField;

    @FXML
    private Button registerButton;

    private User user;

    UserDao userFacade = new UserDaoImpl();

    private String getInvalidInputMessage() {
        String alphanumericRegex = "[a-zA-Z0-9~`!@#$%^&*()_={}|:;\"'<,>.?/-]+";

        if (usernameField.getText().isEmpty() || entityIdField.getText().isEmpty()) {
            return "All fields must be filled.";
        }

        if (!usernameField.getText().matches(alphanumericRegex)) {
            return "Invalid input for Username. Please enter alphanumeric characters only.";
        }

        if (!entityIdField.getText().matches(alphanumericRegex)) {
            return "Invalid input for Entity. Please enter alphanumeric characters and dashes only.";
        }
        return null;
    }
    @FXML
    protected void saveRegisterClicked(ActionEvent event) {
        try {
            String invalidInputMessage = getInvalidInputMessage();
            if (invalidInputMessage != null) {
                showAlert("Invalid Input", invalidInputMessage);
                return;
            }

            User addUser = new User();
            addUser.setUsername(usernameField.getText());
            addUser.setEntityId(entityIdField.getText());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            addUser.setDateCreated(timestamp);
            addUser.setDateModified(timestamp);

            userFacade.saveUser(addUser);
        } catch(Exception ex) {
            ex.printStackTrace();;
        }
        finally {
            try {
                Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                previousStage.close();

                Stage dashboardStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/MainView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);

                dashboardStage.initStyle(StageStyle.UNDECORATED);

                dashboardStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handleHaveAccount(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleRegisterNow(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

