package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AddUserFormController {

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
    private JFXDatePicker firstDayOfwork;

    @FXML
    private JFXTextField numbreAnneeService;

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


}
