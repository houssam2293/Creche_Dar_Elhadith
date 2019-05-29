package home.controllers;

import home.java.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowArchvDataController  implements Initializable {


    @FXML
    private AnchorPane ArchvData;
    @FXML
    private Label Id,nam,fnam,birthday,classe,anee,adress,namfather,namMather,prof,remarque;

    static Eleve eleveSelected;

    public void initialize(URL location, ResourceBundle resources) {

        Id.setText(String.valueOf(eleveSelected.getId()));
        nam.setText(eleveSelected.getNom());
        fnam.setText(eleveSelected.getPrenom());
        birthday.setText(String.valueOf(eleveSelected.getDateNaissance()) +"      في  "+eleveSelected.getLieuNaissance());
        classe.setText(eleveSelected.getClasse());
        anee.setText(eleveSelected.getAnneeScolaire());
        adress.setText(eleveSelected.getAdresse());
        namfather.setText(eleveSelected.getPrenomPere());
        namMather.setText(eleveSelected.getPrenomMere()+"  "+eleveSelected.getNomMere());
        remarque.setText(eleveSelected.getRemarque());
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
        textArea.minWidth(pageLayout.getPrintableWidth());
        textArea.minHeight(pageLayout.getPrintableHeight());
        job.printPage(pageLayout,textArea);
        job.endJob();
        ((Stage) ArchvData.getScene().getWindow()).close();

    }
}
