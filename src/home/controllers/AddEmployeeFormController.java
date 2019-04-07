package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEmployeeFormController implements Initializable {
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
    private JFXTextField celibacyTitle;

    @FXML
    private Label sommeChild;

    @FXML
    private JFXComboBox<Integer> maleChild;

    @FXML
    private JFXComboBox<Integer> femaleChild;



    @FXML
    void actionToggleButton() {
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
    void btnAdd() {
        Employe employe =new Employe();
        employe.setId(Integer.valueOf(id.getText()));
        employe.setNom(lastNameField.getText().trim().toLowerCase());
        employe.setPrenom(firstNameField.getText().trim().toLowerCase());
        employe.setDateNaissance(Date.valueOf(birthDate.getValue()));
        employe.setLieuNaissance(birthPlace.getText().trim().toLowerCase());
        employe.setAdresse(addresse.getText().trim().toLowerCase());
        employe.setNumTelephone(phoneNumber.getText().trim().toLowerCase());
        employe.setSocialSecurityNumber(socialSecurtyNumber.getText().trim().toLowerCase());
        employe.setDiplome(diplome.getText().trim().toLowerCase());
        employe.setExperience(experience.getText().trim().toLowerCase());
        employe.setItar(itar.getText().trim().toLowerCase());
        employe.setRenouvlement_de_contrat("oui");
        employe.setFonction(fonction.getText().trim().toLowerCase());
        employe.setDate_debut(Date.valueOf(firstDayOfwork.getValue()));
        if (stat.isSelected()) {
            employe.setStatuSocial(1);
            employe.setCelibacyTitle(celibacyTitle.getText().trim().toLowerCase());
            employe.setMaleChild(maleChild.getValue());
            employe.setFemaleChild(femaleChild.getValue());
        }else employe.setStatuSocial(0);

        int status = new EmployeDB().addEmploye(employe);
        switch (status) {
            case -1:System.out.println("Error connecting to DB!");
            break;
            case 2:System.out.println("Error Employer exist!");
            break;
            case 0:System.out.println("Unknown Error failed to add Employer" );
            break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                ManageEmployeeController.addUserDialog.close();
        }
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
        socialSecurtyNumber.setText(null);
        diplome.setText(null);
        experience.setText(null);
        firstDayOfwork.setValue(null);
        celibacyTitle.setText(null);
        maleChild.getSelectionModel().select(null);
        femaleChild.getSelectionModel().select(null);
        sommeChild.setText(null);
        stat.setSelected(false);
        fonction.setText(null);

    }

    @FXML
    void btnClose() {
        ManageEmployeeController.addUserDialog.close();

    }


    @Override
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
