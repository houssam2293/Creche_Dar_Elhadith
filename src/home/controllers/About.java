package home.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class About {

    @FXML
    private AnchorPane root;

    @FXML
    void btnClose() {
        MainController.aboutDialog.close();
    }

}
