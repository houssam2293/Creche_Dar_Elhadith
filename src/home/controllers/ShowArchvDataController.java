package home.controllers;

import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowArchvDataController  implements Initializable {


    @FXML
    private AnchorPane ArchvData;
    @FXML
    private Label Id,nam,fnam,birthday,classe,anee,adress,namfather,namMather,prof,remarque;
    @FXML
    private ImageView Pic;


    static Eleve eleveSelected;

    public void initialize(URL location, ResourceBundle resources) {
        String nom = eleveSelected.getId() + "-" + eleveSelected.getPrenom() + "-" + eleveSelected.getNom();
        System.out.println(nom);
        File outputFile = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\Image\\" + nom + ".png");
        File outputFile1 = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\Image\\" + nom + ".jpg");
        File outputFile2 = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\Image\\" + nom + ".jpeg");

        if (outputFile.exists()) {
            Image imgThumb = new Image(outputFile.toURI().toString());
            Pic.setImage(imgThumb);
        } else if (outputFile1.exists()) {
            Image imgThumb = new Image(outputFile1.toURI().toString());
            Pic.setImage(imgThumb);
        } else if (outputFile2.exists()) {
            Image imgThumb = new Image(outputFile2.toURI().toString());
            Pic.setImage(imgThumb);
        }

        Id.setText(String.valueOf(eleveSelected.getId()));
        nam.setText(eleveSelected.getNom());
        fnam.setText(eleveSelected.getPrenom());
        birthday.setText(String.valueOf(eleveSelected.getDateNaissance()) +"      في  "+eleveSelected.getLieuNaissance());
        classe.setText(eleveSelected.getClasse());
        anee.setText(String.valueOf(eleveSelected.getAnneeScolaire()));
        adress.setText(eleveSelected.getAdresse());
        namfather.setText(eleveSelected.getPrenomPere());
        namMather.setText(eleveSelected.getPrenomMere()+"  "+eleveSelected.getNomMere());
        remarque.setText(eleveSelected.getRemarque());


    }

    @FXML
    void ClssPictr() {
        Stage stage = new Stage();
        File file = new File("C:\\Users\\houss\\Desktop\\max.jpg");
        Image image = new Image(file.toURI().toString());
        //clssImg.setImage(image);
        ImageView imageView = new ImageView(image);
        Group root = new Group(imageView);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);
        //Creating a scene object
        Scene scene = new Scene(root, 800, 600);

        //Setting title to the Stage
        stage.setTitle("Loading an image");

        //Adding scene to the stage
        stage.setScene(scene);
        //Displaying the contents of the stage
        stage.show();
    }
    @FXML
    void btnClose() {
        ((Stage) ArchvData.getScene().getWindow()).close();
    }


    @FXML
    void printFile() {
        Text textArea = new Text();
        textArea.setText("Printing test!    Printing test!\nPrinting test!  Printing test!  Printing test!\nPrinting test!  Printing test!  Printing test!  Printing test!");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(null);
        Printer printer= job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        textArea.setScaleX(0.85);
        textArea.setScaleY(0.85);
        textArea.setTranslateX(50);
        textArea.setTranslateY(10);
        job.printPage(pageLayout,textArea);
        job.endJob();
        textArea.setScaleX(1);
        textArea.setScaleY(1);
        textArea.setTranslateX(0);
        textArea.setTranslateY(0);
        ((Stage) ArchvData.getScene().getWindow()).close();

    }
}
