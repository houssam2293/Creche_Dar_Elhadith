package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TracController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane choosePane,employeeAnchor,studentAnchor;

    @FXML
    private VBox studentPane;

    @FXML
    private Label errorLabelEmploye,errorLabelStudent;

    @FXML
    private JFXTextField searchStudentField;

    @FXML
    private JFXComboBox<?> comboStudentSearchBy;

    @FXML
    private HBox searchToolsBox;

    @FXML
    private JFXComboBox<?> comboStudentSectionFilter;

    @FXML
    private JFXTreeTableView<?> tableEmployeeTrac;

    @FXML
    private PieChart pieChartEmploye;

    @FXML
    private PieChart pieChartEmployeRigime;

    @FXML
    private VBox employePane;

    @FXML
    private JFXTextField searchEmployeField;

    @FXML
    private JFXComboBox<?> comboEmployeSearchBy;

    @FXML
    private JFXTreeTableView<?> tableStudentTrac;

    @FXML
    private PieChart pieChartStudent;

    @FXML
    private PieChart pieChartRigimeStudent;

    @FXML
    Pane studentBox,employeeBox;

    @FXML
    void btnFilterStudent() {

    }


    @FXML
    void btnViewStatisticStudent() {

    }

    @FXML
    void btnBackward() {
        employePane.setVisible(false);
        studentPane.setVisible(false);
        choosePane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(choosePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void showEmploye() {

        employePane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(employePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    @FXML
    void showStudent() {
        btnSearchToolsEmploye();
        initializeTableEmploye();

        studentPane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(studentPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    private void initializeTableEmploye() {

    }

    private void btnSearchToolsEmploye() {
    }

    @FXML
    void updateTableEmploye() {

    }

    @FXML
    void updateTableUser() {

    }

    @FXML
    void viewChartEmploye() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXRippler rippler = new JFXRippler(studentBox);
        JFXRippler rippler1 = new JFXRippler(employeeBox);
        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        employeeAnchor.getChildren().add(rippler1);
        studentAnchor.getChildren().add(rippler);

    }
}
