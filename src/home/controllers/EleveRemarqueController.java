package home.controllers;

import com.jfoenix.controls.*;
import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class EleveRemarqueController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField remarque;

    static Eleve notedEleve;

    @FXML
    void btnOk(){

        Eleve eleve = new Eleve();
        eleve.setId(notedEleve.getId());
        eleve.setRemarque(remarque.getText().trim().toLowerCase());
        int id=eleve.getId();


        int status = new EleveDB().notes(id, eleve);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Eleve does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Eleve");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                EleveController.notesUserDialog.close();

    }}


    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                btnOk();
            }
        });

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                EleveController.notesUserDialog.close();
            }
        });


        remarque.setText(notedEleve.getRemarque());
    }

}
