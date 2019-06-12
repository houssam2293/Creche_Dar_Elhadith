package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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

public class EditEmployeeFormController implements Initializable {
    private ObservableList<Integer> options =
            FXCollections.observableArrayList(
                    0, 1, 2, 3, 4, 5, 6, 7, 8
            );
    private ObservableList<String> typeRegime =
            FXCollections.observableArrayList(
                    "صباح","مساء","صباح+مساء","صباح+نصف داخلي","اليوم كامل"
            );
    private Integer sommeChildren = 0;

    @FXML
    private VBox root;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXDatePicker birthDate;

    @FXML
    private JFXTextField birthPlace;

    @FXML
    private JFXToggleButton stat, renouvlementContrat;

    @FXML
    private JFXTextField celibacyTitle;

    @FXML
    private Label sommeChild;

    @FXML
    private JFXComboBox<Integer> maleChild;

    @FXML
    private JFXComboBox<Integer> femaleChild;

    @FXML
    private JFXTextField addresse;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField socialSecurtyNumber;

    @FXML
    private JFXTextField diplome;

    @FXML
    private JFXTextField itar;

    @FXML
    private JFXComboBox<String> fonction, classe;

    @FXML
    private JFXDatePicker firstDayOfwork;

    @FXML
    private JFXTextField experience;

    @FXML
    private JFXComboBox<String> regime;

    static Employe employeeSelected;

    private ObservableList<String> items = FXCollections.observableArrayList("معلم", "عامل");

    @FXML
    void actionToggleButton() {
        if (stat.isSelected()) {
            celibacyTitle.setDisable(false);
            sommeChild.setDisable(false);
            maleChild.setDisable(false);
            femaleChild.setDisable(false);
        } else {
            celibacyTitle.setText(null);
            celibacyTitle.setDisable(true);
            sommeChild.setDisable(true);
            sommeChild.setText(null);
            maleChild.setDisable(true);
            femaleChild.setDisable(true);

        }
    }

    @FXML
    void btnEdit() {

        Employe employe = new Employe();
        employe.setId(employeeSelected.getId());
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
        if (fonction.getValue().equals("معلم")) {
            employe.setClasse(classe.getValue().trim().toLowerCase());
        } else {
            employe.setClasse(null);
        }

        employe.setRegimeScolaire(regime.getValue());
        employe.setDate_debut(Date.valueOf(firstDayOfwork.getValue()));
        if (stat.isSelected()) {
            employe.setStatuSocial(1);
            employe.setCelibacyTitle(celibacyTitle.getText().trim().toLowerCase());
            employe.setMaleChild(maleChild.getValue());
            employe.setFemaleChild(femaleChild.getValue());
        } else {
            employe.setStatuSocial(0);
            employe.setCelibacyTitle(null);
            employe.setMaleChild(0);
            employe.setFemaleChild(0);
        }

        int status = new EmployeDB().editEmployee(employe);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Employer does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Employer");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                ManageEmployeeController.editUserDialog.close();
        }

    }

    @FXML
    void btnReset() {
        Employe employe = new Employe();
        employe.setId(employeeSelected.getId());
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
        if (employe.getRenouvlement_de_contrat().equals("نعم"))
            renouvlementContrat.setSelected(true);
        else renouvlementContrat.setSelected(false);
        employe.setFonction(fonction.getValue().trim().toLowerCase());
        employe.setRegimeScolaire(regime.getValue());
        employe.setDate_debut(Date.valueOf(firstDayOfwork.getValue()));
        if (employe.estmarier()) {
            employe.setStatuSocial(1);

            employe.setCelibacyTitle(celibacyTitle.getText().trim().toLowerCase());
            employe.setMaleChild(maleChild.getValue());
            employe.setFemaleChild(femaleChild.getValue());
        } else employe.setStatuSocial(0);
        btnClose();
    }

    @FXML
    void btnClose() {

        ManageEmployeeController.editUserDialog.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                btnEdit();
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

        stat.setOnAction(event -> actionToggleButton());

        regime.setItems(typeRegime);
        maleChild.setItems(options);
        femaleChild.setItems(options);
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

        id.setText(String.valueOf(employeeSelected.getId()));
        firstNameField.setText(employeeSelected.getNom());
        lastNameField.setText(employeeSelected.getPrenom());
        birthDate.setValue(LocalDate.parse(String.valueOf(employeeSelected.getDateNaissance())));
        birthPlace.setText(employeeSelected.getLieuNaissance());
        itar.setText(employeeSelected.getItar());
        addresse.setText(employeeSelected.getAdresse());
        phoneNumber.setText(employeeSelected.getNumTelephone());
        socialSecurtyNumber.setText(employeeSelected.getSocialSecurityNumber());
        diplome.setText(employeeSelected.getDiplome());
        if (employeeSelected.getRenouvlement_de_contrat().equals("نعم"))
            renouvlementContrat.setSelected(true);
        else renouvlementContrat.setSelected(false);
        experience.setText(employeeSelected.getExperience());
        firstDayOfwork.setValue(LocalDate.parse(String.valueOf(employeeSelected.getDate_debut())));
        fonction.getSelectionModel().select(employeeSelected.getFonction());
        fonction.setOnAction(event -> {
            if (fonction.getSelectionModel().getSelectedItem().equals("عامل")) {
                classe.setDisable(true);
            } else {
                classe.setDisable(false);
            }
        });
        if (employeeSelected.getFonction().equals("معلم")) {
            classe.setDisable(false);
            if (employeeSelected.getClasse() != null) {
                classe.getItems().add(employeeSelected.getClasse());
                classe.getSelectionModel().select(employeeSelected.getClasse());
            }
        }
        regime.getSelectionModel().select(employeeSelected.getRegimeScolaire());

        if (employeeSelected.estmarier()) {
            celibacyTitle.setText(employeeSelected.getCelibacyTitle());
            stat.setSelected(true);
            actionToggleButton();
            maleChild.getSelectionModel().select(employeeSelected.getMaleChild());
            femaleChild.getSelectionModel().select(employeeSelected.getFemaleChild());
            sommeChildren = calculSomme(maleChild.getSelectionModel().getSelectedItem(),
                    femaleChild.getSelectionModel().getSelectedItem());
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
        } else {
            celibacyTitle.setText(null);
            sommeChild.setText(null);
            maleChild.getSelectionModel().select(null);
            femaleChild.getSelectionModel().select(null);
            stat.setSelected(false);

        }

    }

    private Integer calculSomme(Integer a, Integer b) {
        return a + b;
    }

    void valider() {
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
            if (/*new Validation().arabValid(addresse)*/true) {
                addresse.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                addresse.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        birthPlace.setOnKeyReleased(t -> {
            if (/*new Validation().arabValid(birthPlace)*/true) {
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
        firstDayOfwork.setOnKeyReleased(t -> {
            LocalDate date = LocalDate.now();
            LocalDate birthdat = firstDayOfwork.getValue();
            int a = date.compareTo(birthdat);

            if (a>-1) {
                firstDayOfwork.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                firstDayOfwork.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });


    }
}