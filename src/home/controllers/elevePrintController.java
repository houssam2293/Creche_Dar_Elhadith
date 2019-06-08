package home.controllers;

import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ESCAPE;

public class elevePrintController implements Initializable {

    static Eleve elevePrinted;


    @FXML
    private VBox root;
    @FXML
    private Label id;
    @FXML
    private Label firstname;
    @FXML
    private Label surname;
    @FXML
    private Label classRoom;
    @FXML
    private Label adresse;
    @FXML
    private Label phone;
    @FXML
    private Label phone1;
    @FXML
    private Label phone2;
    @FXML
    private ImageView imageview;

    @FXML
    void btnClose() {
        EleveController.printUserDialog.close();

    }

    @FXML
    void btnPrint() {
        //EleveController.printUserDialog.print();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                EleveController.printUserDialog.close();
            }
        });

        id.setText(String.valueOf(elevePrinted.getId()));
        surname.setText(elevePrinted.getNom());
        firstname.setText(elevePrinted.getPrenom());
        classRoom.setText(elevePrinted.getClasse());
        adresse.setText(elevePrinted.getAdresse());
        phone.setText(elevePrinted.getPhone());


    }
}
