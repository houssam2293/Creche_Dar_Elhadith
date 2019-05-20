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

public class EditEleveFormController implements Initializable {

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


    static Eleve eleveSelected;


    @FXML
    void actionToggleButton() {
        if (stat.isSelected()) {
            maladie.setDisable(false);

        } else {
            maladie.setDisable(true);

        }

    }


    @FXML
    void btnEdit() {

        Eleve eleve = new Eleve();
        eleve.setId(eleveSelected.getId());
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


        int status = new EleveDB().editEleve(eleve);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Eleve does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Eleve");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                EleveController.editUserDialog.close();
        }

    }

    @FXML
    void btnClear() {
        Eleve eleve = new Eleve();
        eleve.setId(eleveSelected.getId());
        eleve.setNom(lastNameField.getText().trim().toLowerCase());
        eleve.setPrenom(firstNameField.getText().trim().toLowerCase());
        eleve.setDateNaissance(Date.valueOf(birthDate.getValue()));
        eleve.setLieuNaissance(birthPlace.getText().trim().toLowerCase());
        eleve.setAdresse(addresse.getText().trim().toLowerCase());
        eleve.setPhone(phoneNumber.getText().trim().toLowerCase());
        eleve.setClasse(classe.getText().trim().toLowerCase());
        eleve.setRegime(regime.getText().trim().toLowerCase());
        eleve.setMaladie(maladie.getText().trim().toLowerCase());
        eleve.setAnneeScolaire(schoolYear.getText().trim().toLowerCase());
        eleve.setPrenomPere(nameFather.getText().trim().toLowerCase());
        eleve.setPrenomMere(firstNameMother.getText().trim().toLowerCase());
        eleve.setNomMere(lastNameMother.getText().trim().toLowerCase());
        eleve.setTravailPere(fonction.getText().trim().toLowerCase());
        eleve.setTravailMere(fonction1.getText().trim().toLowerCase());
        eleve.setRemarque(remarque.getText().trim().toLowerCase());

    }

    @FXML
    void btnClose() {

        EleveController.editUserDialog.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                btnClear();
            }
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });
        stat.setOnAction(event -> actionToggleButton());


        id.setText(String.valueOf(eleveSelected.getId()));
        lastNameField.setText(eleveSelected.getNom());
        firstNameField.setText(eleveSelected.getPrenom());
        birthDate.setValue(java.time.LocalDate.parse(String.valueOf(eleveSelected.getDateNaissance())));
        birthPlace.setText(eleveSelected.getLieuNaissance());
        classe.setText(eleveSelected.getClasse());
        schoolYear.setText(eleveSelected.getAnneeScolaire());
        addresse.setText(eleveSelected.getAdresse());
        regime.setText(eleveSelected.getRegime());
        phoneNumber.setText(eleveSelected.getPhone());
        nameFather.setText(eleveSelected.getPrenomPere());
        firstNameMother.setText(eleveSelected.getPrenomMere());
        lastNameMother.setText(eleveSelected.getNomMere());
        fonction.setText(eleveSelected.getTravailPere());
        fonction1.setText(eleveSelected.getTravailMere());
        maladie.setText(eleveSelected.getMaladie());
        remarque.setText(eleveSelected.getRemarque());

    }}
