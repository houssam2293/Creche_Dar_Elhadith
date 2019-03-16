package home.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private HBox parent;

    @FXML
    private VBox sidebar;

    @FXML
    private HBox boxHome;

    @FXML
    private FontAwesomeIconView iconHome;

    @FXML
    private HBox boxCon;

    @FXML
    private Icons525View iconCon;

    @FXML
    private HBox boxTrac;

    @FXML
    private OctIconView iconTrac;

    @FXML
    private HBox boxGrades;

    @FXML
    private OctIconView iconGrades;

    @FXML
    private HBox boxQuestion;

    @FXML
    private OctIconView iconQuestion;

    @FXML
    private HBox boxAccount;

    @FXML
    private FontAwesomeIconView iconAccount;

    @FXML
    private HBox boxChat;

    @FXML
    private FontAwesomeIconView iconChat;

    @FXML
    private HBox boxSettings;

    @FXML
    private Icons525View iconSettings;

    @FXML
    private HBox boxGuide;

    @FXML
    private MaterialDesignIconView iconGuide;

    @FXML
    private HBox boxAbout;

    @FXML
    private MaterialDesignIconView iconAbout;

    @FXML
    private StackPane rightPane;

    @FXML
    private AnchorPane holderPane;

    @FXML
    private VBox homePane;

    @FXML
    private Pane paneSlider;

    @FXML
    private ImageView imgSlider;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox conBox;

    @FXML
    private VBox tracBox;

    @FXML
    private VBox manageQuestionBox;

    @FXML
    private VBox manageAccountBox;

    private final byte NUMBER_IMAGE_SLIDER = 4;
    private int counter = 1;

    /*---------------------------------------------------------*/

    @FXML
    void aboutClicked(MouseEvent event) {

    }

    @FXML
    void accountClicked(MouseEvent event) {

    }

    @FXML
    void chatClicked(MouseEvent event) {

    }

    @FXML
    void connectionClicked(MouseEvent event) {

    }

    @FXML
    void expandSidebar(MouseEvent event) {

    }

    @FXML
    void gradesClicked(MouseEvent event) {

    }

    @FXML
    void guideClicked(MouseEvent event) {

    }

    @FXML
    void homeClicked(MouseEvent event) {
        styleBox(0);
        homePane.setVisible(true);
        sidebar.setPrefWidth(50);

    }

    @FXML
    void logoutClicked(MouseEvent event) {

    }

    @FXML
    void questionClicked(MouseEvent event) {

    }

    @FXML
    void settingsClicked(MouseEvent event) {

    }

    @FXML
    void traceabilityClicked(MouseEvent event) {

    }

    public void expandSidebar(javafx.scene.input.MouseEvent mouseEvent) {
        sidebar.setPrefWidth((sidebar.getWidth() == 50) ? 200 : 50);
    }

    public void homeClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void connectionClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void traceabilityClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void gradesClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void questionClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void accountClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void chatClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void settingsClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void logoutClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void guideClicked(javafx.scene.input.MouseEvent mouseEvent) {
    }

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
