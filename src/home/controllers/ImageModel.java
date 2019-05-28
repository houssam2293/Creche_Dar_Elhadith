package home.controllers;

import home.java.Eleve;
import home.java.NodePrinter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
    static boolean imageDeleted = false;


    void setImageHolder(Image image) {
        imageDeleted = false;
        imageHolder.setImage(image);
    }

    void deleteImage() {
        imageHolder.setImage(null);
        imageDeleted = true;
    }

    void setStudentClasse(String selectedItem, Eleve eleve) {
        classeLable.setText(selectedItem);
        eleveDeLaClasse = eleve;
        populateScene();
    }

    void print() {
        PrinterJob job = PrinterJob.createPrinterJob();
        //todo: fix this next
        NodePrinter printer = new NodePrinter();
        boolean success = printer.print(job, true, root);
        if (success) {
            job.endJob();
        }

    }


    private void populateScene() {
        nameLable.setText(eleveDeLaClasse.getNom() + " " + eleveDeLaClasse.getPrenom());
        birthdayLable.setText(eleveDeLaClasse.getDateNaissance().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
