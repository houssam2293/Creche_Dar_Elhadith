package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;

public class SettingsController {

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
    private JFXComboBox<?> comboLanguage;

    @FXML
    private VBox changeThemePane;

    @FXML
    private JFXComboBox<?> comboTheme;


    @FXML
    void btnSave(ActionEvent event) {

    }

    @FXML
    public void btnCloseErrorMsg(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
