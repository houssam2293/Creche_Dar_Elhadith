package home.controllers;

import com.jfoenix.controls.*;
import home.dbDir.EmployeDB;
import home.dbDir.PointageDB;
import home.java.Employe;
import home.java.Pointage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.xml.soap.Text;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;

import static javafx.scene.input.KeyCode.ENTER;
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
    ObservableList<Pointage> data ;
    TableColumn idCol,fullNameCol,jobCol,remarqCol;


    @FXML
    void btnClose() {
        Pointag.listeAbsences.close();

    }
    @FXML
    void updateTable(){


        List<Pointage> pointagess = FXCollections.observableArrayList();
        data = null ;
        data = FXCollections.observableArrayList();

        List<Pointage> pointageDB = new PointageDB().getPointage(dateJ.getValue());
        if (pointageDB == null) {

            listevide.setText("لا يوجد غيابات");
            listevide.setTextFill(Color.web("#D50E21"));


        } else {
            for (Pointage poi : pointageDB) {
                pointagess.add(poi);
                System.out.println("try");

            }
            for (int i=0; i < pointagess.size();i++){
                Pointage ep = pointagess.get(i);
                listevide.setText("");
                System.out.println("i= "+i);
                data.add(ep);
                System.out.println(ep.getFirstName()+" zid "+ep.getId());


            }
        }


        idCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("id")
        );
        fullNameCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("firstName")
        );
        jobCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("lastName")
        );

        remarqCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("remarkk")
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
        tableview.getColumns().addAll(idCol, fullNameCol, jobCol,remarqCol );

        // updateTable();
      /*  Tooltip tooltip = new Tooltip();
        tooltip.setText("\nYour password must be\n");
        rech.setTooltip(tooltip);
        */
        root.setOnKeyPressed(e->{
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }

}
