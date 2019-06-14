package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.CompteDB;
import home.java.Compte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginEntry implements Initializable {

    private Path currentRelativePath = Paths.get("");
    private String s = currentRelativePath.toAbsolutePath().toString();
    @FXML
    private JFXTextField email, user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField password1;
    @FXML
    private Text erreurText;
    @FXML
    private JFXButton login;

    private Compte compte;
    private CompteDB compteDB;


    @FXML
    void annuler(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void confirmer(ActionEvent event) {
        Properties prop = new Properties();
        OutputStream output = null;
        InputStream inputStream;
        String eMail = email.getText();
        String userName = user.getText();
        String passeWord = password.getText();


        if (checkPass()) {
            erreurText.setVisible(false);
            if (!emailValidation()) {
                erreurText.setText("يرجى إدخال بريد إلكتروني صحيح!");
                erreurText.setVisible(true);
            } else {
                compte = new Compte(eMail, userName, passeWord);
                int status = compteDB.addAcount(compte);
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
                                .title("تمت الإضافة بنجاح                                   ")
                                .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                                .hideAfter(Duration.millis(2000))
                                .position(Pos.BOTTOM_RIGHT)
                                .darkStyle()
                                .show();
                }
                try {

                    output = new FileOutputStream(s + "/loginCredentials.properties");
                    inputStream = new FileInputStream(s + "/loginCredentials.properties");
                    prop.load(inputStream);

                    inputStream.close();
                    // set the properties value
                    prop.putIfAbsent("email", eMail);
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
                            Parent root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/login.fxml"));
                            Stage window = (Stage) user.getScene().getWindow();
                            window.close();
                            window = new Stage();
                            window.getIcons().add(new Image("/home/resources/images/logo.png"));
                            window.initStyle(StageStyle.UNDECORATED);
                            window.setScene(new Scene(root));
                            window.setTitle("مؤسسة دار الحديث");
                            window.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        } else {
            erreurText.setText("كلمة السر غير متطابقة!");
            erreurText.setVisible(true);
        }
    }

    private boolean emailValidation() {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }


    private boolean checkPass() {
        return password.getText().equals(password1.getText());
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            confirmer(new ActionEvent());
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) annuler(new ActionEvent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String filename = s + "/loginCredentials.properties";
        File file = new File(filename);

        compteDB = new CompteDB();
    }
}
