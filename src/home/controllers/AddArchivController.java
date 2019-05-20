package home.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.EleveDB;
import home.dbDir.EmployeDB;
import home.java.Eleve;
import home.java.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static java.lang.System.exit;


public class AddArchivController  implements Initializable {
    private int anee=2019;
    @FXML
    private AnchorPane addArchiv;



    @FXML
    private JFXListView<String> yearSelect;

    public void initialize(URL url, ResourceBundle rb) {

        for (int i = 0; i < 300; i++) {
            yearSelect.getItems().add(anee + "-" + (++anee));
        }

    }

    @FXML
    void Cancel() {
        ((Stage) addArchiv.getScene().getWindow()).close();
    }

    @FXML
    void btnClose() {
        ((Stage) addArchiv.getScene().getWindow()).close();
    }

    @FXML
    public void NewArchiv(ActionEvent actionEvent) throws IOException {
        File dir = new File ("c:\\Archive creche darelhadith");
        dir.mkdir();
        String nom= yearSelect.getSelectionModel().getSelectedItem();
        System.out.println(nom);
        File file = new File("c:\\Archive creche darelhadith\\"+nom+".txt");
        FileWriter writer = new FileWriter(file);
        if (file.exists()) {
            System.out.println("File exists");
        }
        else {
            System.out.println("File does not exist");
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("file successfully created.");
            }
        }
        ArrayList<Employe> employeeDB = (ArrayList<Employe>) new EmployeDB().getEmployee();
        ArrayList<Eleve> elevDB = (ArrayList<Eleve>) new EleveDB().getEleve();
        if(elevDB==null){
            System.out.println("Connection Failed !");
        }
        else {
            writer.write( "Eleve\r\n");
            for(Eleve elv :elevDB){
                writer.write(elv.getId() + "\r\n");
                writer.write(elv.getNom() + "\r\n");
                writer.write(elv.getPrenom() + "\r\n");
                writer.write(elv.getDateNaissance() + "\r\n");
                writer.write(elv.getLieuNaissance() + "\r\n");
                writer.write(elv.getClasse()+ "\r\n");
                writer.write(elv.getAnneeScolaire()+ "\r\n");
                writer.write(elv.getRegime()+ "\r\n");
                writer.write(elv.getAdresse() + "\r\n");
                writer.write(elv.getPhone() + "\r\n");
                writer.write(elv.getPrenomPere() + "\r\n");
                writer.write(elv.getPrenomMere() + "\r\n");
                writer.write(elv.getNomMere() + "\r\n");
                writer.write(elv.getTravailPere()+ "\r\n");
                writer.write(elv.getTravailMere() + "\r\n");
                writer.write(elv.getMaladie() + "\r\n");
                writer.write(elv.getRemarque() + "\r\n");

                writer.write("-\r\n");
            }
            writer.write("lafin***\r\n");
        }



        if (employeeDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Employe text : employeeDB) {
                writer.write(text.getId() + "\r\n");
                writer.write(text.getNom() + "\r\n");
                writer.write(text.getPrenom() + "\r\n");
                writer.write(text.getDateNaissance() + "\r\n");
                writer.write(text.getLieuNaissance() + "\r\n");
                writer.write(text.getAdresse() + "\r\n");
                writer.write(text.getExperience() + "\r\n");
                writer.write(text.getNumTelephone() + "\r\n");
                writer.write(text.getSocialSecurityNumber() + "\r\n");
                writer.write(text.getDiplome() + "\r\n");
                writer.write(text.getItar() + "\r\n");
                writer.write(text.getDate_debut() + "\r\n");
                writer.write(text.getFonction() + "\r\n");
                writer.write(text.getRenouvlement_de_contrat() + "\r\n");
                writer.write(text.getRegimeScolaire()+ "\r\n");
                writer.write(text.getStatuSocial() + "\r\n");
                writer.write(text.getCelibacyTitle() + "\r\n");
                writer.write(text.getMaleChild() + "\r\n");
                writer.write(text.getFemaleChild() + "\r\n");
                writer.write("-\r\n");
            }
            writer.close();


        }
        ((Stage) addArchiv.getScene().getWindow()).close();
    }




}