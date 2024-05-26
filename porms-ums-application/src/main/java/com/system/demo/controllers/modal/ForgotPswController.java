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
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ForgotPswController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField newPswField;

    @FXML
    private TextField showNewPsw;

    @FXML
    private Button saveForgotPswButton;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private ToggleButton toggleButton2;

    private User user;

    UserDao userFacade = new UserDaoImpl();
    private String getInvalidInputMessage() {
        String alphanumericRegex = "[a-zA-Z0-9~`!@#$%^&*()_={}|:;\"'<,>.?/]+";

        if ( usernameField.getText().isEmpty() || nicknameField.getText().isEmpty() || newPswField.getText().isEmpty()) {
            return "All fields must be filled.";
        }

        if (!usernameField.getText().matches(alphanumericRegex)) {
            return "Invalid input for username. Please enter alphanumeric characters only.";
        }
        if (!nicknameField.getText().matches(alphanumericRegex)) {
            return "Invalid childhood nickname. Please Enter alphanumeric characters only";
        }
        if (!newPswField.getText().matches(alphanumericRegex)) {
            return "Invalid input for password. Please enter alphanumeric characters only.";
        }
        return null;
    }
    @FXML
    protected void saveForgotPswClicked(ActionEvent event) {
        try {
            String invalidInputMessage = getInvalidInputMessage();
            if (invalidInputMessage != null) {
                showAlert("Invalid Input", invalidInputMessage);
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User forgotPsw = new User();
        forgotPsw.setUsername(usernameField.getText());
        forgotPsw.setPassword(newPswField.getText());

        String username = usernameField.getText();
        String nickname = nicknameField.getText();
        String newPassword  = newPswField.getText();


        try {

            // saan class neto?
            userFacade.forgotPassword(username, newPassword);
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
    void changeVisibility(ActionEvent event){
        if(toggleButton.isSelected()){
            showNewPsw.setText(newPswField.getText());
            showNewPsw.setVisible(true);
            newPswField.setVisible(false);
            toggleButton.setVisible(false);
            return;
        }
        newPswField.setText(showNewPsw.getText());
        newPswField.setVisible(true);
        showNewPsw.setVisible(false);
        toggleButton.setVisible(true);
    }

    @FXML
    void changeVisibility2(ActionEvent event){
        if(toggleButton2.isSelected()){
            newPswField.setText(showNewPsw.getText());
            newPswField.setVisible(true);
            showNewPsw.setVisible(false);
            toggleButton.setVisible(true);
            return;
        }
        showNewPsw.setText(newPswField.getText());
        showNewPsw.setVisible(true);
        newPswField.setVisible(false);
        toggleButton.setVisible(false);
    }

    @FXML
    protected void handleCancelForgotPsw(MouseEvent event) {
        try {
            Stage previousStage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage2.close();

            Stage dashboardStage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("/views/MainView.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            dashboardStage2.setScene(scene2);
            dashboardStage2.initStyle(StageStyle.UNDECORATED);
            dashboardStage2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}