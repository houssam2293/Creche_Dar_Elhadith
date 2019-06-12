package home.controllers;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import home.dbDir.ClasseDB;
import home.dbDir.EmployeDB;
import home.java.ClasseModel;
import home.java.Employe;
import home.java.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEmployeeFormController implements Initializable {

    private ObservableList<Integer> options =
            FXCollections.observableArrayList(
                    0, 1, 2, 3, 4, 5, 6, 7, 8
            );
    private ObservableList<String> typeRegime =
            FXCollections.observableArrayList(
                    "صباح", "مساء", "صباح+مساء", "صباح+نصف داخلي", "اليوم كامل"
            );
    private Integer sommeChildren = 0;


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
    private JFXComboBox<String> fonction, classe;

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
    private JFXTextArea remarque;

    @FXML
    private JFXToggleButton stat, renouvlementContrat;

    @FXML
    private JFXTextField celibacyTitle;

    @FXML
    private Label sommeChild;

    @FXML
    private EmojiOneView print;

    @FXML
    private JFXComboBox<Integer> maleChild, femaleChild;

    @FXML
    private JFXComboBox<String> regime;

    private ObservableList<String> items = FXCollections.observableArrayList("معلم", "عامل");


    @FXML
    void actionToggleButton() {
        if (stat.isSelected()) {
            celibacyTitle.setDisable(false);
            sommeChild.setDisable(false);
            maleChild.setDisable(false);
            femaleChild.setDisable(false);
        } else {
            celibacyTitle.setDisable(true);
            sommeChild.setDisable(true);
            maleChild.setDisable(true);
            femaleChild.setDisable(true);
        }

    }

    @FXML
    void btnAdd() {
        Employe employe = new Employe();
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
        if (renouvlementContrat.isSelected())
            employe.setRenouvlement_de_contrat("نعم");
        else employe.setRenouvlement_de_contrat("لا");
        employe.setFonction(fonction.getValue().trim().toLowerCase());
        employe.setRegimeScolaire(regime.getValue());
        employe.setDate_debut(Date.valueOf(firstDayOfwork.getValue()));
        employe.setRemarque(remarque.getText());
        if (stat.isSelected()) {
            employe.setStatuSocial(1);
            employe.setCelibacyTitle(celibacyTitle.getText().trim().toLowerCase());
            employe.setMaleChild(maleChild.getValue());
            employe.setFemaleChild(femaleChild.getValue());
        } else employe.setStatuSocial(0);

        int status = new EmployeDB().addEmploye(employe);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Employer exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Employer");
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
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
        regime.getSelectionModel().select(null);
        renouvlementContrat.setSelected(false);
        fonction.setValue(null);

    }

    @FXML
    void btnClose() {
        ManageEmployeeController.addUserDialog.close();

    }


    private void valider() {
        // start of validation
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


        itar.setOnKeyReleased(t -> {
            if (new Validation().arabValid(itar)) {
                itar.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                itar.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        addresse.setOnKeyReleased(t -> {
            if (new Validation().arabValid(addresse)) {
                addresse.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                addresse.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        birthPlace.setOnKeyReleased(t -> {
            if (new Validation().arabValid(birthPlace)) {
                birthPlace.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                birthPlace.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        phoneNumber.setOnKeyReleased(t -> {
            if (new Validation().phoneValid(phoneNumber)) {
                phoneNumber.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                phoneNumber.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        socialSecurtyNumber.setOnKeyReleased(t -> {
            if (new Validation().alphanumValid(socialSecurtyNumber)) {
                socialSecurtyNumber.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                socialSecurtyNumber.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        diplome.setOnKeyReleased(t -> {
            if (new Validation().arabValid(diplome)) {
                diplome.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                diplome.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        celibacyTitle.setOnKeyReleased(t -> {
            if (new Validation().arabValid(celibacyTitle)) {
                celibacyTitle.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                celibacyTitle.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        experience.setOnKeyReleased(t -> {
            if (new Validation().isNumber(experience)) {
                experience.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                experience.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        birthDate.setOnAction(t -> {
            LocalDate date = LocalDate.parse("2005-01-01");// On suppose que l'employer agé de 15 ou plus
            LocalDate birthdat = birthDate.getValue();
            int a = date.compareTo(birthdat);
            if (a>0) {
                birthDate.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                birthDate.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        firstDayOfwork.setOnKeyReleased(t -> {
            LocalDate date = LocalDate.now();
            LocalDate birthdat = firstDayOfwork.getValue();
            int a = date.compareTo(birthdat);

            if (a>=0) {
                firstDayOfwork.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                firstDayOfwork.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Tooltip tooltip = new Tooltip("حفظ و طباعة");
        Tooltip.install(print, tooltip);
        Font font = new Font("Arial Bold", 12);
        tooltip.setFont(font);

        fonction.setItems(items);
        List<ClasseModel> cls = new ClasseDB().getClasse();
        ObservableList<String> classes = FXCollections.observableArrayList();
        for (ClasseModel classeModel : cls) {
            classes.add(classeModel.getClassNam());
        }
        List<Employe> employes = new EmployeDB().getEmployee();
        for (Employe employe : employes) {
            classes.remove(employe.getClasse());
        }
        classe.setItems(classes);
        fonction.setOnAction(event -> {
            if (fonction.getSelectionModel().getSelectedItem().equals("عامل")) {
                classe.setDisable(true);
            } else {
                classe.setDisable(false);
            }
        });

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

        root.setOnKeyReleased(e -> valider());

        regime.setItems(typeRegime);
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

    @FXML
    void printFile() {
        btnAdd();

    }

    private Integer calculSomme(Integer a, Integer b) {
        return a + b;
    }
}
