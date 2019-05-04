package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import home.dbDir.CalendarDB;
import home.java.ModelCalendar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventController implements Initializable {
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
    void btnClose() {
        ((Stage) topLabel.getScene().getWindow()).close();
    }

    @FXML
    void btnDelete() {

        int day = ModelCalendar.getInstance().event_day;
        int month = ModelCalendar.getInstance().event_month + 1;
        int year = ModelCalendar.getInstance().event_year;
        String eventdate = year + "-" + month + "-" + day;
        String eventtype = ModelCalendar.getInstance().eventType;
        String descript = ModelCalendar.getInstance().eventDescreption;
        LocalTime eventtime = ModelCalendar.getInstance().eventTime;
        String calName = ModelCalendar.getInstance().calendar_name;

        //Get the original date of the event to be updated in the format yyyy-mm-dd
        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";
        Date auxEventDate;
        try {
            auxEventDate = auxDateFormat.parse(eventdate);
            auxDate = auxDateFormat.format(auxEventDate);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Query that will delete the selected event
        String deleteEventQuery = "DELETE FROM creche_dar_elhadith.events "
                + "WHERE "
                + "(`EventDescription`='" + descript + "') AND "
                + "(`EventDate`='" + auxDate + "') AND "
                + "(`EventTime`='" + eventtime + "') AND "
                + "(`TypeEvent`='" + selectionHandler(eventtype)+ "') AND "
                + "(`CalendarName`='" + calName + "') ";

        //Execute query that deletes the selected event
        boolean eventWasDeleted = calendarDB.executeAction(deleteEventQuery);
        if (eventWasDeleted)
        {
            //Show message indicating that the selected rule was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("تم حذف الحدث المحدد بنجاح");
            alertMessage.showAndWait();

            // Update view
            mainController.repaintView();

            // Close the window, so that when user clicks on "Manage Rules" only the remaining existing rules appear
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            //Show message indicating that the rule could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("حذف الحدث فشل!");
            alertMessage.showAndWait();
        }

    }

    @FXML
    void updateEvent() {


        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int day = ModelCalendar.getInstance().event_day;
        int month = ModelCalendar.getInstance().event_month + 1;
        int year = ModelCalendar.getInstance().event_year;
        String eventdate = year + "-" + month + "-" + day;
        String eventtype = ModelCalendar.getInstance().eventType;
        String descript = ModelCalendar.getInstance().eventDescreption;
        LocalTime eventtime = ModelCalendar.getInstance().eventTime;
        String calName = ModelCalendar.getInstance().calendar_name;

        //Get the original date of the event to be updated in the format yyyy-mm-dd
        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";
        Date auxEventDate;
        try {
            auxEventDate = auxDateFormat.parse(eventdate);
            auxDate = auxDateFormat.format(auxEventDate);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }


        String newCalendarDate = eventDate.getValue().format(myFormat);
        String newEventDescription = eventDescription.getText();
        String newEventType = eventType.getValue();
        LocalTime newEventTime = eventTime.getValue();


        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (newEventDescription.contains("~"))
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("لا يمكن أن يحتوي وصف الحدث على الحرف ~");
            alertMessage.showAndWait();
            return;
        }




        //Query to will update the selected event with the new information
        String updateEventQuery = "UPDATE creche_dar_elhadith.events"
                + " SET "
                + "EventDescription='" + newEventDescription + "', "
                + "EventDate='" + newCalendarDate + "', "
                + "EventTime='" + newEventTime + "', "
                + "TypeEvent='" + newEventType
                + "' WHERE "
                + "(events.EventDescription='" + descript + "') AND "
                + "(events.EventDate='" + auxDate + "') AND "
                + "(events.EventTime='" + eventtime + "') AND "
                + "(events.TypeEvent='" + selectionHandler(eventtype) + "') AND "
                + "(events.CalendarName='" + calName + "') ";

        //Execute query in otder to update the info for the selected event
        //and
        //Check if the update of the event in the database was successful, and show message either if it was or not
        if(calendarDB.executeAction(updateEventQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("تم تحديث الحدث بنجاح");
            alertMessage.showAndWait();

            // Update view
            mainController.repaintView();

        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("فشل تحديث الحدث!\n يوجد بالفعل حدث بنفس المعلومات");
            alertMessage.showAndWait();
        }

        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    void autofillDatePicker() {

        // Get selected day, month, and year and autofill date selection
        int day = ModelCalendar.getInstance().event_day;
        int month = ModelCalendar.getInstance().event_month + 1;
        int year = ModelCalendar.getInstance().event_year;
        String type = ModelCalendar.getInstance().eventType;
        LocalTime time = ModelCalendar.getInstance().eventTime;
        String descript = ModelCalendar.getInstance().eventDescreption;

        eventDescription.setText(descript);
        eventDate.setValue(LocalDate.of(year,month,day));
        eventType.getSelectionModel().select(selectionHandler(type));
        eventTime.setValue(time);


    }

    private String selectionHandler(String type) {
        String eventType = null;
        switch (type) {
            case "excursion":
                eventType = "رحلة";
            break;
            case "spectacle":
                eventType = "عرض";
            break;
            case "atelier":
                eventType = "ورشة";
            break;
            case "sieste":
                eventType = "قيلولة";
            break;
            case "jeux":
                eventType = "ألعاب";
            break;
        }
        return eventType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        calendarDB = new CalendarDB();
        autofillDatePicker();
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