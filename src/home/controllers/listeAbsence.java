package home.controllers;

import com.jfoenix.controls.*;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class listeAbsence implements Initializable {


    private static JFXDialog addUserDialog;
    @FXML
    private VBox root;


    @FXML
    void btnClose() {
        Pointag.addUserDialog.close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {




        root.setOnKeyPressed(e->{
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }

}
