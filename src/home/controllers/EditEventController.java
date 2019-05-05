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

    private CalendarController mainController;
    CalendarDB calendarDB;

    void setMainController(CalendarController mainController) {
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

        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";
        Date auxEventDate;
        try {
            auxEventDate = auxDateFormat.parse(eventdate);
            auxDate = auxDateFormat.format(auxEventDate);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String deleteEventQuery = "DELETE FROM creche_dar_elhadith.events "
                + "WHERE "
                + "(`EventDescription`='" + descript + "') AND "
                + "(`EventDate`='" + auxDate + "') AND "
                + "(`EventTime`='" + eventtime + "') AND "
                + "(`TypeEvent`='" + selectionHandler(eventtype) + "') AND "
                + "(`CalendarName`='" + calName + "') ";

        boolean eventWasDeleted = calendarDB.executeAction(deleteEventQuery);
        if (eventWasDeleted) {
            //Show message indicating that the selected rule was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("تم حذف الحدث المحدد بنجاح");
            alertMessage.showAndWait();

            mainController.repaintView();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        } else {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("حذف الحدث فشل!");
            alertMessage.showAndWait();
        }

    }

    @FXML
    void updateEvent() {

        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int day = ModelCalendar.getInstance().event_day;
        int month = ModelCalendar.getInstance().event_month + 1;
        int year = ModelCalendar.getInstance().event_year;
        String eventdate = year + "-" + month + "-" + day;
        String eventtype = ModelCalendar.getInstance().eventType;
        String descript = ModelCalendar.getInstance().eventDescreption;
        LocalTime eventtime = ModelCalendar.getInstance().eventTime;
        String calName = ModelCalendar.getInstance().calendar_name;

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

        if (newEventDescription.contains("~")) {
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("لا يمكن أن يحتوي وصف الحدث على الحرف ~");
            alertMessage.showAndWait();
            return;
        }

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

        if (calendarDB.executeAction(updateEventQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("تم تحديث الحدث بنجاح");
            alertMessage.showAndWait();

            mainController.repaintView();
        } else {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("فشل تحديث الحدث!\n يوجد بالفعل حدث بنفس المعلومات");
            alertMessage.showAndWait();
        }

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    private void autofillDatePicker() {

        int day = ModelCalendar.getInstance().event_day;
        int month = ModelCalendar.getInstance().event_month + 1;
        int year = ModelCalendar.getInstance().event_year;
        String type = ModelCalendar.getInstance().eventType;
        LocalTime time = ModelCalendar.getInstance().eventTime;
        String descript = ModelCalendar.getInstance().eventDescreption;

        eventDescription.setText(descript);
        eventDate.setValue(LocalDate.of(year, month, day));
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

        topLabel.setOnMousePressed(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        topLabel.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        topLabel.setOnMouseEntered(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.HAND);
        });

        topLabel.setOnMouseExited(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.DEFAULT);
        });
    }

}