package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {
    ObservableList<Integer> options =
            FXCollections.observableArrayList(
                    0,1,2,3,4,5,6,7,8
            );
    Integer sommeChildren=0;

    @FXML
    private VBox root;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXDatePicker birthDate;

    @FXML
    private JFXTextField itar;

    @FXML
    private JFXTextField birthPlace;

    @FXML
    private JFXTextField addresse;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField socialSecurtyNumber;

    @FXML
    private JFXTextField diplome;

    @FXML
    private JFXDatePicker firstDayOfwork;

    @FXML
    private JFXTextField experience;

    @FXML
    private JFXToggleButton stat;

    @FXML
    private JFXTextField celibacyTitle;

    @FXML
    private Label sommeChild;

    @FXML
    private JFXComboBox<Integer> maleChild;

    @FXML
    private JFXComboBox<Integer> femaleChild;

    @FXML
    void actionToggleButton(ActionEvent event) {
        if (stat.isSelected()){
            celibacyTitle.setDisable(false);
            sommeChild.setDisable(false);
            maleChild.setDisable(false);
            femaleChild.setDisable(false);
        }else {
            celibacyTitle.setDisable(true);
            sommeChild.setDisable(true);
            maleChild.setDisable(true);
            femaleChild.setDisable(true);
        }

    }

    @FXML
    void btnAdd(ActionEvent event) {

    }

    @FXML
    void btnClear(ActionEvent event) {

    }

    @FXML
    void btnClose(MouseEvent event) {
        ManageAccountController.addUserDialog.close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maleChild.setItems(options);
        femaleChild.setItems(options);
        maleChild.getSelectionModel().select(0);
        femaleChild.getSelectionModel().select(0);
        sommeChild.setText(sommeChildren.toString());
        maleChild.setOnAction(event -> {
                sommeChildren = calculSomme(maleChild.getSelectionModel().getSelectedItem(),
                        femaleChild.getSelectionModel().getSelectedItem());
                sommeChild.setText(sommeChildren.toString());

            });
        femaleChild.setOnAction(event -> {
            sommeChildren = calculSomme(maleChild.getSelectionModel().getSelectedItem(),
                    femaleChild.getSelectionModel().getSelectedItem());
            sommeChild.setText(sommeChildren.toString());
        });
    }

    private Integer calculSomme(Integer a,Integer b) {
        return a+b;
    }
}
