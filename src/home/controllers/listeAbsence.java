package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import home.dbDir.PointageDB;
import home.java.PointageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ESCAPE;

public class listeAbsence implements Initializable {


    // private static JFXDialog addUserDialog;
    @FXML
    private VBox root;
    @FXML
    private TableView tableview;
    @FXML
    private JFXDatePicker dateJ;
    @FXML
    private Label listevide;
    @FXML
    private TableColumn idCol, fullNameCol, jobCol, remarqCol;
    @FXML
    private JFXRadioButton checkAbsent;
    @FXML
    private JFXRadioButton checkPresent;
    @FXML
    private JFXRadioButton checkOut;
    @FXML
    private ToggleGroup lang;

    private int radioSelec = 1;
    @FXML
    void btnClose() {
        Pointage.listeAbsences.close();
    }

    @FXML
    void selectedRadio() {
        checkAbsent.setOnMouseClicked(t -> {

            checkOut.setSelected(false);
            checkPresent.setSelected(false);
            checkAbsent.setSelected(true);
            radioSelec = 1;
            updateTable();

        });
        checkPresent.setOnMouseClicked(t -> {
            checkOut.setSelected(false);
            checkAbsent.setSelected(false);
            checkPresent.setSelected(true);
            radioSelec = 2;
            updateTable();
        });
        checkOut.setOnMouseClicked(t -> {
            checkAbsent.setSelected(false);
            checkPresent.setSelected(false);
            checkOut.setSelected(true);
            radioSelec = 3;

            updateTable();
        });

    }

    @FXML
    void updateTable() {
        List<PointageModel> pointagesses = FXCollections.observableArrayList();
        ObservableList<PointageModel> data = null;
        data = FXCollections.observableArrayList();
        List<PointageModel> pointageModelDB = new PointageDB().getPresences(dateJ.getValue());
        ;
        if (radioSelec == 1) {
            pointageModelDB = new PointageDB().getAbsences(dateJ.getValue());
        } else if (radioSelec == 2) {
            pointageModelDB = new PointageDB().getPresences(dateJ.getValue());
        } else if (radioSelec == 3) {
            pointageModelDB = new PointageDB().getOut(dateJ.getValue());
        }
        if (pointageModelDB == null) {
            if (radioSelec == 1) {
                listevide.setText("لا يوجد غيابات");
            } else if (radioSelec == 2) {
                listevide.setText("لا يوجد عمال حاضرون");
            } else if (radioSelec == 3) {
                listevide.setText("لا يوجد عمال غير معنيون");
            }
            listevide.setTextFill(Color.web("#D50E21"));


        } else {
            for (PointageModel poi : pointageModelDB) {
                pointagesses.add(poi);

            }
            for (int i = 0; i < pointagesses.size(); i++) {
                PointageModel ep = pointagesses.get(i);
                listevide.setText("");
                data.add(ep);


            }
        }


        idCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("id")
        );
        fullNameCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("firstName")
        );
        jobCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("lastName")
        );

        remarqCol.setCellValueFactory(
                new PropertyValueFactory<PointageModel, String>("label")
        );
        tableview.setItems(data);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol = new TableColumn("رقم التسجيل");

        idCol.setPrefWidth(120);
        fullNameCol = new TableColumn("اسم و لقب العامل");
        fullNameCol.setPrefWidth(150);
        jobCol = new TableColumn("الوظيفة");
        jobCol.setPrefWidth(120);
        remarqCol = new TableColumn("ملاحظات");
        remarqCol.setPrefWidth(120);
        tableview.getColumns().addAll(idCol, fullNameCol, jobCol, remarqCol);
        dateJ.setValue(LocalDate.now());

        updateTable();
      /*  Tooltip tooltip = new Tooltip();
        tooltip.setText("\nYour password must be\n");
        rech.setTooltip(tooltip);
        */
        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }

}
