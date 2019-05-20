package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.CalendarDB;
import home.java.ModelCalendar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddCalendarController implements Initializable {

    private CalendarController mainController;

    void setMainController(CalendarController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private JFXTextField calendarName;

    @FXML
    private JFXDatePicker date;

    @FXML
    private Label topLabel;


    @FXML
    private AnchorPane rootPane;

    private double xOffset;
    private double yOffset;

    @FXML
    void generateNewCalendar() {

        String calName = calendarName.getText();


        if ((date.getValue() != null) && (!calendarName.getText().isEmpty())) {

            if (calName.contains("~")) {
                Alert alertMessage = new Alert(Alert.AlertType.WARNING);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("اسم التقويم لا يمكن أن يحتوي على الحرف ~");
                alertMessage.showAndWait();
            } else {
                ModelCalendar.getInstance().calendar_start_date = "" + date.getValue();
                ModelCalendar.getInstance().calendar_start = date.getValue().getYear();
                ModelCalendar.getInstance().calendar_end = date.getValue().getYear() + 1;
                ModelCalendar.getInstance().calendar_name = calendarName.getText();

                DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String startingDate = date.getValue().format(myFormat);

                String startingYear = Integer.toString(ModelCalendar.getInstance().calendar_start);
                String endingYear = Integer.toString(ModelCalendar.getInstance().calendar_end);
                String calName2 = calendarName.getText();

                CalendarDB calendarDB = new CalendarDB();

                String calendarQuery = "INSERT INTO calendar VALUES ("
                        + "'" + calName2 + "', " + startingYear + ", " + endingYear + ", " + "'" + startingDate + "')";

                if (calendarDB.executeAction(calendarQuery)) {
                    Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("تم إنشاء التقويم بنجاح!");
                    alertMessage.showAndWait();
                    if (alertMessage.getResult() == ButtonType.OK)
                        ((Stage) rootPane.getScene().getWindow()).close();

                    mainController.calendarGenerate();

                    //TODO:check this later
                    //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
                    //mainController.enableCheckBoxes();
                } else {
                    Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("الرجاء استخدام اسم مختلف للتقويم\n فشل إنشاءالتقويم!");
                    alertMessage.showAndWait();
                }

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "يرجى ملء جميع الحقول.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    @FXML
    void btnCancel() {
        ((Stage) calendarName.getScene().getWindow()).close();
    }

    @FXML
    void btnClose() {
        ((Stage) calendarName.getScene().getWindow()).close();
    }
}