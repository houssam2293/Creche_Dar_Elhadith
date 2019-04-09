package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import home.dbDir.CalendarDB;
import home.java.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {

    // Controllers
    private CalendarController mainController;


    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    CalendarDB calendarDB;
    //--------------------------------------------------------------------


    //Set main controller
    public void setMainController(CalendarController mainController) {
        this.mainController = mainController;
    }


    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label topLabel;

    @FXML
    private JFXTextField eventDescription;

    @FXML
    private JFXDatePicker eventDate;

    @FXML
    private JFXTimePicker eventTime;

    @FXML
    private JFXComboBox<String> eventType;

    private double xOffset;
    private double yOffset;

    @FXML
    void btnCancel() {
        ((Stage) topLabel.getScene().getWindow()).close();
    }

    @FXML
    void btnClose() {
        ((Stage) topLabel.getScene().getWindow()).close();

    }

    @FXML
    void generateNewEvent() {

        String calendarName = Model.getInstance().calendar_name;


        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        if (eventDescription.getText().isEmpty() || eventType.getSelectionModel().isEmpty()
                || eventDate.getValue() == null || eventTime.getValue() == null) {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("يرجى ملء جميع الحقول");
            alertMessage.showAndWait();
            return;
        }

        if (eventDescription.getText().contains("~")) {

            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("لا يمكن أن يحتوي إسم الحدث على الحرف ~");
            alertMessage.showAndWait();
            return;
        }

        String calendarDate = eventDate.getValue().format(myFormat);
        String eventSubject = eventDescription.getText();
        String eventTypeValue = eventType.getValue();
        LocalTime eventTimeValue = eventTime.getValue();


        String insertQuery = "INSERT INTO events (`EventDescription`, `EventDate`, `CalendarName`, `EventTime`, `TypeEvent`) VALUES ("
                + "'" + eventSubject + "', "
                + "'" + calendarDate + "', "
                + "'" + calendarName + "', "
                + "'" + eventTimeValue.toString() + "',"
                + "'" + eventTypeValue + "'"
                + ")";

        //Check if insertion into database was successful, and show message either if it was or not
        if (calendarDB.executeAction(insertQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("تمت إضافة الحدث بنجاح");
            alertMessage.showAndWait();
        } else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("إضافة حدث فشل! \n يوجد بالفعل حدث بنفس المعلومات");
            alertMessage.showAndWait();
        }

        //Show the new event on the calendar according to the selected filters
        mainController.repaintView();

        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    private void autofillDatePicker() {

        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;

        eventDate.setValue(LocalDate.of(year, month, day));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autofillDatePicker();
        calendarDB = new CalendarDB();

        eventType.getItems().addAll("رحلة", "عرض", "ورشة", "قيلولة", "ألعاب");

        //**********************************************************************
        // ************* Everything below is for Draggable Window ********

        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.HAND); //Change cursor to hand
        });

        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
        });
    }
}
