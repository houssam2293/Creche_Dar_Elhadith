package home.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class PreLoadLogin implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane holderPane;

    @FXML
    private ImageView imageView;

    @FXML
    private ProgressBar progresse;

    private void slideLogin() {
        Parent lock = null;
        Scene scene = imageView.getScene();
        try {
            lock=FXMLLoader.load(getClass().getResource("/home/resources/fxml/login.fxml"));
            lock.translateYProperty().set(scene.getHeight());
            root.getChildren().add(lock);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(lock.translateYProperty(),0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            timeline.setOnFinished(e->{
                root.getChildren().remove(holderPane);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Task taskCreator() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i=0; i < 5; i++) {
                    Thread.sleep(1000);
                    updateProgress(i+1,5);
                }
                return true;
            }
        };
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,e->{
            double i = 0;
        Task task = taskCreator();
        progresse.progressProperty().bind(task.progressProperty());

        }),
                new KeyFrame(Duration.seconds(2))
        );
        timeline.setCycleCount(2);
        timeline.play();
        timeline.setOnFinished(event -> slideLogin());
    }
}
