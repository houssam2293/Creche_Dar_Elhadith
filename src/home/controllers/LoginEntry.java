package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LoginEntry {

    private Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField password1;
    @FXML
    private Text erreurText;
    @FXML
    private JFXButton login;

    @FXML
    void annuler(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void confirmer(ActionEvent event) {
        Properties prop = new Properties();
        erreurText.setText("كلمة السر غير متطابقة!");
        OutputStream output = null;
        InputStream inputStream = null;
        String userName = user.getText();
        String passeWord = password.getText();

        if (checkPass()) {
            try {

                output = new FileOutputStream(s + "/loginCredentials.properties");
                inputStream = new FileInputStream(s + "/loginCredentials.properties");
                prop.load(inputStream);

                inputStream.close();
                // set the properties value
                prop.putIfAbsent("user", DigestUtils.shaHex(userName));
                prop.putIfAbsent("password", DigestUtils.shaHex(passeWord));
                // save properties to project root folder
                prop.store(output, "fichier config");

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                        Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/login.fxml"));
                        Stage window = (Stage) user.getScene().getWindow();
                        window.close();
                        window.setScene(new Scene(root));
                        window.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            erreurText.setVisible(true);
        }
    }


    private boolean checkPass() {
        return password.getText().equals(password1.getText());
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            confirmer(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) annuler(new ActionEvent());
    }
}
