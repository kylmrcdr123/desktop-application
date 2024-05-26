package com.system.demo.controllers.dashboard;

import com.system.demo.PrefectInfoMgtApplication;
import com.system.demo.StudentInfoMgtApplication;
import com.system.demo.appl.facade.prefect.communityservice.CommunityServiceFacade;
import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.model.communityservice.CommunityService;
import com.system.demo.appl.model.student.Student;
import com.system.demo.controllers.search.SearchHistoryController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CsHistoryController implements Initializable{
    //for sidebar uses
    @FXML
    private Button burgerButton;

    @FXML
    private ImageView burgerIcon;

    @FXML
    private AnchorPane sidebarPane;

    private boolean sidebarVisible = false;

    //for search
    @FXML
    private TextField searchField;

    //for table id
    @FXML
    TableView table;

    private CommunityServiceFacade communityServiceFacade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PrefectInfoMgtApplication app = new PrefectInfoMgtApplication();

        communityServiceFacade = app.getCommunityserviceFacade();

        table.getItems().clear();
        List<CommunityService> communityServices = communityServiceFacade.getAllCs();
        ObservableList<CommunityService> data = FXCollections.observableArrayList(communityServices);
        table.setItems(data);

        TableColumn<CommunityService, String> studIdColumn = new TableColumn<>("STUDENT ID");
        studIdColumn.setCellValueFactory(cellData -> {
            String studentId = cellData.getValue().getStudent().getStudentId();
            return new SimpleStringProperty(studentId);
        });
        studIdColumn.getStyleClass().addAll("student-column");

        TableColumn<CommunityService, String> studColumn = new TableColumn<>("NAME");
        studColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getStudent().getFirstName();
            String lastName = cellData.getValue().getStudent().getLastName();
            return new SimpleStringProperty(firstName + " " + lastName);
        });
        studColumn.getStyleClass().addAll("student-column");

        TableColumn<CommunityService, Timestamp> dateRenderedColumn = new TableColumn<>("DATE RENDERED");
        dateRenderedColumn.setCellValueFactory(new PropertyValueFactory<>("date_rendered"));
        dateRenderedColumn.getStyleClass().addAll("date-column");
        dateRenderedColumn.setCellFactory(getDateCellFactory());

        TableColumn<CommunityService, Integer> hoursCompleted = new TableColumn<>("HOURS COMPLETED");
        hoursCompleted.setCellValueFactory(new PropertyValueFactory<>("hours_completed"));
        hoursCompleted.getStyleClass().addAll("hours-column");

        table.getColumns().addAll(studIdColumn, studColumn, dateRenderedColumn, hoursCompleted);
    }

    private Callback<TableColumn<CommunityService, Timestamp>, TableCell<CommunityService, Timestamp>> getDateCellFactory() {
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
    protected void handleIconOffense(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OffenseList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconViolationList(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViolationList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconCommunityService(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CommunityService.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconLogout (MouseEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();

            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //for sidebar actions
    @FXML
    private void toggleSidebarVisibility(ActionEvent event) {
        sidebarVisible = !sidebarVisible;
        sidebarPane.setVisible(sidebarVisible);

        if (sidebarVisible) {
            BorderPane.setMargin(sidebarPane, new Insets(0));
        } else {
            BorderPane.setMargin(sidebarPane, new Insets(0, -125.0, 0, 0));
        }
    }

    //for search
    @FXML
    private void handleSearchButton(ActionEvent event) {
        String searchTerm = searchField.getText().trim(); // Remove leading and trailing spaces

        StudentInfoMgtApplication app = new StudentInfoMgtApplication();
        StudentFacade studentFacade = app.getStudentFacade();

        // Retrieve all students
        List<Student> allStudents = studentFacade.getAllStudents();

        // Filter students by name
        List<Student> matchingStudents = new ArrayList<>();
        for (Student student : allStudents) {
            String fullName = student.getFirstName().toLowerCase() + " " + student.getLastName().toLowerCase();

            // Check if the full name contains the search term, ignoring case
            if (fullName.contains(searchTerm.toLowerCase())) {
                matchingStudents.add(student);
            }
        }

        if (!matchingStudents.isEmpty()) {
            // Assuming you want to handle the case where there are multiple students with the same name,
            // you might display a list of matching students or navigate to a different view.
            // For simplicity, I'm assuming you're selecting the first matching student.
            Student selectedStudent = matchingStudents.get(0);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchHistory.fxml"));

                SearchHistoryController searchHistoryController = new SearchHistoryController();
                searchHistoryController.initData(selectedStudent);
                loader.setController(searchHistoryController);
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Student Not Found", "No student found with the name: " + searchTerm);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
