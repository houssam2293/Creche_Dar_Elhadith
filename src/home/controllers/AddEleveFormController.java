package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEleveFormController {
    private ObservableList<Integer> options =
            FXCollections.observableArrayList(
                    0,1,2,3,4,5,6,7,8
            );
    private Integer sommeChildren=0;


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
    private JFXTextField fonction;

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
    private JFXTextField malad;

    @FXML
    private Label sommeChild;

    @FXML
    private JFXComboBox<Integer> maleChild;

    @FXML
    private JFXComboBox<Integer> femaleChild;



    @FXML
    void actionToggleButton() {
        if (stat.isSelected()){
            malad.setDisable(false);

        }else {
            malad.setDisable(true);

        }

    }

    @FXML
    void btnAdd() {


    }

    @FXML
    void btnClear() {
        id.setText(null);
        firstNameField.setText(null);
        lastNameField.setText(null);
        birthDate.setValue(null);
        birthPlace.setText(null);
        itar.setText(null);
        addresse.setText(null);
        phoneNumber.setText(null);
        stat.setSelected(false);
        fonction.setText(null);

    }

    @FXML
    void btnClose() {
        EleveController.addUserDialog.close();

    }



    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                btnAdd();
            }
        });

        root.setOnKeyPressed(e->{
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }

    private Integer calculSomme(Integer a,Integer b) {
        return a+b;
    }
}

