package home.controllers;

import com.jfoenix.controls.JFXDialog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private VBox sidebar; // Menu Left of my System

    /* Start Home Part */
    @FXML
    private VBox homePane;
    @FXML
    private VBox conBox, tracBox, manageQuestionBox, manageAccountBox;

    /* End Home Part */
    private VBox guidePane;
    private AnchorPane connectionPane, gradesPane, addQuestionPane, settingsPane;
    private StackPane tracPane, manageQuestionPane, manageAccountPane, chatPane;
    @FXML // this pane using for the Dialog of about
    private StackPane rightPane;

    /* Start Icon Option */
    @FXML
    private HBox boxHome, boxCon, boxGrades, boxTrac, boxQuestion, boxAccount, boxChat, boxSettings, boxGuide;
    @FXML
    private FontAwesomeIconView iconHome, iconChat, iconAccount;
    @FXML
    private MaterialDesignIconView iconGuide, iconAbout;
    @FXML
    private Icons525View iconCon, iconSettings;
    @FXML
    private OctIconView iconTrac, iconGrades, iconQuestion;
    /* End Icon Option */

    public static JFXDialog aboutDialog; // this for show the about Dialog

    @FXML
    private ImageView imgSlider;
    @FXML
    private Label dateLabel;

    private final byte NUMBER_IMAGE_SLIDER = 4;
    private int counter = 1;

    /*---------------------------------------------------------*/


    @FXML
    public void expandSidebar(javafx.scene.input.MouseEvent mouseEvent) {
        sidebar.setPrefWidth((sidebar.getWidth() == 50) ? 200 : 50);
    }

    @FXML
    public void homeClicked(javafx.scene.input.MouseEvent mouseEvent) {
        styleBox(0);
        homePane.setVisible(true);
        sidebar.setPrefWidth(50);
    }

    @FXML
    public void connectionClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void traceabilityClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void gradesClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void questionClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void accountClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void chatClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void settingsClicked(javafx.scene.input.MouseEvent mouseEvent) {
        styleBox(7);
        setNode(settingsPane);
    }

    @FXML
    public void logoutClicked(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage;
        Parent root = null;
        //get reference - stage
        stage = (Stage) holderPane.getScene().getWindow();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/home/fxml/login.fxml"));
        } catch (IOException ignored) {}

        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    @FXML
    public void guideClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void aboutClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    private void sliderAutoChangePictures() {
        // Make auto change the slider in duration

        Timeline sliderTimer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setNode(imgSlider);
            ft.setDuration(new Duration(5000));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(0);
            ft.setAutoReverse(true);
            ft.play();
            imgSlider.setImage(new Image("/home/images/slider/" + counter + ".png"));
            if (++counter > NUMBER_IMAGE_SLIDER) {
                counter = 1;
            }
        }),
                new KeyFrame(Duration.seconds(4))
        );
        sliderTimer.setCycleCount(Animation.INDEFINITE);
        sliderTimer.play();

        // initialize Clock Showing in home
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy      HH:mm:ss");
            Date date = new Date();
            dateLabel.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        styleBox(0); // for changing the color of Home Icon

        // Initialize the image (to fill parent)
        imgSlider.fitWidthProperty().bind(holderPane.widthProperty());
        imgSlider.fitHeightProperty().bind(holderPane.heightProperty());

        // Make auto change the slider in duration
        sliderAutoChangePictures();

        try {
            settingsPane = FXMLLoader.load(getClass().getResource("/home/fxml/settings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setNode(Node node) {
        homePane.setVisible(false);
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
        sidebar.setPrefWidth(50);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void styleBox(int index) {
        // This function change the style+color of the menu (Menu Item Selected)
        iconHome.setFill(Paint.valueOf("#4a4949"));
        iconCon.setFill(Paint.valueOf("#4a4949"));
        iconGrades.setFill(Paint.valueOf("#4a4949"));
        iconTrac.setFill(Paint.valueOf("#4a4949"));
        iconQuestion.setFill(Paint.valueOf("#4a4949"));
        iconAccount.setFill(Paint.valueOf("#4a4949"));
        iconChat.setFill(Paint.valueOf("#4a4949"));
        iconSettings.setFill(Paint.valueOf("#4a4949"));
        iconGuide.setFill(Paint.valueOf("#4a4949"));

        boxHome.setStyle("-fx-border: 0");
        boxCon.setStyle("-fx-border: 0");
        boxGrades.setStyle("-fx-border: 0");
        boxTrac.setStyle("-fx-border: 0");
        boxQuestion.setStyle("-fx-border: 0");
        boxAccount.setStyle("-fx-border: 0");
        boxChat.setStyle("-fx-border: 0");
        boxSettings.setStyle("-fx-border: 0");
        boxGuide.setStyle("-fx-border: 0");

        switch (index) {
            case 0:
                boxHome.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconHome.setFill(Paint.valueOf("#2196f3"));
                break;
            case 1:
                boxCon.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconCon.setFill(Paint.valueOf("#2196f3"));
                break;
            case 2:
                boxGrades.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconGrades.setFill(Paint.valueOf("#2196f3"));
                break;
            case 3:
                boxTrac.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconTrac.setFill(Paint.valueOf("#2196f3"));
                break;
            case 4:
                boxQuestion.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconQuestion.setFill(Paint.valueOf("#2196f3"));
                break;
            case 5:
                boxAccount.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconAccount.setFill(Paint.valueOf("#2196f3"));
                break;
            case 6:
                boxChat.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconChat.setFill(Paint.valueOf("#2196f3"));
                break;
            case 7:
                boxSettings.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconSettings.setFill(Paint.valueOf("#2196f3"));
                break;
            case 8:
                boxGuide.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconGuide.setFill(Paint.valueOf("#2196f3"));
                break;
        }
    }
}
