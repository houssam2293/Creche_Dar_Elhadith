package home.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.CompteDB;
import home.java.Compte;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPassword implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label topLabel;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField passwordConfirmation;

    @FXML
    private Text erreurText;

    private CompteDB compteDB;
    private boolean accountExist = false;
    private String eMail;

    private double xOffset;
    private double yOffset;

    @FXML
    void annuler() {
        btnClose();
        Stage stage;
        Parent root = null;
        stage = new Stage();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/login.fxml"));
        } catch (IOException ignored) {
        }
        stage.initStyle(StageStyle.UNDECORATED);
        assert root != null;
        Scene scene = new Scene(root);
        stage.setTitle("مؤسسة دار الحديث");
        stage.getIcons().add(new Image("/home/resources/icons/icon.png"));
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void btnClose() {
        ((Stage) topLabel.getScene().getWindow()).close();
    }

    @FXML
    void confirmer() {
        List<Compte> accoutList = compteDB.getAccounts();
        if (!emailValidation()) {
            erreurText.setText("يرجى إدخال بريد إلكتروني صحيح!");
            erreurText.setVisible(true);
            hideText();
        } else {
            for (Compte compte : accoutList) {
                if (email.getText().equals(compte.getEmail())) {
                    accountExist = true;
                    compte.getEmail();
                }
            }
            if (checkPass()) {

            }
        }


    }

    private boolean emailValidation() {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }

    private boolean checkPass() {
        return password.getText().equals(passwordConfirmation.getText());
    }

    private void hideText() {

        Timeline textFade = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setNode(erreurText);
            ft.setDuration(new Duration(3000));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(0);
            ft.setAutoReverse(true);
            ft.play();
        }),
                new KeyFrame(Duration.seconds(3))
        );
        textFade.play();
        textFade.setOnFinished(e -> erreurText.setText(""));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compteDB = new CompteDB();
        topLabel.setOnMousePressed(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        topLabel.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        topLabel.setOnMouseEntered(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.HAND);
        });

        topLabel.setOnMouseExited(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setCursor(Cursor.DEFAULT);
        });
    }
}
