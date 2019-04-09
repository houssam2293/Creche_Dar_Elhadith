package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import home.dbDir.CalendarDB;
import home.java.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarController implements Initializable {

    CalendarDB calendarDB;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox holderPane, selectedCalendarInfo, calendarSelection;

    @FXML
    private HBox weekdayHeader;

    @FXML
    private Label errorLabel;

    @FXML
    private Label calendarNameLbl;

    @FXML
    private Label monthLabel;

    @FXML
    private CheckBox excurtionCheckBox, spectacleCheckBox, atelierCheckbox, siesteCheckBox, jeuxCheckBox, selectAllCheckBox;

    @FXML
    private JFXButton loadCalendar;


    @FXML
    private JFXComboBox<String> selectedYear;

    @FXML
    private JFXListView<String> monthSelect;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private ScrollPane scrollPane;

    public static boolean workingOnCalendar = false;
    private static boolean checkBoxesHaveBeenClicked = false;
    private static boolean calendarLoaded = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        initializeMonthSelector();


    }

    @FXML
    void handleCalendarLoaded() {
        calendarSelection.setVisible(false);
        selectedCalendarInfo.setVisible(true);

    }

    private void addEvent(VBox day) {

        // Purpose - Add event to a day

        // Do not add events to days without labels
        if (!day.getChildren().isEmpty()) {

            // Get the day number
            Label lbl = (Label) day.getChildren().get(0);
            System.out.println(lbl.getText());

            // Store event day and month in data singleton
            Model.getInstance().event_day = Integer.parseInt(lbl.getText());
            Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
            Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());

            // Open add event view
            try {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/home/fxml/addEvent.fxml"));
                AnchorPane rootLayout = loader.load();
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);

                // Pass main controller reference to view
                AddEventController eventController = loader.getController();
                eventController.setMainController(this);

                // Show the scene containing the root layout.
                Scene scene = new Scene(rootLayout);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void editEvent(VBox day, String descript, String typeevent) {

        // Store event fields in data singleton
        Label dayLbl = (Label) day.getChildren().get(0);
        Model.getInstance().event_day = Integer.parseInt(dayLbl.getText());
        Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
        Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
        Model.getInstance().eventDescreption = descript;
        Model.getInstance().eventType = typeevent;

        // When user clicks on any date in the calendar, event editor window opens
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/fxml/editEvent.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass main controller reference to view
            EditEventController eventController = loader.getController();
            eventController.setMainController(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadCalendarLabels() {

        // Get the current VIEW
        int year = Model.getInstance().viewing_year;
        int month = Model.getInstance().viewing_month;

        // Note: Java's Gregorian Calendar class gives us the right
        // "first day of the month" for a given calendar & month
        // This accounts for Leap Year
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        System.out.println(firstDay);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        // We are "offsetting" our start depending on what the
        // first day of the month is.
        // For example: Sunday start, Monday start, Wednesday start.. etc
        int offset = firstDay;
        int gridCount = 1;
        int lblCount = 1;

        // Go through calendar grid
        for (Node node : calendarGrid.getChildren()) {

            VBox day = (VBox) node;

            day.getChildren().clear();
            day.setStyle("-fx-backgroud-color: white");
            day.setStyle("-fx-font: 14px \"System\" ");

            // Start placing labels on the first day for the month
            if (gridCount < offset) {
                gridCount++;
                // Darken color of the offset days
                day.setStyle("-fx-background-color: #EEEEEE");
            } else {

                // Don't place a label if we've reached maximum label for the month
                if (lblCount > daysInMonth) {
                    // Instead, darken day color
                    day.setStyle("-fx-background-color: #EEEEEE");
                } else {

                    // Make a new day label
                    Label lbl = new Label(Integer.toString(lblCount));
                    lbl.setPadding(new Insets(5));
                    lbl.setStyle("-fx-text-fill:white");

                    day.getChildren().add(lbl);
                }

                lblCount++;
            }
        }
    }


    private void initializeCalendarGrid() {


        // Go through each calendar grid location, or each "day" (7x6)
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(calendarGrid.getPrefWidth() / 7);

                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    addEvent(vPane);
                });

                GridPane.setVgrow(vPane, Priority.ALWAYS);

                // Add it to the grid
                calendarGrid.add(vPane, j, i);
            }
        }

        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(scrollPane.getHeight() / 7);
            calendarGrid.getRowConstraints().add(row);
        }
    }

    public void calendarGenerate() {

        // Load year selection
        selectedYear.getItems().clear(); // Note: Invokes its change listener
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_start));
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_end));

        // Select the first YEAR as default
        selectedYear.getSelectionModel().selectFirst();

        // Update the VIEWING YEAR
        Model.getInstance().viewing_year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());

        System.out.println("---------------------------------------------------------");
        System.out.println("calendarGenerate from CaldendarController");
        System.out.println("Initialized Model for calendar instance :");
        System.out.println("Model.calendarName : " + Model.getInstance().calendar_name);
        System.out.println("Model.calendarStartDay : " + Model.getInstance().calendar_start_date);
        System.out.println("Model.calendarViewingMonth : " + Model.getInstance().viewing_month);
        System.out.println("Model.calendarViewingYear : " + Model.getInstance().viewing_year);
        System.out.println("---------------------------------------------------------");


        // Enable year selection box
        selectedYear.setVisible(true);

        // Set calendar name label
        calendarNameLbl.setText(Model.getInstance().calendar_name);

        // Get a list of all the months (1-12) in a year
        DateFormatSymbols dateFormat = new DateFormatSymbols();
        String[] months = dateFormat.getMonths();
        String[] spliceMonths = Arrays.copyOfRange(months, 0, 12);

        // Load month selection
        monthSelect.getItems().clear();
        monthSelect.getItems().addAll(spliceMonths);

        // Select the first MONTH as default
        monthSelect.getSelectionModel().selectFirst();
        monthLabel.setText(monthSelect.getSelectionModel().getSelectedItem());

        // Update the VIEWING MONTH
        Model.getInstance().viewing_month =
                Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());

        calendarLoaded = true;
        loadCalendar.setDisable(false);
        handleCalendarLoaded();

        // Update view
        repaintView();
    }

    public void repaintView() {
        // Purpose - To be usable anywhere to update view
        // 1. Correct calendar labels based on Gregorian Calendar
        // 2. Display events known to database


        loadCalendarLabels();
        if (!checkBoxesHaveBeenClicked) {
            //populateMonthWithEvents();
        } else {
            //handleCheckBoxAction();
        }
        //populateMonthWithEvents();
    }

    private void populateMonthWithEvents() {

        // Get viewing calendar
        String calendarName = Model.getInstance().calendar_name;
        String currentMonth = monthLabel.getText();

        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        int currentYear = Integer.parseInt(selectedYear.getValue());

        // Query to get ALL Events from the selected calendar!!
        String getMonthEventsQuery = "SELECT * From events WHERE CalendarName='" + calendarName + "';";
        System.out.println("---------------------------------------------------------");
        System.out.println("populateMonthWithEvents CalendarName-----> " + calendarName);
        System.out.println("populateMonthWithEvents getMonthEventsQuery---------> " + getMonthEventsQuery);
        System.out.println("---------------------------------------------------------");
        // Store the results here
        ResultSet result = calendarDB.executeQuery(getMonthEventsQuery);

        try {

            while (result.next()) {

                // Get date for the event
                Date eventDate = result.getDate("EventDate");
                String eventDescript = result.getString("EventDescription");
                String typeEvent = result.getString("TypeEvent");
                Time eventTime = result.getTime("EventTime");


                // Check for year we have selected
                if (currentYear == eventDate.toLocalDate().getYear()) {
                    // Check for the month we already have selected (we are viewing)
                    if (currentMonthIndex == eventDate.toLocalDate().getMonthValue()) {

                        // Get day for the month
                        int day = eventDate.toLocalDate().getDayOfMonth();

                        // Display decription of the event given it's day
                        showDate(day, eventDescript, typeEvent, eventTime);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR MESSAGE : " + ex.getMessage());
        }
    }

    public void showDate(int dayNumber, String descript, String typeEvent, Time eventTime) {

        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("/home/icons/icon2.png")));
        ImageView imgView = new ImageView();
        imgView.setImage(img);

        for (Node node : calendarGrid.getChildren()) {

            // Get the current day
            VBox day = (VBox) node;

            // Don't look at any empty days (they at least must have a day label!)
            if (!day.getChildren().isEmpty()) {

                // Get the day label for that day
                Label lbl = (Label) day.getChildren().get(0);

                // Get the number
                int currentNumber = Integer.parseInt(lbl.getText());

                // Did we find a match?
                if (currentNumber == dayNumber) {

                    // Add an event label with the given description
                    Label eventLbl = new Label(descript);
                    eventLbl.setGraphic(imgView);
                    eventLbl.getStyleClass().add("event-label");

                    // Save the term ID in accessible text
                    eventLbl.setAccessibleText(typeEvent + "--" + eventTime.toString());
                    System.out.println(eventLbl.getAccessibleText());

                    eventLbl.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> editEvent((VBox) eventLbl.getParent(), eventLbl.getText(), eventLbl.getAccessibleText()));

                    // Get term color from term's table
                    String eventRGB = calendarDB.getEventColor(typeEvent);

                    // Parse for rgb values
                    String[] colors = eventRGB.split("-");
                    String red = colors[0];
                    String green = colors[1];
                    String blue = colors[2];

                    System.out.println("Color; " + red + green + blue);

                    eventLbl.setStyle("-fx-background-color: rgb(" + red +
                            ", " + green + ", " + blue + ", " + 1 + ");");

                    // Stretch to fill box
                    eventLbl.setMaxWidth(Double.MAX_VALUE);

                    // Mouse effects
                    eventLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> eventLbl.getScene().setCursor(Cursor.HAND));

                    eventLbl.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> eventLbl.getScene().setCursor(Cursor.DEFAULT));

                    // Add label to calendar
                    day.getChildren().add(eventLbl);
                }
            }
        }
    }

    //TODO:disable check boxes brfore adding the calendar
    private void initializeCalendarWeekdayHeader() {

        // 7 days in a week
        int weekdays = 7;

        // Weekday names
        String[] weekAbbr = {"Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};

        for (int i = 0; i < weekdays; i++) {

            // Make new pane and label of weekday
            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");

            // Make panes take up equal space
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);

            // Note: After adding a label to this, it tries to resize itself..
            // So I'm setting a minimum width.
            pane.setMinWidth(weekdayHeader.getPrefWidth() / 7);

            // Add it to the header
            weekdayHeader.getChildren().add(pane);

            // Add weekday name
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }

    private void initializeMonthSelector() {

        // Add event listener to each month list item, allowing user to change months
        monthSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            // Necessary to check for null because change listener will
            // also detect clear() calls
            if (newValue != null) {

                // Show selected/current month above calendar
                monthLabel.setText(newValue);

                // Update the VIEWING MONTH
                Model.getInstance().viewing_month = Model.getInstance().getMonthIndex(newValue);

                // Update view
                repaintView();
            }

        });

        // Add event listener to each year item, allowing user to change years
        selectedYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                // Update the VIEWING YEAR
                Model.getInstance().viewing_year = Integer.parseInt(newValue);

                // Update view
                repaintView();
            }
        });
    }

    @FXML
    public void btnBackward() {
        calendarSelection.setVisible(true);
        selectedCalendarInfo.setVisible(false);
    }

    @FXML
    public void newCalendarEvent() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/fxml/addCalendar.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass main controller reference to view
            AddCalendarController calendarController = loader.getController();
            calendarController.setMainController(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (calendarLoaded) {
            calendarSelection.setVisible(false);
            selectedCalendarInfo.setVisible(true);
        }

    }

    @FXML
    private void listCalendarsEvent() {
        // List Calendar view
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/fxml/listeCalendars.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass main controller reference to view
            ListeCalendarsController listController = loader.getController();
            listController.setMainController(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (calendarLoaded) {
            calendarSelection.setVisible(false);
            selectedCalendarInfo.setVisible(true);
        }

    }

    @FXML
    void handleCheckBoxAction() {
        if (!checkBoxesHaveBeenClicked) {
            checkBoxesHaveBeenClicked = true;

        }

        ArrayList<String> eventToFilter = new ArrayList<>();

        if (excurtionCheckBox.isSelected()) {
            eventToFilter.add("excurtion");
        }
        if (!excurtionCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("excurtion");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (spectacleCheckBox.isSelected()) {
            eventToFilter.add("spectacle");
        }
        if (!spectacleCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("spectacle");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (atelierCheckbox.isSelected()) {
            eventToFilter.add("atelier");
        }
        if (!atelierCheckbox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("atelier");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (siesteCheckBox.isSelected()) {
            eventToFilter.add("sieste");
        }
        if (!siesteCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("sieste");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (jeuxCheckBox.isSelected()) {
            eventToFilter.add("jeux");
        }
        if (!jeuxCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("jeux");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        String calName = Model.getInstance().calendar_name;

        System.out.println("and calendarName is: " + calName);

        if (eventToFilter.isEmpty()) {
            System.out.println("terms are not selected. No events have to appear on calendar. Just call loadCalendarLabels method in the RepaintView method");
            selectAllCheckBox.setSelected(false);
            loadCalendarLabels();
        } else {
            System.out.println("Call the appropiate function to populate the month with the filtered events");
            //Get List of Filtered Events and store all events in an ArrayList variable
            ArrayList<String> filteredEventsList = calendarDB.getFilteredEvents(eventToFilter, calName);

            System.out.println("List of Filtered events is: " + filteredEventsList);

            //Repaint or reload the events based on the selected terms
            showFilteredEventsInMonth(filteredEventsList);
        }
    }

    public void showFilteredEventsInMonth(ArrayList<String> filteredEventsList) {

        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("I am in the show filtered events in month function");
        System.out.println("The list of filted events is: " + filteredEventsList);
        System.out.println("****------******-------******--------");


        String calendarName = Model.getInstance().calendar_name;

        String currentMonth = monthLabel.getText();
        System.out.println("currentMonth is: " + currentMonth);
        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        System.out.println("currentMonthIndex is: " + currentMonthIndex);

        int currentYear = Integer.parseInt(selectedYear.getValue());
        System.out.println("CurrentYear is: " + currentYear);
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");


        System.out.println("Now the labels on the calendar are cleared");
        loadCalendarLabels();
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");
        System.out.println("Now, the filtered events/labels will be shown/put on the calendar");
        System.out.println("****------******-------******--------");

        for (int i = 0; i < filteredEventsList.size(); i++) {
            String[] eventInfo = filteredEventsList.get(i).split("~");
            String eventDescript = eventInfo[0];
            String eventDate = eventInfo[1];
            String eventCalName = eventInfo[2];
            String eventTime = eventInfo[3];
            String eventType = eventInfo[4];
            System.out.println(eventDescript);
            System.out.println(eventDate);
            System.out.println(eventTime);
            System.out.println(eventCalName);
            System.out.println(eventType);

            String[] dateParts = eventDate.split("-");
            int eventYear = Integer.parseInt(dateParts[0]);
            int eventMonth = Integer.parseInt(dateParts[1]);
            int eventDay = Integer.parseInt(dateParts[2]);


            System.out.println("****------******-------******--------");
            System.out.println("Now I will check if currentYear equals eventYear. Result is:");
            if (currentYear == eventYear) {
                System.out.println("Yes, both year match.");
                System.out.println("Now I will check if the month index equals the event's month. REsult is");
                if (currentMonthIndex == eventMonth) {
                    System.out.println("Yes they are the same. Now I will call showDate function");
                    showDate(eventDay, eventDescript, eventType, Time.valueOf(eventTime));
                }
            }
        }
    }

    @FXML
    void selectAllCheckBoxes(ActionEvent event) {
        if (selectAllCheckBox.isSelected())
        {
            selectCheckBoxes();
        }
        else
        {
            unSelectCheckBoxes();
        }
        //handleCheckBoxAction();

    }

    private void unSelectCheckBoxes() {
        excurtionCheckBox.setSelected(false);
        spectacleCheckBox.setSelected(false);
        atelierCheckbox.setSelected(false);
        siesteCheckBox.setSelected(false);
        jeuxCheckBox.setSelected(false);
    }

    private void selectCheckBoxes() {
        excurtionCheckBox.setSelected(true);
        spectacleCheckBox.setSelected(true);
        atelierCheckbox.setSelected(true);
        siesteCheckBox.setSelected(true);
        jeuxCheckBox.setSelected(true);

    }

    @FXML
    void updateColors(MouseEvent event) {

    }
}
