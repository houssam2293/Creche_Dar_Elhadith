package home.controllers;

import home.dbDir.CalendarDB;
import home.java.Calendar;
import home.java.ModelCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListeCalendarsController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label topLabel;

    @FXML
    private TableView<Calendar> tableView;

    @FXML
    private TableColumn<Calendar, String> nameCol;

    @FXML
    private TableColumn<Calendar, String> springCol;

    @FXML
    private TableColumn<Calendar, String> fallCol;

    private CalendarDB calendarDB;
    private double xOffset;
    private double yOffset;

    private ObservableList<Calendar> list = FXCollections.observableArrayList();

    private CalendarController mainController;

    public void setMainController(CalendarController mainController) {
        this.mainController = mainController ;
    }

    private void loadData() {

        //Load all calendars into the Calendar View Table
        String qu = "SELECT * FROM calendar";
        ResultSet result = calendarDB.executeQuery(qu);

        try {
            while (result.next()) {
                String calendarName = result.getString("CalendarName");
                String startYear = Integer.toString(result.getInt("StartYear"));
                String endYear = Integer.toString(result.getInt("EndYear"));
                String startingDate = result.getString("StartDate");

                list.add(new Calendar(calendarName, startYear, endYear, startingDate));


            }
        } catch (SQLException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        springCol.setCellValueFactory(new PropertyValueFactory<>("startYear"));
        fallCol.setCellValueFactory(new PropertyValueFactory<>("endYear"));
    }

    @FXML
    void btnClose() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    void deleteCalendar() {
        if (!tableView.getSelectionModel().isEmpty())
        {
            //Show confirmation dialog to make sure the user want to delete the selected rule
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("تأكيد");
            alert.setHeaderText("حذف التقويم");
            alert.setContentText("هل تريد بالتأكيد حذف هذا التقويم؟");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("نعم");
            ButtonType buttonTypeNo = new ButtonType("لا");
            //Set buttons onto the confirmation dialog
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();

            //If the user wants to delete the calendar, call the function that deletes the calendar. Otherwise, close the window
            if (result.get() == buttonTypeYes){
                deleteSelectedCalendar();
            }
        }
        else
        {
            //Show message indicating that the selected calendar was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("يرجى اختيار التقويم!");
            alertMessage.showAndWait();
        }

    }

    public void deleteSelectedCalendar() {

        // Get selected calendar from table
        Calendar cal = tableView.getSelectionModel().getSelectedItem();
        String calendarName = cal.getName();
        System.out.println(calendarName);

        //Query that will delete all events that belong to the selected calendar
        String deleteEventsQuery = "DELETE FROM events "
                + "WHERE events.CalendarName='" + calendarName + "'";

        System.out.println(deleteEventsQuery);

        //Query that will delete the selected calendar, AFTER all its events had been deleted
        String deleteCalendarQuery = "DELETE FROM calendar "
                + "WHERE calendar.CalendarName='" + calendarName + "'";

        System.out.println(deleteCalendarQuery);

        //Execute query that deletes all events associated to the selected calendar
        boolean eventsWereDeleted = calendarDB.executeAction(deleteEventsQuery);

        if (eventsWereDeleted)
        {
            System.out.println("All events associated to the selected calendar were successfully deleted. Deleting Calendar is next");
            //Execute query that deletes the selected calendar
            boolean calendarWasDeleted = calendarDB.executeAction(deleteCalendarQuery);

            //Check if the selected calendar was deleted
            if (calendarWasDeleted)
            {

                //Show message indicating that the selected calendar was deleted
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("تم حذف التقويم بنجاح");
                alertMessage.showAndWait();

                // Close the window, so that when user clicks on "Manage Your Calendars" only the remaining existing calendar appear
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
                try {
                    mainController.reloadStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                //Show message indicating that the calendar could not be deleted
                Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("حذف التقويم فشل!");
                alertMessage.showAndWait();

            }
        }
        else
        {
            //Show message indicating that the calendar could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("حذف التقويم فشل!");
            alertMessage.showAndWait();
            System.out.println("Deleting Events of Selected Calendar Failed!!!");
        }
    }

    @FXML
    void openCalendar() {
        if (!tableView.getSelectionModel().isEmpty())
        {
            // Get selected calendar from table
            Calendar cal = tableView.getSelectionModel().getSelectedItem();
            ModelCalendar.getInstance().calendar_name = cal.getName();
            ModelCalendar.getInstance().calendar_start = Integer.parseInt(cal.getStartYear());
            ModelCalendar.getInstance().calendar_end = Integer.parseInt(cal.getEndYear());
            ModelCalendar.getInstance().calendar_start_date = cal.getStartDate();

            // Load the calendar in the main window
            mainController.calendarGenerate();

            //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
            //mainController.enableCheckBoxes();

            //Enable the buttons that work with rules
            //mainController.enableButtons();

            // Close the window after opening and loading the selected calendar
            ((Stage) rootPane.getScene().getWindow()).close();
        }
        else
        {
            //Show message indicating that the selected calendar was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("يرجى اختيار التقويم!");
            alertMessage.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //*** Instantiate DBHandler object *******************
         calendarDB= new CalendarDB();
        //****************************************************

        initCol();
        loadData();

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
