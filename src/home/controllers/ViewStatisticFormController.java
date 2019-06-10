package home.controllers;

import com.jfoenix.controls.JFXButton;
import home.dbDir.EmployeDB;
import home.dbDir.PointageDB;
import home.java.Employe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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


    @FXML
    void btnClose() {
        TracController.detailChart.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("الحضور");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("الغياب");

        EmployeDB employeDB = new EmployeDB();
        List<Employe> employes = employeDB.getEmployee();
        for (Employe employe : employes) {
            String s = employe.getNom() + " " + employe.getPrenom();
            series1.getData().add(new XYChart.Data(s, new PointageDB().countPrecence(s)));
            series2.getData().add(new XYChart.Data(s, new PointageDB().countAbsence(s)));

        }

        statisticUserBarChart.setBarGap(3);
        statisticUserBarChart.setCategoryGap(40);
        statisticUserBarChart.getData().addAll(series1, series2);

    }
}
