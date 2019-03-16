package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        InputStream input = null;
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current path : " + s);

        try {

            String filename = s + "/dbCredentials.properties";
            File file = new File(filename);
            if (file.exists()) {
                input = new FileInputStream(filename);
            }

            if (input == null) {
                System.out.println("file 'dbCredentials.properties' not found!(main)");
                Parent root = FXMLLoader.load(getClass().getResource("fxml/firstLoad.fxml"));
                primaryStage.setTitle("Creche Dar EL Hadith/Configuration");
                primaryStage.getIcons().add(new Image("/home/icons/icon.ico"));
                primaryStage.setResizable(false);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } else {
                InputStream input1 = null;


                try {

                    String filename1 = s + "/loginCredentials.properties";
                    File file1 = new File(filename1);
                    if (file1.exists()) {

                        input1 = new FileInputStream(filename1);

                    }

                    if (input1 == null) {
                        System.out.println("file 'loginCredentials.properties' not found!(main)");
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("fxml/loginEntry.fxml"));
                        stage.setTitle("Premier Lancement");
                        stage.getIcons().add(new Image("/home/icons/icon.ico"));
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.show();

                    } else {

                        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
                        primaryStage.setTitle("Creche Dar EL Hadith");
                        primaryStage.getIcons().add(new Image("/home/icons/icon.ico"));
                        primaryStage.setResizable(false);
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();


                    }


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
