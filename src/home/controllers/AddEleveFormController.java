package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEleveFormController implements Initializable {


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
    private JFXTextField birthPlace;


    @FXML
    private JFXTextField classe;
    @FXML
    private JFXTextField schoolYear;


    @FXML
    private JFXTextField regime;
    @FXML
    private JFXTextField addresse;
    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXToggleButton stat;
    @FXML
    private JFXTextField maladie;


    @FXML
    private JFXTextField nameFather;
    @FXML
    private JFXTextField firstNameMother;
    @FXML
    private JFXTextField lastNameMother;


    @FXML
    private JFXTextField fonction;
    @FXML
    private JFXTextField fonction1;

    @FXML
    private JFXTextField remarque;


    @FXML
    void actionToggleButton() {
        if (stat.isSelected()) {
            maladie.setDisable(false);

        } else {
            maladie.setDisable(true);

        }

    }

    @FXML
    void btnAdd() {

        Eleve eleve = new Eleve();
        eleve.setId(Integer.valueOf(id.getText()));
        eleve.setNom(lastNameField.getText().trim().toLowerCase());
        eleve.setPrenom(firstNameField.getText().trim().toLowerCase());
        eleve.setDateNaissance(Date.valueOf(birthDate.getValue()));
        eleve.setLieuNaissance(birthPlace.getText().trim().toLowerCase());
        eleve.setClasse(classe.getText().trim().toLowerCase());
        eleve.setAnneeScolaire(schoolYear.getText().trim().toLowerCase());
        eleve.setRegime(regime.getText().trim().toLowerCase());
        eleve.setAdresse(addresse.getText().trim().toLowerCase());
        eleve.setPhone(phoneNumber.getText().trim().toLowerCase());
        eleve.setMaladie(maladie.getText().trim().toLowerCase());
        eleve.setPrenomPere(nameFather.getText().trim().toLowerCase());
        eleve.setPrenomMere(firstNameMother.getText().trim().toLowerCase());
        eleve.setNomMere(lastNameMother.getText().trim().toLowerCase());
        eleve.setTravailPere(fonction.getText().trim().toLowerCase());
        eleve.setTravailMere(fonction1.getText().trim().toLowerCase());
        eleve.setRemarque(remarque.getText().trim().toLowerCase());


        int status = new EleveDB().addEleve(eleve);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Eleve exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Eleve");
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                EleveController.addUserDialog.close();
        }


    }

    @FXML
    void btnClear() {
        id.setText(null);
        firstNameField.setText(null);
        lastNameField.setText(null);
        birthDate.setValue(null);
        birthPlace.setText(null);
        classe.setText(null);
        schoolYear.setText(null);
        regime.setText(null);
        addresse.setText(null);
        phoneNumber.setText(null);
        stat.setSelected(false);
        maladie.setText(null);
        nameFather.setText(null);
        firstNameMother.setText(null);
        lastNameMother.setText(null);
        fonction.setText(null);
        fonction1.setText(null);

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

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }
}

