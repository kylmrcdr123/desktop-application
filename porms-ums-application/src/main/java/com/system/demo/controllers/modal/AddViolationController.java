package com.system.demo.controllers.modal;

import com.system.demo.EmployeeInfoMgtApplication;
import com.system.demo.PrefectInfoMgtApplication;
import com.system.demo.StudentInfoMgtApplication;
import com.system.demo.appl.facade.employee.EmployeeFacade;
import com.system.demo.appl.facade.prefect.offense.OffenseFacade;
import com.system.demo.appl.facade.prefect.violation.ViolationFacade;
import com.system.demo.appl.facade.student.StudentFacade;
import com.system.demo.appl.model.employee.Employee;
import com.system.demo.appl.model.offense.Offense;
import com.system.demo.appl.model.violation.Violation;
import com.system.demo.appl.model.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.ComboBox;
import javafx.stage.StageStyle;

public class AddViolationController{
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentNameField;
    @FXML
    private ComboBox offenseComboBox;
    @FXML
    private TextField warningNumField;
    @FXML
    private TextField csHoursField;
    @FXML
    private TextField disciplinaryField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private TextField employeeNameField;

    private OffenseFacade offenseFacade;
    private ViolationFacade violationFacade;
    private StudentFacade studentFacade;
    private EmployeeFacade employeeFacade;

    @FXML
    public void initialize() {
        PrefectInfoMgtApplication appl = new PrefectInfoMgtApplication();
        offenseFacade = appl.getOffenseFacade();

        List<Offense> offenses = offenseFacade.getAllOffense();

        List<String> offenseName = offenses.stream()
                .map(Offense::getDescription)
                .collect(Collectors.toList());

        offenseComboBox.getItems().addAll(offenseName);
    }

    @FXML
    protected void saveAddClicked(ActionEvent event) {
        if (studentIdField.getText().isEmpty() || employeeIdField.getText().isEmpty() || dateField.getValue() == null) {
            showAlert("Please fill in all required fields.");
            return;
        }

        if (!isNumeric(warningNumField.getText()) || !isNumeric(csHoursField.getText())) {
            showAlert("Warning Number and Community Service Hours must be numeric.");
            return;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dateField.getValue();
        if (selectedDate != null && selectedDate.isAfter(currentDate)) {
            showAlert("Invalid input: Violation date cannot be in the future.");
            return;
        }
        PrefectInfoMgtApplication app = new PrefectInfoMgtApplication();
        violationFacade = app.getViolationFacade();

        offenseFacade = app.getOffenseFacade();
        Offense offense = offenseFacade.getOffenseByName((String) offenseComboBox.getValue());

        StudentInfoMgtApplication appl = new StudentInfoMgtApplication();
        studentFacade = appl.getStudentFacade();
        Student student = studentFacade.getStudentByNumber(studentIdField.getText());

        EmployeeInfoMgtApplication ap = new EmployeeInfoMgtApplication();
        employeeFacade = ap.getEmployeeFacade();
        Employee employee = employeeFacade.getEmployeeById(employeeIdField.getText());

        Violation addViolation = new Violation();
        addViolation.setStudent(student);
        addViolation.setOffense(offense);
        addViolation.setApprovedBy(employee);

        LocalDateTime localDateTime = LocalDateTime.now();
        addViolation.setDateOfNotice(Timestamp.valueOf(localDateTime));

        addViolation.setWarningNum(Integer.parseInt(warningNumField.getText()));
        addViolation.setCommServHours(Integer.parseInt(csHoursField.getText()));
        addViolation.setDisciplinaryAction(disciplinaryField.getText());
        try {
            violationFacade.addViolation(addViolation);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                previousStage.close();

                Stage dashboardStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/ViolationList.fxml"));
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handleCancelAddViolationClicked(MouseEvent event) {
        try {
            Stage previousStage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage2.close();

            Stage dashboardStage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("/views/ViolationList.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            dashboardStage2.setScene(scene2);
            dashboardStage2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void handleStudentIdChanged(KeyEvent event) {

        if(!studentIdField.getText().isEmpty()){
            StudentInfoMgtApplication appl = new StudentInfoMgtApplication();
            studentFacade = appl.getStudentFacade();
            Student student = studentFacade.getStudentByNumber(studentIdField.getText());
            if (student != null) {
                String fullName = student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName();
                studentNameField.setText(fullName);
            } else {
                studentNameField.clear();
            }
        }
    }

    @FXML
    protected void handleEmployeeIdChanged(KeyEvent event) {

        if(!employeeIdField.getText().isEmpty()){
            EmployeeInfoMgtApplication appl = new EmployeeInfoMgtApplication();
            employeeFacade = appl.getEmployeeFacade();
            Employee employee = employeeFacade.getEmployeeById(employeeIdField.getText());
            if (employee != null) {
                String fullName = employee.getLastName() + ", " + employee.getFirstName() + " " + employee.getMiddleName();
                employeeNameField.setText(fullName);
            } else {
                employeeNameField.clear();
            }
        }
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}