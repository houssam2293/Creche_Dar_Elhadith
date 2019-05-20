package home.controllers;

import com.jfoenix.controls.JFXTextArea;
import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ImageModel implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane namesGrid;

    @FXML
    private ImageView modelPicture;

    @FXML
    private Label nameLable;

    @FXML
    private Label directeurLable;

    @FXML
    private Label teacherLabel;

    @FXML
    private Label birthdayLable;

    @FXML
    private Label classeLable;
    private List<Eleve> eleveDeLaClasse = new ArrayList<>();
    private EleveDB eleveDB;

    void setStudentClasse(String selectedItem) {
        List<Eleve> eleves = eleveDB.getEleve();
        for (Eleve eleve : eleves) {
            if (eleve.getClasse().toUpperCase().equals(selectedItem)) {
                eleveDeLaClasse.add(eleve);
            }
            System.out.println("Current selected classe : " + selectedItem);
            System.out.println("Current Student classe in the list : " + eleve.getClasse());
        }

        if (eleveDeLaClasse.isEmpty()) {
            System.out.println("Import of Student list failed!");
        } else {
            populateScene();
        }
    }


    private void populateScene(){
        classeLable.setText(eleveDeLaClasse.get(0).getClasse().toUpperCase());
        nameLable.setText(eleveDeLaClasse.get(0).getNom() + " " + eleveDeLaClasse.get(0).getPrenom());
        birthdayLable.setText(eleveDeLaClasse.get(0).getDateNaissance().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eleveDB = new EleveDB();
    }



}
