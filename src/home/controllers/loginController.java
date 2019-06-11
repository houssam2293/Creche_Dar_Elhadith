package home.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.CompteDB;
import home.java.Compte;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private Path currentRelativePath = Paths.get("");
    private String s = currentRelativePath.toAbsolutePath().toString();

    private final String filename = (s + "/loginCredentials.properties");
    private InputStream input = null;
    private String pass, username;

    private CompteDB compteDB;

    @FXML
    private AnchorPane root;



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
            ((Stage) francais_radio.getScene().getWindow()).close();
            Parent root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/main.fxml"));
            Stage window = new Stage();
            window.initStyle(StageStyle.DECORATED);
            window.setResizable(true);
            window.setTitle("مؤسسة دار الحديث");
            window.getIcons().add(new Image("/home/resources/images/logo.png"));
            window.setFullScreen(true);
            window.setFullScreenExitHint("أنقر على 'Echap' للخروج من وضع شاشة كاملة");
            window.setScene(new Scene(root));
            window.show();
        } else {
            errorlabel.setText("إسم المستخدم أو كلمة السر غير متطابقة!");
            hideText();

        }
    }




    @FXML
    void btnClose() {
        ((Stage) errorlabel.getScene().getWindow()).close();
    }

    @FXML
    void resetPasseword(ActionEvent event) {
        Stage stage;
        Parent root = null;
        //get reference - stage
        stage = (Stage) francais_radio.getScene().getWindow();
        stage.close();
        stage = new Stage();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/resetPassword.fxml"));
        } catch (IOException ignored) {
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/home/resources/images/logo.png"));
        stage.setTitle("استعادة كلمة المرور");
        //create a new scene with root and set the stage
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        compteDB = new CompteDB();
        Platform.runLater(() -> user.requestFocus());
        francais_radio.setOnAction(event -> {
            Notifications notification = Notifications.create()
                    .title("سوف يتم توفير التطبيق باللغة الفرنسية عن قريب في التحديث المقبل ")
                    .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();
            arabic_radio.setSelected(true);
        });
    }


    private boolean checkPass() {
        boolean check = false;
        List<Compte> accounts = compteDB.getAccounts();
        if (!accounts.isEmpty()) {
            for (Compte compte : accounts) {
                if (compte.getLogin().equals(user.getText())
                        && compte.getPassword().equals(DigestUtils.shaHex(password.getText()))) {
                    username = compte.getLogin();
                    pass = compte.getPassword();
                    check = true;
                    SettingsController.currentUser = compte;
                }
            }
        } else {
            Properties properties = new Properties();
            try {
                input = new FileInputStream(filename);

                System.out.println("file recovered");
                properties.load(input);
                username = properties.getProperty("user");
                pass = properties.getProperty("password");
                check = DigestUtils.shaHex(password.getText()).equals(pass) && DigestUtils.shaHex(user.getText()).equals(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return check;
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
