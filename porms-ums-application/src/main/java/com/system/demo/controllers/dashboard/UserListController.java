package com.system.demo.controllers.dashboard;


import com.system.demo.appl.facade.user.UserFacade;
import com.system.demo.appl.facade.user.impl.UserFacadeImpl;
import com.system.demo.appl.model.user.User;
import com.system.demo.controllers.modal.ChangePswController;
import com.system.demo.data.user.dao.UserDao;
import com.system.demo.data.user.dao.impl.UserDaoImpl;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

public class UserListController implements Initializable {


    @FXML
    TableView table;

    private UserFacade userFacade;

    private UserDao userDao;

    public UserListController() {
        userDao = new UserDaoImpl();

        userFacade = new UserFacadeImpl(userDao);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.getItems().clear();

        List<User> users = userFacade.getAllUsers();
        table.getItems().addAll(users);

        ObservableList<User> data = FXCollections.observableArrayList(users);
        table.setItems(data);

        TableColumn userNameColumn = new TableColumn("Username");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userNameColumn.getStyleClass().addAll("username-column");
        userNameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w

        TableColumn lastLoginColumn = new TableColumn("Last Login");
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginDate"));
        lastLoginColumn.getStyleClass().addAll("last-login-column");
        lastLoginColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.6)); // 40% w

        TableColumn joinDateColumn = new TableColumn("Join Date");
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        joinDateColumn.getStyleClass().addAll("join-date-column");
        joinDateColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w

        TableColumn roleColumn = new TableColumn("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.getStyleClass().addAll("role-column");
        roleColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w

        TableColumn authoritiesColumn = new TableColumn("Authorities");
        authoritiesColumn.setCellValueFactory(new PropertyValueFactory<>("authorities"));
        authoritiesColumn.getStyleClass().addAll("action-column");
        authoritiesColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.6)); // 40% w

        TableColumn otpColumn = new TableColumn("Otp");
        otpColumn.setCellValueFactory(new PropertyValueFactory<>("otp"));
        otpColumn.getStyleClass().addAll("otp-column");
        otpColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w

        TableColumn isActiveColumn = new TableColumn("Active");
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActiveColumn.getStyleClass().addAll("action-column");
        isActiveColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w

        TableColumn isLockedColumn = new TableColumn("Locked");
        isLockedColumn.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
        isLockedColumn.getStyleClass().addAll("action-column");
        isLockedColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4)); // 40% w


        TableColumn actionColumn = new TableColumn("ACTION");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>(" "));
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
                        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/editbutton.png"))));
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

        table.getColumns().addAll(userNameColumn, lastLoginColumn, joinDateColumn,
                roleColumn, authoritiesColumn,otpColumn, isActiveColumn, isLockedColumn, actionColumn );


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

    @FXML
    protected void handleIconUserList(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconStudList(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EmployeeList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconLogout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

}

