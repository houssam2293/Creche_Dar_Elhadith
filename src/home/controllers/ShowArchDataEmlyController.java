package home.controllers;

import de.jensd.fx.glyphs.emojione.EmojiOneView;
import home.java.Employe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ShowArchDataEmlyController implements Initializable {




        @FXML
        private AnchorPane ArchvDataEmp;

        @FXML
        private Label Id;

        @FXML
        private Label nam;

        @FXML
        private Label fnam;

        @FXML
        private Label birthday;

        @FXML
        private Label job;

        @FXML
        private Label adress;

        @FXML
        private Label regime;

        @FXML
        private Label experience;

        @FXML
        private Label firstDayOfwork;

        @FXML
        private Label remarque;

        @FXML
        private EmojiOneView print;

        static Employe emplSelected;


    public void initialize(URL location, ResourceBundle resources) {

        Id.setText(String.valueOf(emplSelected.getId()));
        nam.setText(emplSelected.getNom());
        fnam.setText(emplSelected.getPrenom());
        birthday.setText(String.valueOf(emplSelected.getDateNaissance()) + "      في  " + emplSelected.getLieuNaissance());
        job.setText(emplSelected.getFonction());
        adress.setText(emplSelected.getAdresse());
        regime .setText(emplSelected.getRegimeScolaire());
        experience.setText(emplSelected.getExperience());
        firstDayOfwork.setText(String.valueOf(emplSelected.getDate_debut()));
        //remarque.setText(emplSelected.getRemarque());

}

    @FXML
    void btnClose() {
        ((Stage) ArchvDataEmp.getScene().getWindow()).close();
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
        ((Stage) ArchvDataEmp.getScene().getWindow()).close();

    }
}
