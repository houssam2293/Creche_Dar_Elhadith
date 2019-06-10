package home.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FirstLoad {

    @FXML
    private JFXTextField host;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField passeword;


    @FXML
    void hundleAbordClick(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void hundleOkClick(ActionEvent event) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        Properties prop = new Properties();
        OutputStream output = null;
        String hst = host.getText();
        String utilisateur = user.getText();
        String motDePasse = passeword.getText();
        String dbname = "creche_dar_elhadith";


        if (hst == null || utilisateur == null) {
            System.exit(0);
        } else {
            try {

                output = new FileOutputStream(s + "/dbCredentials.properties");

                // set the properties value
                prop.setProperty("database", hst);
                prop.setProperty("dbname", dbname);
                prop.setProperty("dbuser", utilisateur);
                prop.setProperty("dbpassword", motDePasse);


                prop.store(output, "fichier config");

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                        Parent root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/loginEntry.fxml"));
                        Stage window = (Stage) user.getScene().getWindow();
                        window.setScene(new Scene(root));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            hundleOkClick(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            hundleAbordClick(new ActionEvent());

        }
    }
}


