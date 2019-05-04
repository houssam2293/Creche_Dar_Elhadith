package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Français", "العربية"
            );

    @FXML
    private HBox usernameOption;

    @FXML
    private HBox emailOption;

    @FXML
    private HBox passwordOption;

    @FXML
    private HBox languageOption;

    @FXML
    private HBox savePoint;
    @FXML
    private VBox changeUsernamePaneFull;

    @FXML
    private Label headerLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private HBox boxError,saveBox;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox changeUsernamePane;

    @FXML
    private TextField newUsernameUserPart;

    @FXML
    private PasswordField currentPasswordUserPart;

    @FXML
    private VBox changeEmailPane;

    @FXML
    private TextField newEmailEmailPart;

    @FXML
    private PasswordField currentPasswordEmailPart;

    @FXML
    private VBox changePasswordPane;

    @FXML
    private PasswordField currentPasswordPassPart;

    @FXML
    private PasswordField newPasswordPassPart;

    @FXML
    private PasswordField verifyPasswordPassPart;

    @FXML
    private FontAwesomeIconView iconValid;
    @FXML
    private VBox save;

    @FXML
    private VBox changeLanguagePane;

    @FXML
    private JFXComboBox<String> comboLanguage;

    @FXML
    private VBox changeThemePane;

    @FXML
    private JFXComboBox<?> comboTheme;


    @FXML
    void btnSave(ActionEvent event) {
        if (changeUsernamePane.isVisible()) {
            saveUsername();
        } else if (changeEmailPane.isVisible()) {
            saveEmail();
        } else if (changePasswordPane.isVisible()) {
            savePassword();
        } else if (changeLanguagePane.isVisible()) {
            saveLanguage();
        }

    }

    private void saveLanguage() {
    }

    private void saveEmail() {
    }

    private void savePassword() {
    }

    private void saveUsername() {
    }


    @FXML
    public void btnCloseErrorMsg() {
        boxError.setPrefHeight(0);
        boxError.setVisible(false);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboLanguage.setItems(options);
        comboLanguage.getSelectionModel().select(0);
        addListenerOption();

        // max length of username
        newUsernameUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newUsernameUserPart.getText().length() > 25) {
                newUsernameUserPart.setText(newUsernameUserPart.getText().substring(0, 25));
                newUsernameUserPart.positionCaret(newUsernameUserPart.getText().length());
            }
        });

        /* missing code here for ... */

        // max length for password
        currentPasswordUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (currentPasswordUserPart.getText().length() > 25) {
                currentPasswordUserPart.setText(currentPasswordUserPart.getText().substring(0, 25));
                currentPasswordUserPart.positionCaret(currentPasswordUserPart.getText().length());
            }
        });

        currentPasswordEmailPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (currentPasswordEmailPart.getText().length() > 25) {
                currentPasswordEmailPart.setText(currentPasswordEmailPart.getText().substring(0, 25));
                currentPasswordEmailPart.positionCaret(currentPasswordEmailPart.getText().length());
            }
        });

        currentPasswordPassPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (currentPasswordPassPart.getText().length() > 50) {
                currentPasswordPassPart.setText(currentPasswordPassPart.getText().substring(0, 50));
                currentPasswordPassPart.positionCaret(currentPasswordPassPart.getText().length());
            }
        });

        newPasswordPassPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newPasswordPassPart.getText().length() > 50) {
                newPasswordPassPart.setText(newPasswordPassPart.getText().substring(0, 50));
                newPasswordPassPart.positionCaret(newPasswordPassPart.getText().length());
            }
            showIconPassPart();
        });

        verifyPasswordPassPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (verifyPasswordPassPart.getText().length() > 50) {
                verifyPasswordPassPart.setText(verifyPasswordPassPart.getText().substring(0, 50));
                verifyPasswordPassPart.positionCaret(verifyPasswordPassPart.getText().length());
            }
            showIconPassPart();
        });
    }

    private void showIconPassPart() {
        // This method show / hide password
        if (newPasswordPassPart.getText().isEmpty() || verifyPasswordPassPart.getText().isEmpty()) {
            iconValid.setVisible(false);
            return;
        }
        if (newPasswordPassPart.getText().equals(verifyPasswordPassPart.getText())) {
            iconValid.setVisible(true);
        } else {
            iconValid.setVisible(false);
        }
    }

    private void addListenerOption() {
        usernameOption.setOnMouseClicked(e -> {
            // Show the content of selected option
            changeUsernamePane.setVisible(true);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            // Make label option selected bold and reset other option to normal
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("إسم المستخدم");
            contentLabel.setText("تغيير إسم المستخدم");

            // Remove value from field
            newUsernameUserPart.setText("");
            currentPasswordUserPart.setText("");
        });
        emailOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(true);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("البريد الإلكتروني");
            contentLabel.setText("تغيير البريد الإلكتروني");

            // Remove value from field
            newEmailEmailPart.setText("");
            currentPasswordEmailPart.setText("");
        });
        passwordOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(true);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(252);

            // Change the text in top selected box
            headerLabel.setText("كلمة المرور");
            contentLabel.setText("تغيير كلمة المرور");

            // Remove value from field
            newPasswordPassPart.setText("");
            currentPasswordPassPart.setText("");
            verifyPasswordPassPart.setText("");
        });
        savePoint.setOnMouseClicked(e -> {

            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            saveBox.setVisible(false);
            save.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: bold");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("حفظ البيانات");
            contentLabel.setText(" ");

        });


        languageOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(true);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("اللغة");
            contentLabel.setText("تغيير اللغة");

            comboLanguage.getSelectionModel().select(2);
        });


    }


    @FXML
    private void serialiser(){
        ArrayList<Employe> serialization = new ArrayList<Employe>();
        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                serialization.add(new Employe(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                        employe.getLieuNaissance(), employe.getAdresse(), employe.getNumTelephone(), employe.getSocialSecurityNumber(), employe.getDiplome(),
                        employe.getExperience(), employe.getItar(), employe.getRenouvlement_de_contrat(), employe.getDate_debut(), employe.getFonction(), employe.getStatuSocial(),
                        employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
            }
        }
        try {
            File file = new File("employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)) ;;
            out.writeObject(serialization) ;
            out.close();

            System.out.printf("Serialized data is saved in employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    @FXML
    private void deserialiser(){
        ArrayList<Employe> deserialization = new ArrayList<Employe>();
        try {
            FileInputStream fileIn = new FileInputStream("employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserialization=(ArrayList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        }catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    @FXML
    public void btnSaveArchiv(ActionEvent actionEvent) {
    }
}
