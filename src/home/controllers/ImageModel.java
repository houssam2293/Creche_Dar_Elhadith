package home.controllers;

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
    private ImageView modelPicture, imageHolder;

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
    private Eleve eleveDeLaClasse = new Eleve();


    void setStudentClasse(String selectedItem,Eleve eleve) {
        classeLable.setText(selectedItem);
        eleveDeLaClasse=eleve;
            populateScene();
    }


    private void populateScene() {
        System.out.println("eleveDeLaClasse : "+eleveDeLaClasse.getNom() + " " + eleveDeLaClasse.getPrenom());
        nameLable.setText(eleveDeLaClasse.getNom() + " " + eleveDeLaClasse.getPrenom());
        birthdayLable.setText(eleveDeLaClasse.getDateNaissance().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
