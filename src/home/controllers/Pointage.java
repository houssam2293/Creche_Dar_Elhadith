package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.EmployeDB;
import home.dbDir.PointageDB;
import home.java.Employe;
import home.java.PointageModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Pointage implements Initializable {


    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label titleLabel1;

    @FXML
    private Label time;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXComboBox<String> combo;

    @FXML
    private TableView<PointageModel> tableview;
    static JFXDialog listeAbsences;
    private ObservableList<PointageModel> data;
    private TableColumn idCol, fullNameCol, jobCol, timeEntCol, remarqCol, actionCol;

    private boolean donneSaved = false;

    @FXML
    void confirm(ActionEvent event) {

        ObservableList<PointageModel> person = tableview.getSelectionModel().getSelectedItems();
        if (person.isEmpty()) {
            System.out.println("Liste is null !");
            Notifications.create()
                    .title("يرجى تحديد العامل                   ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }
        int i = 0;
        for (PointageModel poi : person) //insertion des données a BDD
        {
            i = new PointageDB().addPointage(poi);

        }
        donneSaved = true; // si les donnée sont stocké dans BDD
        switch (i) {
            case -1:
                System.out.println("Error connecting to DB!");
                errorLabel.setText("No connection to database!!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add PointageModel");
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();
        }

    }

    @FXML
    void listeview(ActionEvent event) {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/fxml/listeAbsence.fxml"));
        } catch (IOException ignored) {
        }
        listeAbsences = getSpecialDialog(addUserPane);
        listeAbsences.show();

    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        //  dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }


    @FXML
    void updateTable() {
        combo.getSelectionModel().select(null);
        searchField.setText(null);
        List<Employe> employes = FXCollections.observableArrayList();
        data = null;
        data = FXCollections.observableArrayList();

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(employe);
            }
            for (Employe ep : employes) {
                data.add(new PointageModel(ep.getId(), ep.getNom() + " " + ep.getPrenom(), ep.getFonction(), "remark", ""));
            }
        }
        tableview.setItems(data);
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donneSaved = false;
        //combo.setItems(options);
       /* searchField.setOnKeyReleased(t -> {
            if(new Validation().alphanumValid(searchField))
                searchField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            else
                searchField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");


        });*/

        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "الوظيفة");
        initializeTable();


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            time.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    private void invalidated() {
        if (searchField.textProperty().get().isEmpty()) {
            tableview.setItems(data);
            return;
        }
        ObservableList<PointageModel> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<PointageModel, ?>> cols = tableview.getColumns();
        for (PointageModel datum : data) {

            for (TableColumn col : cols) {
                String cellValue = col.getCellData(datum).toString();
                cellValue = cellValue.toLowerCase();
                if (cellValue.contains(searchField.textProperty().get().toLowerCase())) {
                    tableItems.add(datum);
                    break;
                }
            }
        }
        tableview.setItems(tableItems);
    }


    private void initializeTable() {

        idCol = new TableColumn("رقم التسجيل");
        idCol.setPrefWidth(180);
        fullNameCol = new TableColumn("اسم و لقب العامل");
        fullNameCol.setPrefWidth(250);
        jobCol = new TableColumn("الوظيفة");
        jobCol.setPrefWidth(150);
        timeEntCol = new TableColumn("توقيت الدخول");
        timeEntCol.setPrefWidth(120);
        remarqCol = new TableColumn("ملاحظات");
        remarqCol.setPrefWidth(300);
        actionCol = new TableColumn("تسجيل الدخول");
        actionCol.setPrefWidth(120);

        tableview.getColumns().addAll(idCol, fullNameCol, jobCol, timeEntCol, actionCol, remarqCol);
        updateTable();
      /* data = FXCollections.observableArrayList(
                new PointageModel(1,"محمد رياض محمد رياض", "موظف","لا توجد ملاحظات",""),
                new PointageModel(2,"جمال الدين", "معلم","لا توجد ملاحظات", ""),
                new PointageModel(3,"ريتاج امال", "معلم", "لا توجد ملاحظات",""),
                new PointageModel(4,"أمين انور", "موظف", "لا توجد ملاحظات",""),
                new PointageModel(5,"شيماء نور", "عامل نظافة", "لا توجد ملاحظات","")
        );*/

        idCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("id")
        );
        fullNameCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("firstName")
        );
        jobCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("lastName")
        );
        timeEntCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("tempEnt")
        );
        remarqCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("remarkk")
        );

        actionCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("remark")
        );

        // tableview.setItems(data);
        searchField.textProperty().addListener(e -> invalidated());
        combo.setOnAction(event -> invalidated());

    }
}
