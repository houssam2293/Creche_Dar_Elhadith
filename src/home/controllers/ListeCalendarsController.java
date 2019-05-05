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

    void setMainController(CalendarController mainController) {
        this.mainController = mainController;
    }

    private void loadData() {

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
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("تأكيد");
            alert.setHeaderText("حذف التقويم");
            alert.setContentText("هل تريد بالتأكيد حذف هذا التقويم؟");

            ButtonType buttonTypeYes = new ButtonType("نعم");
            ButtonType buttonTypeNo = new ButtonType("لا");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                deleteSelectedCalendar();
            }
        } else {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("يرجى اختيار التقويم!");
            alertMessage.showAndWait();
        }

    }

    private void deleteSelectedCalendar() {

        Calendar cal = tableView.getSelectionModel().getSelectedItem();
        String calendarName = cal.getName();
        System.out.println(calendarName);

        String deleteEventsQuery = "DELETE FROM events "
                + "WHERE events.CalendarName='" + calendarName + "'";

        System.out.println(deleteEventsQuery);

        String deleteCalendarQuery = "DELETE FROM calendar "
                + "WHERE calendar.CalendarName='" + calendarName + "'";

        System.out.println(deleteCalendarQuery);

        boolean eventsWereDeleted = calendarDB.executeAction(deleteEventsQuery);

        if (eventsWereDeleted) {
            System.out.println("All events associated to the selected calendar were successfully deleted. Deleting Calendar is next");

            boolean calendarWasDeleted = calendarDB.executeAction(deleteCalendarQuery);

            if (calendarWasDeleted) {
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("تم حذف التقويم بنجاح");
                alertMessage.showAndWait();

                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
                try {
                    mainController.reloadStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("حذف التقويم فشل!");
                alertMessage.showAndWait();

            }
        } else {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("حذف التقويم فشل!");
            alertMessage.showAndWait();
            System.out.println("Deleting Events of Selected Calendar Failed!!!");
        }
    }

    @FXML
    void openCalendar() {
        if (!tableView.getSelectionModel().isEmpty()) {
            Calendar cal = tableView.getSelectionModel().getSelectedItem();
            ModelCalendar.getInstance().calendar_name = cal.getName();
            ModelCalendar.getInstance().calendar_start = Integer.parseInt(cal.getStartYear());
            ModelCalendar.getInstance().calendar_end = Integer.parseInt(cal.getEndYear());
            ModelCalendar.getInstance().calendar_start_date = cal.getStartDate();
            //TODO:check this part later
            mainController.calendarGenerate();

            //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
            //mainController.enableCheckBoxes();

            //Enable the buttons that work with rules
            //mainController.enableButtons();

            ((Stage) rootPane.getScene().getWindow()).close();
        } else {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("يرجى اختيار التقويم!");
            alertMessage.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarDB = new CalendarDB();
        initCol();
        loadData();

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
