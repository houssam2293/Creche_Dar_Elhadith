package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

import java.net.URL;
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
    private VBox changeUsernamePaneFull;

    @FXML
    private Label headerLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private HBox boxError;

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

            // Make label option selected bold and reset other option to normal
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
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
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
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
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
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
        languageOption.setOnMouseClicked(e -> {
            changeUsernamePane.setVisible(false);
            changeEmailPane.setVisible(false);
            changePasswordPane.setVisible(false);
            changeLanguagePane.setVisible(true);
            usernameOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            emailOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            passwordOption.getChildren().get(0).setStyle("-fx-font-weight: normal");
            languageOption.getChildren().get(0).setStyle("-fx-font-weight: bold");
            btnCloseErrorMsg();
            changePasswordPane.setPrefHeight(0);

            // Change the text in top selected box
            headerLabel.setText("اللغة");
            contentLabel.setText("تغيير اللغة");

            comboLanguage.getSelectionModel().select(2);
        });


    }
}
