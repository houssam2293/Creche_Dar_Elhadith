package home.controllers;

import com.jfoenix.controls.*;
import home.dbDir.CalendarDB;
import home.dbDir.ClasseDB;
import home.java.ClasseCellFactory;
import home.java.ClasseModel;
import home.java.ModelCalendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalendarController implements Initializable {

    private CalendarDB calendarDB;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox weekdayTimeTable, holderPane, selectedCalendarInfo, calendarSelection;

    @FXML
    private HBox hourDayTimeTable, weekdayHeader;

    @FXML
    private Label errorLabel;

    @FXML
    private Label calendarNameLbl, dateLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private CheckBox excurtionCheckBox, spectacleCheckBox, atelierCheckbox, siesteCheckBox, jeuxCheckBox, selectAllCheckBox;

    @FXML
    private JFXColorPicker excursionCP, spectacleCP, atelierCP, siesteCP, jeuxCP;

    @FXML
    private JFXButton loadCalendar;

    @FXML
    private JFXTabPane utilityTab,contentTab;

    @FXML
    private JFXButton addTimebtn, editTimebtn, printTimebtn;

    @FXML
    private ListView<ClasseModel> classeListView;

    @FXML
    private JFXComboBox<String> selectedYear;

    @FXML
    private JFXListView<String> monthSelect;

    @FXML
    private GridPane calendarGrid, timeTable;

    @FXML
    private ScrollPane scrollPane1, scrollPane;

    private static boolean checkBoxesHaveBeenClicked = false;
    private static boolean calendarLoaded = false;
    private String currentYearTimeLable = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (Calendar.getInstance().get(Calendar.MONTH) > 8)
            currentYearTimeLable = Calendar.getInstance().get(Calendar.YEAR) + "-" + Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
        else
            currentYearTimeLable = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1) + "-" + Calendar.getInstance().get(Calendar.YEAR);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss      yyyy/MM/dd");
            java.util.Date date = new java.util.Date();
            dateLabel.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        calendarDB = new CalendarDB();

        utilityTab.setOnMouseClicked(e->{
            if (utilityTab.getSelectionModel().isSelected(2)) {
                contentTab.getSelectionModel().select(1);
            } else {
                contentTab.getSelectionModel().select(0);
            }
        });
        contentTab.setOnMouseClicked(event ->{
            if (contentTab.getSelectionModel().isSelected(1)) {
                utilityTab.getSelectionModel().select(2);
            } else {
                utilityTab.getSelectionModel().select(0);
            }
        });

        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        ArrayList<ClasseModel> classeModels = new ArrayList<>(clsDB);
        classeListView.getItems().addAll(classeModels);
        classeListView.setCellFactory(new ClasseCellFactory());
        classeListView.setOnMouseClicked(event -> {
            ClasseModel classe = classeListView.getSelectionModel().getSelectedItem();
            enableButtons(classe);
        });

        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        initializeMonthSelector();
        initializeTimetableWeekdayHeader();
        initializeTimetableTimesHeader();
        initializeTimeTableGrid();
        initalizeColorPicker();

    }

    @FXML
    void handleCalendarLoaded() {
        calendarSelection.setVisible(false);
        selectedCalendarInfo.setVisible(true);

    }

    private void addEvent(VBox day) {

        if (!day.getChildren().isEmpty()) {

            Label lbl = (Label) day.getChildren().get(0);

            ModelCalendar.getInstance().event_day = Integer.parseInt(lbl.getText());
            ModelCalendar.getInstance().event_month = ModelCalendar.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
            ModelCalendar.getInstance().event_year = Integer.parseInt(selectedYear.getValue());

            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/home/resources/fxml/addEvent.fxml"));
                AnchorPane rootLayout = loader.load();
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);

                AddEventController eventController = loader.getController();
                eventController.setMainController(this);

                Scene scene = new Scene(rootLayout);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void editEvent(VBox day, String descript, String typeevent, Time eventTime) {

        Label dayLbl = (Label) day.getChildren().get(0);
        ModelCalendar.getInstance().event_day = Integer.parseInt(dayLbl.getText());
        ModelCalendar.getInstance().event_month = ModelCalendar.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
        ModelCalendar.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
        ModelCalendar.getInstance().eventDescreption = descript;
        ModelCalendar.getInstance().eventType = typeevent;
        ModelCalendar.getInstance().eventTime = LocalTime.of(eventTime.getHours() - 1, eventTime.getMinutes());

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/resources/fxml/editEvent.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            EditEventController eventController = loader.getController();
            eventController.setMainController(this);

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadCalendarLabels() {

        int year = ModelCalendar.getInstance().viewing_year;
        int month = ModelCalendar.getInstance().viewing_month;

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        int gridCount = 1;
        int lblCount = 1;

        for (Node node : calendarGrid.getChildren()) {
            VBox day = (VBox) node;
            day.getChildren().clear();
            day.setStyle("-fx-backgroud-color: white");
            day.setStyle("-fx-font: 14px \"System\" ");

            if (gridCount < firstDay) {
                gridCount++;
                day.setStyle("-fx-background-color: #EEEEEE");
            } else {
                if (lblCount > daysInMonth) {
                    day.setStyle("-fx-background-color: #EEEEEE");
                } else {
                    Label lbl = new Label(Integer.toString(lblCount));
                    lbl.setPadding(new Insets(5));
                    lbl.setStyle("-fx-text-fill:white");
                    day.getChildren().add(lbl);
                }

                lblCount++;
            }
        }
    }


    public void exportCalendarExcel() {

        if (calendarLoaded) {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                createExcelSheet(file);
                System.out.println("hi");
            }
        } else {
            Notifications.create()
                    .title("يرجى تحديد التقويم أولا!                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
        }
    }

    private void createExcelSheet(File file) {
        String calendarName = ModelCalendar.getInstance().calendar_name;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(calendarName);

        //styles
        HSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight((short) (16 * 20));
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeight((short) (16 * 20));


        HSSFCellStyle cellStyleBorderTopRight = wb.createCellStyle();
        cellStyleBorderTopRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderTopRight.setLocked(true);
        cellStyleBorderTopRight.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTopRight.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTopRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleBorderTopRight.setFont(headerFont);
        cellStyleBorderTopRight.setBorderTop(BorderStyle.THICK);
        cellStyleBorderTopRight.setBorderRight(BorderStyle.THICK);
        cellStyleBorderTopRight.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorderTopRight.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFCellStyle cellStyleBorderTopLeft = wb.createCellStyle();
        cellStyleBorderTopLeft.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderTopLeft.setLocked(true);
        cellStyleBorderTopLeft.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTopLeft.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTopLeft.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleBorderTopLeft.setFont(headerFont);
        cellStyleBorderTopLeft.setBorderTop(BorderStyle.THICK);
        cellStyleBorderTopLeft.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorderTopLeft.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorderTopLeft.setBorderLeft(BorderStyle.THICK);

        HSSFCellStyle cellStyleBorderBottomRight = wb.createCellStyle();
        cellStyleBorderBottomRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderBottomRight.setFont(font);
        cellStyleBorderBottomRight.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorderBottomRight.setBorderRight(BorderStyle.THICK);
        cellStyleBorderBottomRight.setBorderBottom(BorderStyle.THICK);
        cellStyleBorderBottomRight.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFCellStyle cellStyleBorderBottomLeft = wb.createCellStyle();
        cellStyleBorderBottomLeft.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderBottomLeft.setFont(font);
        cellStyleBorderBottomLeft.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorderBottomLeft.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorderBottomLeft.setBorderBottom(BorderStyle.THICK);
        cellStyleBorderBottomLeft.setBorderLeft(BorderStyle.THICK);

        HSSFCellStyle cellStyleBorderBottom = wb.createCellStyle();
        cellStyleBorderBottom.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderBottom.setFont(font);
        cellStyleBorderBottom.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorderBottom.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorderBottom.setBorderBottom(BorderStyle.THICK);
        cellStyleBorderBottom.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFCellStyle cellStyleBorderTop = wb.createCellStyle();
        cellStyleBorderTop.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderTop.setLocked(true);
        cellStyleBorderTop.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTop.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        cellStyleBorderTop.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleBorderTop.setFont(headerFont);
        cellStyleBorderTop.setBorderTop(BorderStyle.THICK);
        cellStyleBorderTop.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorderTop.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorderTop.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFCellStyle cellStyleBorderRight = wb.createCellStyle();
        cellStyleBorderRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderRight.setFont(font);
        cellStyleBorderRight.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorderRight.setBorderRight(BorderStyle.THICK);
        cellStyleBorderRight.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorderRight.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFCellStyle cellStyleBorderLeft = wb.createCellStyle();
        cellStyleBorderLeft.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorderLeft.setFont(font);
        cellStyleBorderLeft.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorderLeft.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorderLeft.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorderLeft.setBorderLeft(BorderStyle.THICK);

        HSSFCellStyle cellStyleBorder = wb.createCellStyle();
        cellStyleBorder.setAlignment(HorizontalAlignment.CENTER);
        cellStyleBorder.setFont(font);
        cellStyleBorder.setBorderTop(BorderStyle.DASH_DOT);
        cellStyleBorder.setBorderRight(BorderStyle.DASH_DOT);
        cellStyleBorder.setBorderBottom(BorderStyle.DASH_DOT);
        cellStyleBorder.setBorderLeft(BorderStyle.DASH_DOT);

        HSSFRow row = sheet.createRow(1);
        HSSFCell cell;

        cell = row.createCell(1);
        cell.setCellValue("إسم الحدث");
        cell.setCellStyle(cellStyleBorderTopLeft);
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("يوم");
        cell.setCellStyle(cellStyleBorderTop);
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("على الساعة");
        cell.setCellStyle(cellStyleBorderTop);
        sheet.autoSizeColumn(3);

        cell = row.createCell(4);
        cell.setCellValue("نوع الحدث");
        cell.setCellStyle(cellStyleBorderTopRight);
        sheet.autoSizeColumn(4);

        // Query to get ALL Events from the selected calendar!!
        String getMonthEventsQuery = "SELECT * From events WHERE CalendarName='" + calendarName + "' ORDER BY EventDate,EventTime ";

        // Store the results here
        ResultSet result = calendarDB.executeQuery(getMonthEventsQuery);
        int last = 0;
        try {
            if (result.last()) {
                last = result.getRow();
                result.beforeFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int counter = 2;
            while (result.next()) {


                String eventDescript = result.getString("EventDescription");

                Date dDate = result.getDate("EventDate");
                String eventDate = dDate.toString();
                String eventTime = result.getTime("EventTime").toString();

                String eventType = result.getString("TypeEvent");
                if (result.getRow() == last) {
                    row = sheet.createRow(counter);
                    cell = row.createCell(1);
                    cell.setCellValue(eventDescript);
                    cell.setCellStyle(cellStyleBorderBottomLeft);

                    cell = row.createCell(2);
                    cell.setCellValue(eventDate);
                    cell.setCellStyle(cellStyleBorderBottom);

                    cell = row.createCell(3);
                    cell.setCellValue(eventTime);
                    cell.setCellStyle(cellStyleBorderBottom);

                    cell = row.createCell(4);
                    cell.setCellValue(eventType);
                    cell.setCellStyle(cellStyleBorderBottomRight);

                } else {
                    row = sheet.createRow(counter);
                    cell = row.createCell(1);
                    cell.setCellValue(eventDescript);
                    cell.setCellStyle(cellStyleBorderLeft);

                    cell = row.createCell(2);
                    cell.setCellValue(eventDate);
                    cell.setCellStyle(cellStyleBorder);

                    cell = row.createCell(3);
                    cell.setCellValue(eventTime);
                    cell.setCellStyle(cellStyleBorder);

                    cell = row.createCell(4);
                    cell.setCellValue(eventType);
                    cell.setCellStyle(cellStyleBorderRight);

                }
                for (int i = 0; i < 4; i++) {
                    sheet.autoSizeColumn(i);
                }

                counter++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeCalendarGrid() {
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(calendarGrid.getPrefWidth() / 7);
                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> addEvent(vPane));

                GridPane.setVgrow(vPane, Priority.ALWAYS);

                calendarGrid.add(vPane, j, i);
            }
        }

        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(scrollPane.getHeight() / 7);
            calendarGrid.getRowConstraints().add(row);
        }
    }

    private void initializeTimeTableGrid() {
        int rows = 5;
        int cols = 5;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(timeTable.getPrefWidth() / 5);

                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> addEvent(vPane));

                GridPane.setVgrow(vPane, Priority.ALWAYS);

                timeTable.add(vPane, j, i);
            }
        }

        for (int i = 0; i < 5; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(scrollPane1.getHeight() / 5);
            timeTable.getColumnConstraints().add(col);
        }
    }

    void calendarGenerate() {
        selectedYear.getItems().clear();
        selectedYear.getItems().add(Integer.toString(ModelCalendar.getInstance().calendar_start));
        selectedYear.getItems().add(Integer.toString(ModelCalendar.getInstance().calendar_end));
        selectedYear.getSelectionModel().selectFirst();

        ModelCalendar.getInstance().viewing_year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());

        System.out.println("---------------------------------------------------------");
        System.out.println("calendarGenerate from CaldendarController");
        System.out.println("Initialized ModelCalendar for calendar instance :");
        System.out.println("ModelCalendar.calendarName : " + ModelCalendar.getInstance().calendar_name);
        System.out.println("ModelCalendar.calendarStartDay : " + ModelCalendar.getInstance().calendar_start_date);
        System.out.println("ModelCalendar.calendarViewingMonth : " + ModelCalendar.getInstance().viewing_month);
        System.out.println("ModelCalendar.calendarViewingYear : " + ModelCalendar.getInstance().viewing_year);
        System.out.println("---------------------------------------------------------");

        selectedYear.setVisible(true);
        calendarNameLbl.setText(ModelCalendar.getInstance().calendar_name);

        String[] months = DateFormatSymbols.getInstance(new Locale("ar")).getMonths();
        String[] spliceMonths = Arrays.copyOfRange(months, 0, 12);
        monthSelect.getItems().clear();
        monthSelect.getItems().addAll(spliceMonths);


        monthSelect.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
        monthLabel.setText(monthSelect.getSelectionModel().getSelectedItem());

        ModelCalendar.getInstance().viewing_month =
                ModelCalendar.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());

        calendarLoaded = true;
        loadCalendar.setDisable(false);
        handleCalendarLoaded();

        repaintView();
    }

    void repaintView() {
        loadCalendarLabels();
        if (!checkBoxesHaveBeenClicked) {
            populateMonthWithEvents();
        } else {
            handleCheckBoxAction();
        }
    }

    private void populateMonthWithEvents() {

        String calendarName = ModelCalendar.getInstance().calendar_name;
        String currentMonth = monthLabel.getText();

        int currentMonthIndex = ModelCalendar.getInstance().getMonthIndex(currentMonth) + 1;
        int currentYear = Integer.parseInt(selectedYear.getValue());

        String getMonthEventsQuery = "SELECT * From events WHERE CalendarName='" + calendarName + "'";
        ResultSet result = calendarDB.executeQuery(getMonthEventsQuery);
        try {
            while (result.next()) {
                Date eventDate = result.getDate("EventDate");
                String eventDescript = result.getString("EventDescription");
                String typeEvent = result.getString("TypeEvent");
                Time eventTime = result.getTime("EventTime");

                if (currentYear == eventDate.toLocalDate().getYear()) {
                    if (currentMonthIndex == eventDate.toLocalDate().getMonthValue()) {
                        int day = eventDate.toLocalDate().getDayOfMonth();
                        showDate(day, eventDescript, getEventType(typeEvent), eventTime);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("SQL ERROR MESSAGE : " + ex.getMessage());
        }
    }

    private String getEventType(String event) {
        String eveTyp = null;
        switch (event) {
            case "رحلة":
                eveTyp = "excursion";
                break;
            case "عرض":
                eveTyp = "spectacle";
                break;
            case "ورشة":
                eveTyp = "atelier";
                break;
            case "قيلولة":
                eveTyp = "sieste";
                break;
            case "ألعاب":
                eveTyp = "jeux";
        }
        return eveTyp;
    }

    private void showDate(int dayNumber, String descript, String typeEvent, Time eventTime) {

        Image img = new Image("/home/resources/icons/icon2.png");
        ImageView imgView = new ImageView();
        imgView.setImage(img);

        for (Node node : calendarGrid.getChildren()) {

            VBox day = (VBox) node;

            if (!day.getChildren().isEmpty()) {

                Label lbl = (Label) day.getChildren().get(0);

                int currentNumber = Integer.parseInt(lbl.getText());

                if (currentNumber == dayNumber) {

                    Label eventLbl = new Label(descript);
                    eventLbl.setGraphic(imgView);
                    eventLbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

                    eventLbl.setAccessibleText(typeEvent);

                    eventLbl.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> editEvent((VBox) eventLbl.getParent(), eventLbl.getText(), eventLbl.getAccessibleText(), eventTime));

                    String eventRGB = calendarDB.getEventColor(typeEvent);

                    String[] colors = eventRGB.split("-");
                    String red = colors[0];
                    String green = colors[1];
                    String blue = colors[2];

                    eventLbl.setStyle("-fx-background-color: rgb(" + red +
                            ", " + green + ", " + blue + ", " + 1 + ");");

                    eventLbl.setMaxWidth(Double.MAX_VALUE);

                    eventLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> eventLbl.getScene().setCursor(Cursor.HAND));

                    eventLbl.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> eventLbl.getScene().setCursor(Cursor.DEFAULT));

                    day.getChildren().add(eventLbl);
                }
            }
        }
    }

    private void initializeCalendarWeekdayHeader() {

        int weekdays = 7;

        String[] weekAbbr = {"الأحد", "الإثنين", "الثلثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"};

        for (int i = 0; i < weekdays; i++) {

            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");

            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);

            pane.setMinWidth(weekdayHeader.getPrefWidth() / 7);

            weekdayHeader.getChildren().add(pane);

            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }

    private void initializeTimetableWeekdayHeader() {

        int weekdays = 5;

        String[] weekAbbr = {"الأحد", "الإثنين", "الثلثاء", "الأربعاء", "الخميس"};

        for (int i = 0; i < weekdays; i++) {

            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");

            VBox.setVgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);

            pane.setMinWidth(weekdayTimeTable.getPrefWidth() / 5);

            weekdayTimeTable.getChildren().add(pane);

            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }

    private void initializeTimetableTimesHeader() {

        int dayHours = 6;

        String[] weekAbbr = {"", "10h-09h", "11h-10h", "12h-11h", "14h-13h", "15h-14h", "16h-15h"};

        for (int i = 0; i < dayHours; i++) {

            if (i == 0) {
                VBox pane = new VBox();
                pane.getStyleClass().add("weekday-header");

                HBox.setHgrow(pane, Priority.ALWAYS);
                pane.setMaxWidth(Double.MAX_VALUE);
                pane.setAlignment(Pos.CENTER);

                pane.setMinWidth(115);

                hourDayTimeTable.getChildren().add(pane);
                pane.getChildren().add(new Label("السنة الدراسية"));
                pane.getChildren().add(new Label(currentYearTimeLable));
            } else {

                StackPane pane = new StackPane();
                pane.getStyleClass().add("weekday-header");

                HBox.setHgrow(pane, Priority.ALWAYS);
                pane.setMaxWidth(Double.MAX_VALUE);

                pane.setMinWidth(hourDayTimeTable.getPrefWidth() / 6);

                hourDayTimeTable.getChildren().add(pane);

                pane.getChildren().add(new Label(weekAbbr[i]));
            }
        }
    }


    private void initializeMonthSelector() {

        monthSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                monthLabel.setText(newValue);

                ModelCalendar.getInstance().viewing_month = ModelCalendar.getInstance().getMonthIndex(newValue);

                repaintView();
            }

        });

        selectedYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                ModelCalendar.getInstance().viewing_year = Integer.parseInt(newValue);
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/resources/fxml/addCalendar.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            AddCalendarController calendarController = loader.getController();
            calendarController.setMainController(this);

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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/home/resources/fxml/listeCalendars.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            ListeCalendarsController listController = loader.getController();
            listController.setMainController(this);

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
            eventToFilter.add("رحلة");
        }
        if (!excurtionCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("رحلة");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (spectacleCheckBox.isSelected()) {
            eventToFilter.add("عرض");
        }
        if (!spectacleCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("عرض");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (atelierCheckbox.isSelected()) {
            eventToFilter.add("ورشة");
        }
        if (!atelierCheckbox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("ورشة");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (siesteCheckBox.isSelected()) {
            eventToFilter.add("قيلولة");
        }
        if (!siesteCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("قيلولة");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        if (jeuxCheckBox.isSelected()) {
            eventToFilter.add("ألعاب");
        }
        if (!jeuxCheckBox.isSelected()) {
            int auxIndex = eventToFilter.indexOf("ألعاب");
            if (auxIndex != -1) {
                eventToFilter.remove(auxIndex);
            }
        }

        String calName = ModelCalendar.getInstance().calendar_name;

        if (eventToFilter.isEmpty()) {
            selectAllCheckBox.setSelected(false);
            loadCalendarLabels();
        } else {

            ArrayList<String> filteredEventsList = calendarDB.getFilteredEvents(eventToFilter, calName);

            showFilteredEventsInMonth(filteredEventsList);
        }
    }

    private void showFilteredEventsInMonth(ArrayList<String> filteredEventsList) {

        String currentMonth = monthLabel.getText();
        int currentMonthIndex = ModelCalendar.getInstance().getMonthIndex(currentMonth) + 1;
        int currentYear = Integer.parseInt(selectedYear.getValue());
        loadCalendarLabels();

        for (String s : filteredEventsList) {
            String[] eventInfo = s.split("~");
            String eventDescript = eventInfo[0];
            String eventDate = eventInfo[1];
            String eventCalName = eventInfo[2];
            String eventTime = eventInfo[3];
            String eventType = eventInfo[4];

            String[] dateParts = eventDate.split("-");
            int eventYear = Integer.parseInt(dateParts[0]);
            int eventMonth = Integer.parseInt(dateParts[1]);
            int eventDay = Integer.parseInt(dateParts[2]);

            if (currentYear == eventYear) {
                if (currentMonthIndex == eventMonth) {
                    showDate(eventDay, eventDescript, getEventType(eventType), Time.valueOf(eventTime));
                }
            }
        }
    }

    @FXML
    void selectAllCheckBoxes(ActionEvent event) {
        if (selectAllCheckBox.isSelected()) {
            selectCheckBoxes();
        } else {
            unSelectCheckBoxes();
        }
        handleCheckBoxAction();

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
        changeColors();
        if (ModelCalendar.getInstance().calendar_name != null)
            repaintView();

    }

    private String getRGB(Color c) {

        return (int) (c.getRed() * 255) + "-"
                + (int) (c.getGreen() * 255) + "-"
                + (int) (c.getBlue() * 255);
    }

    private void changeColors() {

        Color excursionColor = excursionCP.getValue();
        String excursionRGB = getRGB(excursionColor);
        calendarDB.setEventColor("excursion", excursionRGB);

        Color spectacleColor = spectacleCP.getValue();
        String spectacleRGB = getRGB(spectacleColor);
        calendarDB.setEventColor("spectacle", spectacleRGB);

        Color atelierColor = atelierCP.getValue();
        String atelierRGB = getRGB(atelierColor);
        calendarDB.setEventColor("atelier", atelierRGB);

        Color siesteColor = siesteCP.getValue();
        String siesteRGB = getRGB(siesteColor);
        calendarDB.setEventColor("sieste", siesteRGB);

        Color jeuxColor = jeuxCP.getValue();
        String jeuxRGB = getRGB(jeuxColor);
        calendarDB.setEventColor("jeux", jeuxRGB);

    }

    private void initalizeColorPicker() {

        String excursionRGB = calendarDB.getEventColor("excursion");
        String spectacleRGB = calendarDB.getEventColor("spectacle");
        String atelierRGB = calendarDB.getEventColor("atelier");
        String siesteRGB = calendarDB.getEventColor("sieste");
        String jeuxRGB = calendarDB.getEventColor("jeux");

        String[] colors = excursionRGB.split("-");
        String red = colors[0];
        String green = colors[1];
        String blue = colors[2];
        Color c = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        excursionCP.setValue(c);

        colors = spectacleRGB.split("-");
        red = colors[0];
        green = colors[1];
        blue = colors[2];
        c = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        spectacleCP.setValue(c);

        colors = atelierRGB.split("-");
        red = colors[0];
        green = colors[1];
        blue = colors[2];
        c = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        atelierCP.setValue(c);

        colors = siesteRGB.split("-");
        red = colors[0];
        green = colors[1];
        blue = colors[2];
        c = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        siesteCP.setValue(c);

        colors = jeuxRGB.split("-");
        red = colors[0];
        green = colors[1];
        blue = colors[2];
        c = Color.rgb(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        jeuxCP.setValue(c);
    }

    private void enableButtons(ClasseModel classe) {
        addTimebtn.setDisable(false);
        editTimebtn.setDisable(false);
        printTimebtn.setDisable(false);
    }

    @FXML
    void printTimeTable(ActionEvent event) {

        //todo:this is next

    }

    @FXML
    void editTimeTable(ActionEvent event) {

    }

    @FXML
    void addTimeTable(ActionEvent event) {

    }

    void reloadStage() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/home/resources/fxml/main.fxml")));
        ((Stage) holderPane.getScene().getWindow()).setScene(scene);
    }
}
