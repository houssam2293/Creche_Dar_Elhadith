package home.controllers;

import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
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
            /*PrinterJob job = PrinterJob.createPrinterJob();
            Printer printer= job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            job.printPage(pageLayout,root);
        job.endJob();*/
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null) {
            boolean showDialog = job.showPageSetupDialog(null);
            if (showDialog) {
                root.setScaleX(-0.70);
                root.setScaleY(0.70);
                root.setTranslateX(10);
                root.setTranslateY(10);
                boolean success = job.printPage(root);
                if (success) {
                    job.endJob();
                }
                root.setTranslateX(0);
                root.setTranslateY(0);
                root.setScaleX(1.0);
                root.setScaleY(1.0);
            }
        }
        //todo: fix this next


    }


    private void populateScene() {
        nameLable.setText(eleveDeLaClasse.getNom() + " " + eleveDeLaClasse.getPrenom());
        birthdayLable.setText(eleveDeLaClasse.getDateNaissance().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
