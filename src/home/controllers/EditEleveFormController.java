package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.ClasseDB;
import home.dbDir.EleveDB;
import home.java.ClasseModel;
import home.java.Eleve;
import home.java.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class EditEleveFormController implements Initializable {


    private ObservableList<String> typeRegime =
            FXCollections.observableArrayList(
                    "صباح", "مساء", "صباح+مساء", "صباح+نصف داخلي", "اليوم كامل"
            );
    private ObservableList<Integer> anneeScolaire =
            FXCollections.observableArrayList(
                    1, 2
            );

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
    private JFXComboBox<String> classesCombos;
    @FXML
    private JFXComboBox<Integer> schoolYear;


    @FXML
    private JFXComboBox<String> regime;
    @FXML
    private JFXTextField addresse;
    @FXML
    private JFXTextField phoneNumber;

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


    static Eleve eleveSelected;





    @FXML
    void btnEdit() {

        Eleve eleve = new Eleve();
        eleve.setId(eleveSelected.getId());
        eleve.setNom(lastNameField.getText().trim().toLowerCase());
        eleve.setPrenom(firstNameField.getText().trim().toLowerCase());
        eleve.setDateNaissance(Date.valueOf(birthDate.getValue()));
        eleve.setLieuNaissance(birthPlace.getText().trim().toLowerCase());
        eleve.setClasse(classesCombos.getValue());
        eleve.setAnneeScolaire(schoolYear.getValue());
        eleve.setRegime(regime.getValue());
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
        eleve.setClasse(classesCombos.getValue());
        eleve.setRegime(regime.getValue());
        eleve.setMaladie(maladie.getText().trim().toLowerCase());
        eleve.setAnneeScolaire(schoolYear.getValue());
        eleve.setPrenomPere(nameFather.getText().trim().toLowerCase());
        eleve.setPrenomMere(firstNameMother.getText().trim().toLowerCase());
        eleve.setNomMere(lastNameMother.getText().trim().toLowerCase());
        eleve.setTravailPere(fonction.getText().trim().toLowerCase());
        eleve.setTravailMere(fonction1.getText().trim().toLowerCase());
        eleve.setWakil(wakil.getText().trim().toLowerCase());
        eleve.setRemarque(remarque.getText().trim().toLowerCase());
        if (eleve.getGender()==1)
            gender1.setSelected(true);
        else gender2.setSelected(true);

    }

    @FXML
    void btnClose() {

        EleveController.editUserDialog.close();
    }

    @FXML
    void hit1(){
        gender2.setSelected(false);
    }


    @FXML
    void hit2(){
        gender1.setSelected(false);
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

        root.setOnKeyReleased(e -> {
            valider();
        });

        regime.setItems(typeRegime);
        schoolYear.setItems(anneeScolaire);


        id.setText(String.valueOf(eleveSelected.getId()));
        lastNameField.setText(eleveSelected.getNom());
        firstNameField.setText(eleveSelected.getPrenom());
        birthDate.setValue(LocalDate.parse(String.valueOf(eleveSelected.getDateNaissance())));
        birthPlace.setText(eleveSelected.getLieuNaissance());
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clss : clsDB) {
                classesCombos.getItems().add(clss.getClassNam());

            }
        }
        classesCombos.getSelectionModel().select(eleveSelected.getClasse());
        if (eleveSelected.getAnneeScolaire() == 1) {
            schoolYear.getSelectionModel().select(0);
        } else {
            schoolYear.getSelectionModel().select(1);
        }
        addresse.setText(eleveSelected.getAdresse());
        regime.getSelectionModel().select(eleveSelected.getRegime());
        phoneNumber.setText(eleveSelected.getPhone());
        nameFather.setText(eleveSelected.getPrenomPere());
        firstNameMother.setText(eleveSelected.getPrenomMere());
        lastNameMother.setText(eleveSelected.getNomMere());
        fonction.setText(eleveSelected.getTravailPere());
        fonction1.setText(eleveSelected.getTravailMere());
        maladie.setText(eleveSelected.getMaladie());
        wakil.setText(eleveSelected.getWakil());
        remarque.setText(eleveSelected.getRemarque());
        if (eleveSelected.getGender()==1)
            gender1.setSelected(true);
        else gender2.setSelected(true);

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
        /*classe.setOnKeyReleased(t -> {
            if (new Validation().arabValid(classe)) {
                classe.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                classe.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        /*schoolYear.setOnKeyReleased(t -> {
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
        });*/

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
