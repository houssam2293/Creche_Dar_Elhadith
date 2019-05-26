package home.controllers;

import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.DateTimeStringConverter;


import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ESCAPE;

public class ficheEleveController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label id;

    @FXML
    private Label firstname;
    @FXML
    private Label surname;

    @FXML
    private Label birthDay;
    @FXML
    private Label birthPlace;


    @FXML
    private Label classRoom;
    @FXML
    private Label schoolYear;


    @FXML
    private Label regime;
    @FXML
    private Label adresse;
    @FXML
    private Label phone;

    @FXML
    private Label maladie;


    @FXML
    private Label fatherName;
    @FXML
    private Label motherName;


    @FXML
    private Label fatherWork;
    @FXML
    private Label motherWork;
    @FXML
    private Label wakil;

    @FXML
    private Label remarque;
    @FXML
    private ImageView imageview;


    static Eleve eleveFiled;


    @FXML
    void btnClose() {
        EleveController.fileUserDialog.close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if (eleveFiled.getGender()==1){
            imageview.setImage(new Image("home/resources/images/boy.png"));
        }
        else imageview.setImage(new Image("home/resources/images/girl.png"));

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                EleveController.fileUserDialog.close();
            }
        });

        id.setText(String.valueOf(eleveFiled.getId()));
        surname.setText(eleveFiled.getNom());
        firstname.setText(eleveFiled.getPrenom());
        birthDay.setText(eleveFiled.getDateNaissance().toString());
        birthPlace.setText(eleveFiled.getLieuNaissance());
        classRoom.setText(eleveFiled.getClasse());
        schoolYear.setText(eleveFiled.getAnneeScolaire());
        adresse.setText(eleveFiled.getAdresse());
        regime.setText(eleveFiled.getRegime());
        phone.setText(eleveFiled.getPhone());
        fatherName.setText(eleveFiled.getPrenomPere());
        motherName.setText(eleveFiled.getPrenomMere());
        fatherWork.setText(eleveFiled.getTravailPere());
        motherWork.setText(eleveFiled.getTravailMere());
        maladie.setText(eleveFiled.getMaladie());
        wakil.setText(eleveFiled.getWakil());
        remarque.setText(eleveFiled.getRemarque());

    }}
