package home.controllers;

import com.jfoenix.controls.*;
import home.controllers.EleveController;
import home.java.Eleve;
import home.dbDir.EleveDB;
import home.java.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEleveFormController implements Initializable {


    @FXML
    private VBox root;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXRadioButton gender1;

    @FXML
    private JFXRadioButton gender2;


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
    private JFXTextField wakil;

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
        eleve.setWakil(wakil.getText().trim().toLowerCase());
        eleve.setRemarque(remarque.getText().trim().toLowerCase());
        if (gender1.isSelected())
            eleve.setGender(1);
        else eleve.setGender(2);


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
        gender1.setSelected(false);
        gender2.setSelected(false);
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
        wakil.setText(null);
        remarque.setText(null);

    }

    @FXML
    void btnClose() {
        EleveController.addUserDialog.close();

    }

    @FXML
    void hit1(){
        gender2.setSelected(false);
    }

    @FXML
    void hit2(){
        gender1.setSelected(false);
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

        root.setOnKeyReleased(e -> {
            valider();
        });

    }

    void valider(){

        id.setOnKeyReleased(t -> {
            if (new Validation().isNumber(id)) {
                id.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");

            } else {
                id.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");

            }
        });
        firstNameField.setOnKeyReleased(t -> {
            if (new Validation().arabValid(firstNameField)) {
                firstNameField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                firstNameField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        lastNameField.setOnKeyReleased(t -> {
            if (new Validation().arabValid(lastNameField)) {
                lastNameField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                lastNameField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        birthDate.setOnKeyReleased(t -> {
            LocalDate date = LocalDate.parse("2005-01-01");// On suppose que l'employer agé de 15 ou plus
            LocalDate birthdat = birthDate.getValue();
            int a = date.compareTo(birthdat);
            if (a>0) {
                birthDate.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                birthDate.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        birthPlace.setOnKeyReleased(t -> {
            if (/*new Validation().arabValid(birthPlace)*/true) {
                birthPlace.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                birthPlace.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        classe.setOnKeyReleased(t -> {
            if (new Validation().arabValid(classe)) {
                classe.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                classe.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        schoolYear.setOnKeyReleased(t -> {
            if (new Validation().arabValid(schoolYear)) {
                schoolYear.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                schoolYear.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        regime.setOnKeyReleased(t -> {
            if (new Validation().arabValid(regime)) {
                regime.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                regime.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        addresse.setOnKeyReleased(t -> {
            if (/*new Validation().arabValid(addresse)*/true) {
                addresse.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                addresse.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        phoneNumber.setOnKeyReleased(t -> {
            if (new Validation().phoneValid(phoneNumber)) {
                phoneNumber.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                phoneNumber.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        maladie.setOnKeyReleased(t -> {
            if (new Validation().arabValid(maladie) || new Validation().frenshValid(maladie)) {
                maladie.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                maladie.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        nameFather.setOnKeyReleased(t -> {
            if (new Validation().arabValid(nameFather)) {
                nameFather.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                nameFather.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        firstNameMother.setOnKeyReleased(t -> {
            if (new Validation().arabValid(firstNameMother)) {
                firstNameMother.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                firstNameMother.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        lastNameMother.setOnKeyReleased(t -> {
            if (new Validation().arabValid(lastNameMother)) {
                lastNameMother.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                lastNameMother.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        fonction.setOnKeyReleased(t -> {
            if (new Validation().arabValid(fonction)) {
                fonction.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                fonction.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        fonction1.setOnKeyReleased(t -> {
            if (new Validation().arabValid(fonction1)) {
                fonction1.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                fonction1.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        wakil.setOnKeyReleased(t -> {
            if (new Validation().arabValid(wakil)) {
                wakil.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                wakil.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        remarque.setOnKeyReleased(t -> {
            if (new Validation().arabValid(remarque)) {
                remarque.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                remarque.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });


    }
}

