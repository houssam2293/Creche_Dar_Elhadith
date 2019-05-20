package home.controllers;

import de.jensd.fx.glyphs.emojione.EmojiOneView;
import home.dbDir.EleveDB;
import home.java.Eleve;
import home.java.Salle;
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
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TraitmentImage implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private ListView<String> classeListview;

    @FXML
    private EmojiOneView print;

    @FXML
    private AnchorPane holderPane;

    private AnchorPane imageModel;



    @FXML
    void btnAnnuler() {
        holderPane.getChildren().clear();
    }

    @FXML
    void btnShow() {
        print.setDisable(false);
        print.setFill(Paint.valueOf("#2196f3"));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/resources/fxml/imageModel.fxml"));
            imageModel = loader.load();
            ImageModel imagemodel = loader.getController();
            imagemodel.setStudentClasse(classeListview.getSelectionModel().getSelectedItem());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> classes =
                FXCollections.observableArrayList(
                        "C01", "C02", "C03", "C04", "C05", "C06", "C07", "C08", "C09", "C10", "C11", "C12"
                );
        classeListview.setItems(classes);

    }
}
