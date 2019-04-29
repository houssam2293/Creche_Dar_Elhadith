package home.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditImage implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private  ImageView imageHolder;


    @FXML
    void btnClose() {
        TraitmentImage.editImage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    imageHolder.setImage(uploadImage());

    }

    private Image uploadImage() {
        Image thumbImg;
        thumbImg = TraitmentImage.currentImage;
        return thumbImg;
    }
}