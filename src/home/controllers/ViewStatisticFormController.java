package home.controllers;

import com.jfoenix.controls.JFXButton;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStatisticFormController implements Initializable {


    @FXML
    private Label userSelected;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnChartType;

    @FXML
    private AnchorPane barChartPane;

    @FXML
    private BarChart statisticUserBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private List<String> employesNames;

    @FXML
    void btnClose() {
        TracController.detailChart.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmployeDB employeDB = new EmployeDB();
        List<Employe> employes = employeDB.getEmployee();
        employesNames = new ArrayList<>();
        for (Employe employe : employes) {
            employesNames.add(employe.getNom() + " " + employe.getPrenom());
        }
        for (String s : employesNames) {
            System.out.println("Current employee : " + s);
        }

    }
}
