package home.controllers;

import de.jensd.fx.glyphs.emojione.EmojiOneView;
import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TraitmentImage implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private HBox controllBox;

    @FXML
    private ListView<String> classeListview;

    @FXML
    private EmojiOneView print;

    @FXML
    private AnchorPane holderPane;

    private AnchorPane imageModel;

    private EleveDB eleveDB;

    List<Eleve> eleves;
    private String selectedClasse;
    private ObservableList<String> classes =
            FXCollections.observableArrayList(
                    "C01", "C02", "C03", "C04", "C05", "C06", "C07", "C08", "C09", "C10", "C11", "C12"
            );

    @FXML
    void btnAnnuler() {
        holderPane.getChildren().clear();
        controllBox.setVisible(false);
        classeListview.setItems(classes);
    }

    @FXML
    void btnShow() {
        print.setDisable(false);
        print.setFill(Paint.valueOf("#2196f3"));
        controllBox.setVisible(true);
        selectedClasse = classeListview.getSelectionModel().getSelectedItem();
        ObservableList<String> classe = FXCollections.observableArrayList();
        eleves = eleveDB.getEleve();
        for (Eleve eleve : eleves) {
            assert false;
            classe.add(eleve.getPrenom() + " - " + eleve.getNom());
        }
        classeListview.setItems(classe);
        classeListview.setOnMouseClicked(event -> {
            String selected = classeListview.getSelectionModel().getSelectedItem();
            String[] selectedStudent = selected.split("-");
            loadScene(selectedStudent[0], selectedStudent[1]);
        });


    }

    private void loadScene(String nom, String prenom) {
        Eleve eleve = new Eleve();
        System.out.println("nom : " + nom);
        System.out.println("prenom : " + prenom);

        for (Eleve eleve1 : eleves) {
            System.out.println("elve dans la liste : "  + eleve1.getNom()+ " " + eleve1.getPrenom());
            if (nom.equals(eleve1.getNom())) {
                //TODO fix this next
                eleve = eleve1;
            }//eleve = eleve1;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/resources/fxml/imageModel.fxml"));
            imageModel = loader.load();
            ImageModel imagemodel = loader.getController();
            imagemodel.setStudentClasse(selectedClasse, eleve);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setNode(imageModel);
    }

    @FXML
    void printFile() {

    }


    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void addImage() {
        System.out.println("add Image clicked!");

    }

    @FXML
    void deleteImage() {
        System.out.println("delete Image clicked!");
    }

    @FXML
    void moveEnd() {
        System.out.println("move end clicked!");
    }

    @FXML
    void moveFirst() {
        System.out.println("move first clicked!");
    }

    @FXML
    void moveNext() {
        System.out.println("move next clicked!");
    }

    @FXML
    void movePrevious() {
        System.out.println("move previous clicked!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        classeListview.setItems(classes);
        eleveDB = new EleveDB();
    }
}
