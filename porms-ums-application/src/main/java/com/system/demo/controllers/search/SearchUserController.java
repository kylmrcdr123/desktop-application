package com.system.demo.controllers.search;

import com.system.demo.appl.model.user.User;
import com.system.demo.controllers.modal.ChangePswController;
import com.system.demo.data.user.dao.UserDao;
import com.system.demo.data.user.dao.impl.UserDaoImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static com.system.demo.data.connection.ConnectionHelper.username;

public class SearchUserController implements Initializable {

    //for table id
    @FXML
    TableView table;
    private String username;
    @FXML
    private Button previousButton;
    UserDao userFacade = new UserDaoImpl();
    public void initData(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        previousButton.setOnAction(event -> {handleBack2Previous((ActionEvent) event);});

        if (username == null || username.isEmpty()) {
            showAlert("Username not provided.", Alert.AlertType.ERROR);
            return;
        }

        System.out.println("Username: " + this.username);
        table.getItems().clear();
        User userData = userFacade.getUserByUsername(username);
        System.out.println(username);


        if (userData == null) {
            // User not found, show alert
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Not Found");
                alert.setHeaderText(null);
                alert.setContentText("The user with username '" + username + "' was not found.");
                alert.showAndWait();
            });
        } else{

            LoadTables(userData);
        }
    }
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void LoadTables(User userData){

        ObservableList<User> data = FXCollections.observableArrayList(userData);
        table.setItems(data);

        TableColumn<User, String> usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.getStyleClass().addAll("username-column");

        TableColumn<User, Integer> lastLoginColumn = new TableColumn<>("LAST LOGIN");
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginDate"));
        lastLoginColumn.getStyleClass().addAll("last-login-column");

        TableColumn<User, Timestamp> joinDateColumn = new TableColumn<>("JOIN DATE");
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        joinDateColumn.getStyleClass().addAll("join-date-column");
        joinDateColumn.setCellFactory(getDateCellFactory());

        TableColumn<User, Timestamp> roleColumn = new TableColumn<>("ROLE");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.getStyleClass().addAll("role-column");

        TableColumn<User, String> authoritiesColumn = new TableColumn<>("AUTHORITIES");
        authoritiesColumn.setCellValueFactory(new PropertyValueFactory<>("authorities"));
        authoritiesColumn.getStyleClass().addAll("authorities-column");

        TableColumn<User, String> otpColumn = new TableColumn<>("OTP");
        otpColumn.setCellValueFactory(new PropertyValueFactory<>("otp"));
        otpColumn.getStyleClass().addAll("otp-column");

        TableColumn<User, Integer> isActiveColumn = new TableColumn<>("IS ACTIVE");
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActiveColumn.getStyleClass().addAll("is-active-column");

        TableColumn<User, Integer> isLockedColumn = new TableColumn<>("IS LOCKED");
        isLockedColumn.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
        isLockedColumn.getStyleClass().addAll("is-locked-column");

        TableColumn<User, String> actionColumn = new TableColumn<>("ACTION");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(""));
        actionColumn.getStyleClass().addAll("action-column");
        actionColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w
        actionColumn.setCellFactory(cell -> {
            final Button editButton = new Button();
            TableCell<User, String> cellInstance = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/pencil.png"))));
                        editButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            showEditUser(user, (ActionEvent) event);
                        });
                        HBox hbox = new HBox(editButton);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.BASELINE_CENTER);
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cellInstance;
        });

        table.getColumns().addAll(usernameColumn, lastLoginColumn, joinDateColumn,
                roleColumn, authoritiesColumn, otpColumn, isActiveColumn, isLockedColumn, actionColumn);
    }

    private Callback<TableColumn<User, Timestamp>, TableCell<User, Timestamp>> getDateCellFactory() {
        return column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDate date = item.toLocalDateTime().toLocalDate();
                    setText(formatter.format(date));
                }
            }
        };
    }

    //show details in edit button
    private void showEditUser(User user, ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            Stage changePsw = new Stage();
            changePsw.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ChangePsw.fxml"));
            AnchorPane changePswLayout = new AnchorPane();
            changePswLayout = loader.load();
            ChangePswController changePswController = loader.getController();
            changePswController.setUser(user);
            Scene scene = new Scene(changePswLayout);
            changePsw.setScene(scene);
            changePsw.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleBack2Previous(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}