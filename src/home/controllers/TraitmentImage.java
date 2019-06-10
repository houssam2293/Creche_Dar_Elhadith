package home.controllers;

import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import home.dbDir.ClasseDB;
import home.dbDir.EleveDB;
import home.java.ClasseCellFactory;
import home.java.ClasseModel;
import home.java.Eleve;
import home.java.EleveCellFactory;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TraitmentImage implements Initializable {

    @FXML
    private Label errorLabel, listViewsLabel;

    @FXML
    private HBox controllBox;

    @FXML
    private ListView<ClasseModel> classeListview;

    @FXML
    private ListView<Eleve> studentListview;

    @FXML
    private EmojiOneView print, first, previous, next, last;

    @FXML
    private Icons525View addImage, deleteImage;

    @FXML
    private AnchorPane holderPane;

    private AnchorPane imageModel;

    private Eleve selected;

    private EleveDB eleveDB;
    private ImageModel imagemodel;
    private Image selectedImage = null;

    private String selectedClasse;

    @FXML
    void btnAnnuler() {
        holderPane.getChildren().clear();
        controllBox.setVisible(false);
        studentListview.getItems().clear();
        studentListview.setVisible(false);
        classeListview.getItems().clear();
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        ArrayList<ClasseModel> classes = new ArrayList<>(clsDB);
        classeListview.getItems().addAll(classes);
        classeListview.setCellFactory(new ClasseCellFactory());
        classeListview.setVisible(true);
        listViewsLabel.setText("الأقسام");
        print.setDisable(true);
        print.setFill(Paint.valueOf("#939da4"));
    }

    @FXML
    void btnShow() {
        if (!classeListview.getSelectionModel().isEmpty()) {
            print.setDisable(false);
            print.setFill(Paint.valueOf("#2196f3"));
            controllBox.setVisible(true);
            listViewsLabel.setText("تلاميذ القسم");
            studentListview.getItems().clear();
            selectedClasse = classeListview.getSelectionModel().getSelectedItem().getClassNam();
            List<Eleve> eleves = eleveDB.getEleveDeClasse(selectedClasse);
            ArrayList<Eleve> students = new ArrayList<>(eleves);
            classeListview.setVisible(false);
            studentListview.getItems().addAll(students);
            studentListview.setCellFactory(new EleveCellFactory());
            studentListview.setVisible(true);
            studentListview.setOnMouseClicked(event -> {
                selected = studentListview.getSelectionModel().getSelectedItem();
                loadScene(selected);
            });

        }

    }

    private void loadScene(Eleve eleve) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/resources/fxml/imageModel.fxml"));
            imageModel = loader.load();
            imagemodel = loader.getController();
            imagemodel.setStudentClasse(selectedClasse, eleve);
            if (ImageModel.imageDeleted) {
                selectedImage = null;
            }
            imagemodel.setImageHolder(selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setNode(imageModel);
    }

    @FXML
    void printFile() {
        imagemodel.print();

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

    private static BufferedImage readImageFromFile(File file)
            throws IOException {
        return ImageIO.read(file);
    }

    @FXML
    void addImage() {
        FileChooser fileChooser = new FileChooser();


        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("image files (png,jpg,jpeg,bmp,gif)", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        //todo:fix next

        //Show save file dialog
        File file = fileChooser.showOpenDialog(errorLabel.getScene().getWindow());
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        System.out.println("Image extension is : " + extension);


        selectedImage = new Image(file.toURI().toString());
        imagemodel.setImageHolder(selectedImage);
    }

    @FXML
    void deleteImage() {
        imagemodel.deleteImage();
    }

    @FXML
    void moveEnd() {
        studentListview.getSelectionModel().selectLast();
        loadScene(studentListview.getSelectionModel().getSelectedItem());
    }

    @FXML
    void moveFirst() {
        studentListview.getSelectionModel().selectFirst();
        loadScene(studentListview.getSelectionModel().getSelectedItem());
    }

    @FXML
    void moveNext() {
        studentListview.getSelectionModel().selectNext();
        loadScene(studentListview.getSelectionModel().getSelectedItem());
    }

    @FXML
    void movePrevious() {
        studentListview.getSelectionModel().selectPrevious();
        loadScene(studentListview.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        ArrayList<ClasseModel> classes = new ArrayList<>(clsDB);
        classeListview.getItems().addAll(classes);
        classeListview.setCellFactory(new ClasseCellFactory());
        eleveDB = new EleveDB();
        Tooltip printTooltip, nextTooltip, previousTooltip, lastTooltip, firstTooltip, addImageTooltip, deleteImageTooltip;
        printTooltip = new Tooltip(" طباعة");
        nextTooltip = new Tooltip(" التالي");
        previousTooltip = new Tooltip(" السابق");
        lastTooltip = new Tooltip(" الأخير");
        firstTooltip = new Tooltip(" الأول");
        addImageTooltip = new Tooltip(" إضافة صورة");
        deleteImageTooltip = new Tooltip(" حذف صورة");

        Font font = new Font("Arial Bold", 12);
        Tooltip.install(print, printTooltip);
        Tooltip.install(next, nextTooltip);
        Tooltip.install(previous, previousTooltip);
        Tooltip.install(last, lastTooltip);
        Tooltip.install(first, firstTooltip);
        Tooltip.install(addImage, addImageTooltip);
        Tooltip.install(deleteImage, deleteImageTooltip);

        printTooltip.setFont(font);
        nextTooltip.setFont(font);
        previousTooltip.setFont(font);
        lastTooltip.setFont(font);
        firstTooltip.setFont(font);
        addImageTooltip.setFont(font);
        deleteImageTooltip.setFont(font);


    }
}
