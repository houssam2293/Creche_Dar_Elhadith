package home.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();

    private final String filename = (s + "/loginCredentials.properties");
    private InputStream input = null;
    private String pass, username;

    @FXML
    private RadioButton francais_radio;

    @FXML
    private ToggleGroup lang;

    @FXML
    private RadioButton arabic_radio;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label errorlabel;

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        if (checkPass()) {
            Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/main.fxml"));
            Stage window = (Stage) francais_radio.getScene().getWindow();
            window.setResizable(true);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            window.setX(bounds.getMinX());
            window.setY(bounds.getMinY());
            window.setWidth(bounds.getWidth());
            window.setHeight(bounds.getHeight());
            window.setScene(new Scene(root));
        } else {
            errorlabel.setText("إسم المستخدم أو كلمة السر غير متطابقة!");
            hideText();

        }
    }


    @FXML
    void resetPasseword(ActionEvent event) {
        Stage stage;
        Parent root = null;
        //get reference - stage
        stage = (Stage) francais_radio.getScene().getWindow();
        stage.close();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/home/fxml/loginEntry.fxml"));
        } catch (IOException ignored) {
        }

        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.setResizable(false);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                user.requestFocus();
            }
        });


    }


    private boolean checkPass() {
        Properties properties = new Properties();
        try {
            input = new FileInputStream(filename);

            if (input != null) {
                System.out.println("file recovered");
                properties.load(input);
                username = properties.getProperty("user");
                pass = properties.getProperty("password");
            } else {
                System.out.println("file not recovered!");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DigestUtils.shaHex(password.getText()).equals(pass) && DigestUtils.shaHex(user.getText()).equals(username);
    }

    public void onEnter(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogin(new ActionEvent());
        } else {
            errorlabel.setText("إسم المستخدم أو كلمة السر غير متطابقة!");
            hideText();

        }

    }

    private void hideText() {

        Timeline textFade = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setNode(errorlabel);
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
        textFade.setOnFinished(e -> errorlabel.setText(""));

    }
}
