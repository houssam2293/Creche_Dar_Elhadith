package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import home.dbDir.PointageDB;
import home.java.PointageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
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
    private JFXButton rech;
    @FXML
    private JFXDatePicker dateJ;
    @FXML
    private Label listevide;
    private TableColumn idCol, fullNameCol, jobCol, remarqCol;


    @FXML
    void btnClose() {
        Pointage.listeAbsences.close();
    }

    @FXML
    void updateTable() {
        List<PointageModel> pointagesses = FXCollections.observableArrayList();
        ObservableList<PointageModel> data = null;
        data = FXCollections.observableArrayList();

        List<PointageModel> pointageModelDB = new PointageDB().getPointage(dateJ.getValue());
        if (pointageModelDB == null) {

            listevide.setText("لا يوجد غيابات");
            listevide.setTextFill(Color.web("#D50E21"));


        } else {
            for (PointageModel poi : pointageModelDB) {
                pointagesses.add(poi);
                System.out.println("try");

            }
            for (int i = 0; i < pointagesses.size(); i++) {
                PointageModel ep = pointagesses.get(i);
                listevide.setText("");
                System.out.println("i= " + i);
                data.add(ep);
                System.out.println(ep.getFirstName() + " zid " + ep.getId());


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
                new PropertyValueFactory<PointageModel, String>("remarkk")
        );
        tableview.setItems(data);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol = new TableColumn("رقم التسجيل");

        idCol.setPrefWidth(120);
        fullNameCol = new TableColumn("اسم و لقب العامل");
        fullNameCol.setPrefWidth(120);
        jobCol = new TableColumn("الوظيفة");
        jobCol.setPrefWidth(120);
        remarqCol = new TableColumn("ملاحظات");
        remarqCol.setPrefWidth(120);
        tableview.getColumns().addAll(idCol, fullNameCol, jobCol, remarqCol);

        // updateTable();
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
