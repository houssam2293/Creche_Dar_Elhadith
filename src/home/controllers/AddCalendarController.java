package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.CalendarDB;
import home.java.ModelCalendar;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddCalendarController implements Initializable {

    private CalendarController mainController ;

    public void setMainController(CalendarController mainController) {
        this.mainController = mainController ;
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

        //Variable that holds the calendar name entered by the user
        String calName = calendarName.getText();

        //Check if the user actually gave input for the calendar name and the start date of the calendar
        if ( (date.getValue() != null) && (!calendarName.getText().isEmpty())) {

            //Check if the calendar name contains the character ~ because it cannot contain it due to database and filtering issues
            if (calName.contains("~"))
            {
                //Show message indicating that the calendar cannot contain the character ~
                Alert alertMessage = new Alert(Alert.AlertType.WARNING);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("اسم التقويم لا يمكن أن يحتوي على الحرف ~");
                alertMessage.showAndWait();
            }
            else
            {
                // Set the starting and ending years, and the starting date, and store them in a ModelCalendar object
                ModelCalendar.getInstance().calendar_start_date = "" + date.getValue();
                ModelCalendar.getInstance().calendar_start = date.getValue().getYear();
                ModelCalendar.getInstance().calendar_end = date.getValue().getYear() + 1;
                ModelCalendar.getInstance().calendar_name = calendarName.getText();

                // Define date format
                DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // Get the date value from the date picker
                String startingDate = date.getValue().format(myFormat);

                //Store calendar's information in String variables that will be used to build the query to insert it into the database
                String startingYear = Integer.toString(ModelCalendar.getInstance().calendar_start);
                String endingYear = Integer.toString(ModelCalendar.getInstance().calendar_end);
                String calName2 = calendarName.getText();

                //************************************************************************
                //************************************************************************
                //
                //********  Inserting the new calendar data into the database  ***********

                //*** Instantiate DBHandler object *******************
                CalendarDB calendarDB = new CalendarDB();
                //****************************************************

                // Query that inserts the new calendar into the database
                String calendarQuery = "INSERT INTO calendar VALUES ("
                        + "'" + calName2 + "', " + startingYear + ", " + endingYear + ", " + "'" + startingDate + "')";

                //Insert the new calendar into the database and show a message wheher the insertion was successful or not
                if(calendarDB.executeAction(calendarQuery))
                {
                    Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("تم إنشاء التقويم بنجاح!");
                    alertMessage.showAndWait();
                    if (alertMessage.getResult()== ButtonType.OK)
                        ((Stage) rootPane.getScene().getWindow()).close();
                    // Load the calendar in the main window
                    mainController.calendarGenerate();

                    //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
                    //mainController.enableCheckBoxes();

                    //Enable the buttons that work with rules
                    //mainController.enableButtons();
                }
                else //if there is an error
                {
                    Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("الرجاء استخدام اسم مختلف للتقويم\n فشل إنشاءالتقويم!");
                    alertMessage.showAndWait();
                }

            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "يرجى ملء جميع الحقول.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ******** Code below is for Draggable windows **********

        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });

        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
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