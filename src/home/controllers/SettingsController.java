package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import home.dbDir.CompteDB;
import home.dbDir.fraisDB;
import home.dbDir.tarifsDB;
import home.java.Compte;
import home.java.FileVisitorImpl;
import home.java.Frais;
import home.java.Tarifs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class SettingsController extends Component implements Initializable {


    private ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Français", "العربية"
            );

    @FXML
    private Label date;

    @FXML
    private JFXTextField sumCharity;

    @FXML
    private JFXTextField from;

    @FXML
    private VBox charity;

    @FXML
    private TextField eleveFull;

    @FXML
    private TextField eleveDemi;

    @FXML
    private TextField eleveMatAprem;

    @FXML
    private TextField eleveAprem;

    @FXML
    private TextField eleveMatin;

    @FXML
    private VBox money;

    @FXML
    private HBox charityOptions;

    @FXML
    private HBox moneyOptions;

    @FXML
    private HBox usernameOption;

    @FXML
    private HBox emailOption;

    @FXML
    private HBox passwordOption;

    @FXML
    private HBox languageOption;

    @FXML
    private HBox newUserOption;

    @FXML
    private HBox savePoint;
    @FXML
    private VBox changeUsernamePaneFull;

    @FXML
    private Label headerLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private HBox boxError, saveBox;

    @FXML
    private Label errorLabel, accountUser;

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
    private VBox addNewUserPane;

    @FXML
    private TextField newUsernameNewUserPart;

    @FXML
    private PasswordField newPasswordUserPart;

    @FXML
    private PasswordField newConfirmationPasswordUserPart;

    @FXML
    private FontAwesomeIconView iconValid;
    @FXML
    private VBox save;

    @FXML
    private VBox changeLanguagePane;

    @FXML
    private JFXComboBox<String> comboLanguage;

    static Compte currentUser;
    private CompteDB compteDB;


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
        } else if (addNewUserPane.isVisible()) {
            addNewUser();
        } else if (moneyOptions.isVisible()) {
            saveMoney();
        }

    }

    private void addNewUser() {

    }

    private void saveLanguage() {
        errorLabel.setText("");
    }

    private void saveEmail() {
        errorLabel.setText("");
        Compte compte = compteDB.getAcountInformation(currentUser.getEmail());
        System.out.println("Curent user email : " + currentUser.getEmail());
        System.out.println("New email : " + newEmailEmailPart.getText());
        if (currentUser.getPassword().equals(DigestUtils.shaHex(currentPasswordEmailPart.getText()))) {
            compte.setEmail(newEmailEmailPart.getText());
            int status = compteDB.editAccount(compte, "login");
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
                            .title("تمت العملية بنجاح                                   ")
                            .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                            .hideAfter(Duration.millis(2000))
                            .position(Pos.BOTTOM_RIGHT)
                            .darkStyle()
                            .show();
                    newEmailEmailPart.setText(null);
                    currentPasswordEmailPart.setText(null);
            }
        } else {
            errorLabel.setText("كلمة المرور خاطئة!");
        }
    }

    private void savePassword() {
        errorLabel.setText("");
        Compte compte = compteDB.getAcountInformation(currentUser.getEmail());
        if (currentUser.getPassword().equals(DigestUtils.shaHex(currentPasswordPassPart.getText()))) {
            if (newPasswordPassPart.getText().equals(verifyPasswordPassPart.getText())) {
                compte.setPassword(compte.getHashedPassword(newPasswordPassPart.getText()));
                int status = compteDB.editAccount(compte, "email");
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
                                .title("تمت العملية بنجاح                                   ")
                                .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                                .hideAfter(Duration.millis(2000))
                                .position(Pos.BOTTOM_RIGHT)
                                .darkStyle()
                                .show();
                        newPasswordPassPart.setText(null);
                        currentPasswordPassPart.setText(null);
                        verifyPasswordPassPart.setText(null);
                }
            } else {
                Notifications.create()
                        .title("كلمتا المرور غير متطابقتين!                            ")
                        .graphic(new ImageView(new Image("/home/resources/icons/icons8_Cancel_48px.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();
            }
        } else {
            errorLabel.setText("كلمة المرور خاطئة!");
        }
    }

    private void saveUsername() {
        errorLabel.setText("");

        Compte compte = compteDB.getAcountInformation(currentUser.getEmail());
        if (currentUser.getPassword().equals(DigestUtils.shaHex(currentPasswordUserPart.getText()))) {
            compte.setLogin(newUsernameUserPart.getText());
            int status = compteDB.editAccount(compte, "email");
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
                            .title("تمت العملية بنجاح                                   ")
                            .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                            .hideAfter(Duration.millis(2000))
                            .position(Pos.BOTTOM_RIGHT)
                            .darkStyle()
                            .show();
                    newUsernameUserPart.setText(null);
                    currentPasswordUserPart.setText(null);
            }
        } else {
            errorLabel.setText("كلمة المرور خاطئة!");
        }
    }

    private void saveMoney() {
        Tarifs tarifs = new Tarifs();
        tarifs.setTarifsSets(1);
        tarifs.setMatin(Double.valueOf(eleveMatin.getText()));
        tarifs.setAprem(Double.valueOf(eleveAprem.getText()));
        tarifs.setMatAprem(Double.valueOf(eleveMatAprem.getText()));
        tarifs.setDemi(Double.valueOf(eleveDemi.getText()));
        tarifs.setComplet(Double.valueOf(eleveFull.getText()));

        boolean existingTarifs = new tarifsDB().tarifsExist();
        if (existingTarifs) {
            int status = new tarifsDB().updateTarifs(tarifs);
            switch (status) {
                case -1:
                    System.out.println("Error connecting to DB!");
                    break;
                case 2:
                    System.out.println("Error Tarifs exist!");
                    break;
                case 0:
                    System.out.println("Unknown Error failed to update Tarifs");
                    break;
                case 1:
                    Notifications.create()
                            .title("تمت الإضافة بنجاح                                   ")
                            .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                            .hideAfter(Duration.millis(2000))
                            .position(Pos.BOTTOM_RIGHT)
                            .darkStyle()
                            .show();
            }
        } else {

            int status = new tarifsDB().setTarifs(tarifs);
            switch (status) {
                case -1:
                    System.out.println("Error connecting to DB!");
                    break;
                case 2:
                    System.out.println("Error Tarifs exist!");
                    break;
                case 0:
                    System.out.println("Unknown Error failed to add Tarifs");
                    break;
                case 1:
                    Notifications.create()
                            .title("تمت الإضافة بنجاح                                   ")
                            .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                            .hideAfter(Duration.millis(2000))
                            .position(Pos.BOTTOM_RIGHT)
                            .darkStyle()
                            .show();
            }
        }

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
        compteDB = new CompteDB();
        accountUser.setText(currentUser.getLogin());

        // max length of username
        newUsernameUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newUsernameUserPart.getText().length() > 25) {
                newUsernameUserPart.setText(newUsernameUserPart.getText().substring(0, 25));
                newUsernameUserPart.positionCaret(newUsernameUserPart.getText().length());
            }
        });

        newUsernameNewUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newUsernameNewUserPart.getText().length() > 25) {
                newUsernameNewUserPart.setText(newUsernameNewUserPart.getText().substring(0, 25));
                newUsernameNewUserPart.positionCaret(newUsernameNewUserPart.getText().length());
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
        newPasswordUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newPasswordUserPart.getText().length() > 50) {
                newPasswordUserPart.setText(newPasswordUserPart.getText().substring(0, 50));
                newPasswordUserPart.positionCaret(newPasswordUserPart.getText().length());
            }
        });
        newConfirmationPasswordUserPart.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (newConfirmationPasswordUserPart.getText().length() > 50) {
                newConfirmationPasswordUserPart.setText(newConfirmationPasswordUserPart.getText().substring(0, 50));
                newConfirmationPasswordUserPart.positionCaret(newConfirmationPasswordUserPart.getText().length());
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
            charity.setVisible(false);
            money.setVisible(false);
            addNewUserPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            // Make label option selected bold and reset other option to normal
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

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
            charity.setVisible(false);
            money.setVisible(false);
            addNewUserPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

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
            charity.setVisible(false);
            money.setVisible(false);
            changePasswordPane.setVisible(true);
            addNewUserPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(252);
            addNewUserPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("كلمة المرور");
            contentLabel.setText("تغيير كلمة المرور");

            // Remove value from field
            newPasswordPassPart.setText("");
            currentPasswordPassPart.setText("");
            verifyPasswordPassPart.setText("");
        });

        newUserOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            charity.setVisible(false);
            money.setVisible(false);
            changePasswordPane.setVisible(false);
            addNewUserPane.setVisible(true);
            changeLanguagePane.setVisible(false);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(252);

            // Change the text in top selected box
            headerLabel.setText("إضافة مستخدم");
            contentLabel.setText("إضافة مستخدم جديد");

            // Remove value from field
            newUsernameNewUserPart.setText("");
            newPasswordUserPart.setText("");
            newConfirmationPasswordUserPart.setText("");
        });

        savePoint.setOnMouseClicked(e -> {

            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            charity.setVisible(false);
            money.setVisible(false);
            addNewUserPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            saveBox.setVisible(false);
            save.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: bold");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("حفظ البيانات");
            contentLabel.setText(" ");

        });


        languageOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            charity.setVisible(false);
            money.setVisible(false);
            addNewUserPane.setVisible(false);
            changeLanguagePane.setVisible(true);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("اللغة");
            contentLabel.setText("تغيير اللغة");

            comboLanguage.getSelectionModel().select(2);
        });

        moneyOptions.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            addNewUserPane.setVisible(false);
            charity.setVisible(false);
            money.setVisible(true);
            save.setVisible(false);
            saveBox.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: bold");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("أسعار التسجيل");
            contentLabel.setText("تحديد أسعار التسجيل");
        });

        charityOptions.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(false);
            addNewUserPane.setVisible(false);
            money.setVisible(false);
            save.setVisible(false);
            charity.setVisible(true);
            saveBox.setVisible(false);
            date.setText(String.valueOf(LocalDate.now()));
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            moneyOptions.getChildren().get(0).setStyle("-fx-font-weight: normal");
            newUserOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            savePoint.getChildren().get(0).setStyle("-fx-font-weight: normal");
            charityOptions.getChildren().get(0).setStyle("-fx-font-weight: bold");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);
            addNewUserPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("إدارة دفع المحسنين");
            contentLabel.setText("تحديد دفع المحسنين");
        });


    }


    @FXML
    private void serialiser() {
        String path = null;
        String filename;
        Stage stg = new Stage();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MySql files (*.sql)", "*.sql");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(stg);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (file != null) {
            try {

                path = file.getAbsolutePath();
                path = path.replace('\\', '/');
                path = path + "_" + date + ".sql";

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }

            Process p;
            p = null;
            try {
                Path startingDir = Paths.get("C:\\");
                String fileName = "mysqldump.exe";
                FileVisitorImpl visitor = new FileVisitorImpl();
                visitor.setStartDir(startingDir);
                visitor.setFileName(fileName);
                Files.walkFileTree(startingDir, visitor);
                String filePathe = visitor.getFilePath();
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec(filePathe + " -uroot -proot --add-drop-database -B creche_dar_elhadith -r" + path);

                int processComplete = p.waitFor();
                if (processComplete == 0) {
                    System.out.println("Backup Created Succuss");
                } else {
                    System.out.println("Can't Create backup");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void deserialiser() {
        String path = null;

        Stage stg = new Stage();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MySql files (*.sql)", "*.sql");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showOpenDialog(stg);

        if (file != null) {
            try {
                path = file.getAbsolutePath();
                path = path.replace('\\', '/');


            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
            Path startingDir = Paths.get("C:\\");
            String fileName = "mysql.exe";
            FileVisitorImpl visitor = new FileVisitorImpl();
            visitor.setStartDir(startingDir);
            visitor.setFileName(fileName);
            try {
                Files.walkFileTree(startingDir, visitor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filePathe = visitor.getFilePath();
            String[] restoreCmd = new String[]{filePathe, "--user=root", "--password=root", "-e", "source " + path};
            Process runtimProcess;
            try {
                runtimProcess = Runtime.getRuntime().exec(restoreCmd);
                int proceCom = runtimProcess.waitFor();

                if (proceCom == 0) {
                    System.out.println("Restored Succuss");
                } else {
                    System.out.println("Can't Restored");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void btnSaveArchiv(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/home/resources/fxml/addArchiv.fxml"));
        AnchorPane rootLayout = loader.load();
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);


        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void btnAdd() {
        Frais frais = new Frais();
        frais.setFraisCharity(Double.valueOf(sumCharity.getText()));

        int status = new fraisDB().addFraisCharity(frais);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Frais does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Frais");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                sumCharity.setText(null);
                from.setText(null);
        }
    }

    @FXML
    private void btnPrint() {
        Text textArea = new Text();
        //textArea.setText("ﺔﻋﺎﺒﻄﻟﺍ ﺭﺎﺒﺘﺧﺍ ﺔﻋﺎﺒﻄﻟﺍ ﺭﺎﺒﺘﺧﺍ ﺔﻋﺎﺒﻄﻟﺍ ﺭﺎﺒﺘﺧﺍ ");
        textArea.setText("من السماء");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(null);
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        textArea.setScaleX(-1);
        textArea.setScaleY(1);
        textArea.setTranslateX(10);
        textArea.setTranslateY(10);
        job.printPage(pageLayout, textArea);
        job.endJob();
        textArea.setScaleX(1);
        textArea.setScaleY(1);
        textArea.setTranslateX(0);
        textArea.setTranslateY(0);
    }
}
