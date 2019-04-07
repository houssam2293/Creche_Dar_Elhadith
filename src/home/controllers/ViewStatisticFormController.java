package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ViewStatisticFormController {

    @FXML
    private Label userSelected;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnChartType;

    @FXML
    private AnchorPane tablePane;

    @FXML
    private JFXTreeTableView<?> tableView;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXComboBox<?> comboSearchBy;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane chartPane;

    @FXML
    private AnchorPane barChartPane;

    @FXML
    private BarChart<?, ?> statisticUserBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private AnchorPane pieChartPane;

    @FXML
    private PieChart numberErrorPieChart;

    @FXML
    private PieChart totalSolvedPieChart;

    @FXML
    void btnClose() {

    }

}