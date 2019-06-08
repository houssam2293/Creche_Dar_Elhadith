package home.controllers;

import com.jfoenix.controls.*;
import home.dbDir.ClasseDB;
import home.dbDir.EleveDB;
import home.dbDir.fraisDB;
import home.dbDir.tarifsDB;
import home.java.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEleveFormController implements Initializable {


    private ObservableList<String> typeRegime =
            FXCollections.observableArrayList(
                    "صباح", "مساء", "صباح+مساء", "صباح+نصف داخلي", "اليوم كامل"
            );

    private ObservableList<Integer> anneeScolaire =
            FXCollections.observableArrayList(
                    1, 2
            );

    private ObservableList<Integer> LesTranches =
            FXCollections.observableArrayList(
                    0, 1, 2, 3, 4
            );


    private double matin;
    private double aprem;
    private double mataprem;
    private double demi;
    private double complet;

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
    private JFXToggleButton toggler;
    @FXML
    private JFXComboBox<Integer> tranches;

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
    void trancheOn() {
        if (toggler.isSelected()) {
            tranches.setDisable(false);

        } else {
            tranches.setDisable(true);

        }

    }

    private static BufferedImage readImageFromFile(File file)
            throws IOException {
        return ImageIO.read(file);
    }

    @FXML
    void btnAdd() {

        double montant = 0;
        Eleve eleve = new Eleve();
        eleve.setId(Integer.valueOf(id.getText()));
        eleve.setNom(lastNameField.getText().trim().toLowerCase());
        eleve.setPrenom(firstNameField.getText().trim().toLowerCase());
        eleve.setDateNaissance(Date.valueOf(birthDate.getValue()));
        eleve.setLieuNaissance(birthPlace.getText().trim().toLowerCase());
        eleve.setClasse(classesCombos.getValue());
        eleve.setAnneeScolaire(schoolYear.getValue());
        eleve.setRegime(regime.getValue());
        switch (regime.getValue()) {
            case "صباح":
                montant = matin;
                break;
            case "مساء":
                montant = aprem;
                break;
            case "صباح+مساء":
                montant = mataprem;
                break;
            case "صباح+نصف داخلي":
                montant = demi;
                break;
            case "اليوم كامل":
                montant = complet;
                break;
        }
        if (toggler.isSelected()) {
            eleve.setTranches(tranches.getValue());
            eleve.setMontantRestant(montant);
        } else {
            eleve.setTranches(0);
            eleve.setMontantRestant(0);
        }
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
                if (!toggler.isSelected()) {
                    Frais fraisEleve = new Frais();
                    double total = montant;
                    fraisEleve.setFraisEleve(montant);

                    int status2 = new fraisDB().addFraisEleve(fraisEleve);
                    switch (status2) {
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
                } else EleveController.addUserDialog.close();
        }


    }

    @FXML
    private void addImage() {

        String nompic = id.getText() + "-" + lastNameField.getText() + "-" + firstNameField.getText();
        System.out.println(nompic);
        Stage stg = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stg);
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i + 1);
        }
        System.out.println("Image extension is : " + extension);
        Image imgThumb = new Image(file.toURI().toString());

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("image files (png,jpg,jpeg,bmp,gif)", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File dir = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\Image");
        dir.mkdirs();
        File fileSav = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\Image\\" + nompic + "." + extension);
        BufferedImage bImage = null;
        try {
            bImage = readImageFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(bImage, extension, fileSav);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnClose() {
        EleveController.addUserDialog.close();

    }

    @FXML
    private void btnClear() {
        id.setText(null);
        gender1.setSelected(false);
        gender2.setSelected(false);
        firstNameField.setText(null);
        lastNameField.setText(null);
        birthDate.setValue(null);
        birthPlace.setText(null);
        classesCombos.getItems().clear();
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clss : clsDB) {
                classesCombos.getItems().add(clss.getClassNam());

            }
        }
        schoolYear.getSelectionModel().select(null);
        regime.getSelectionModel().select(null);
        addresse.setText(null);
        phoneNumber.setText(null);
        toggler.setSelected(false);
        tranches.getSelectionModel().select(null);
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
    void hit1() {
        gender2.setSelected(false);
    }

    @FXML
    void hit2() {
        gender1.setSelected(false);
    }

    private void SettingTarifs() {
        Tarifs LesTarifs = new tarifsDB().getTarifs();
        matin = LesTarifs.getMatin();
        aprem = LesTarifs.getAprem();
        mataprem = LesTarifs.getMatAprem();
        demi = LesTarifs.getDemi();
        complet = LesTarifs.getComplet();

    }

    public void initialize(URL location, ResourceBundle resources) {

        SettingTarifs();

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
        schoolYear.setItems(anneeScolaire);
        tranches.setItems(LesTranches);
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clss : clsDB) {
                classesCombos.getItems().add(clss.getClassNam());

            }
        }

    }

    private void valider() {

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
            if (a > 0) {
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
       /* classe.setOnKeyReleased(t -> {
            if (new Validation().arabValid(classe)) {
                classe.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                classe.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });*/
       /* schoolYear.setOnKeyReleased(t -> {
            if (new Validation().isNumber(schoolYear)) {
                schoolYear.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");

            } else {
                schoolYear.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");

            }
        });*/
       /* regime.setOnKeyReleased(t -> {
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

