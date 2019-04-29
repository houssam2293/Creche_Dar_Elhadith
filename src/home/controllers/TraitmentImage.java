package home.controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TraitmentImage implements Initializable {
    private List<File> images = new ArrayList<>();
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private StackPane root;

    @FXML
    private VBox holderPane;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXComboBox<String> combo;

    @FXML
    private JFXTreeTableView<EleveController.TableEleve> treeTableView;

    @FXML // Cols of table
    public JFXTreeTableColumn<EleveController.TableEleve, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol;

    @FXML
    static JFXDialog editImage;

    @FXML
    private ImageView imageHolder;
    private int imgTotal, imgPosition = 0;
    public static Image currentImage;

    @FXML
    void addImage() {
        List<File> selectedImages = fileChooser.showOpenMultipleDialog(holderPane.getScene().getWindow());
        int selectedFilePos = selectedImages.size() - 1;
        while (selectedFilePos >= 0) {
            File file = selectedImages.get(selectedFilePos);
            if (file.isFile() && (file.getName().contains(".jpg") || file.getName().contains(".jpeg") ||
                    file.getName().contains(".png") || file.getName().contains(".bmp") ||
                    file.getName().contains(".gif"))) {
                images.add(file);
                imgTotal = images.size();
                if (imgTotal > 1) {
                    imgPosition++;
                }
                Image imgThumb = new Image(file.toURI().toString());
                imageHolder.setImage(imgThumb);
            }
            selectedFilePos--;

        }
        //selectedImages.clear();
    }

    @FXML
    void deleteImage() throws MalformedURLException {
        if (imgTotal > 1) {
            images.remove(imgPosition);
            if (imgPosition == imgTotal - 1) {
                imgPosition--;
            }
            imgTotal = images.size();

            String imgThumb = images.get(imgPosition).toURI().toURL().toString();
            Image imageLoad = new Image(imgThumb);
            imageHolder.setImage(imageLoad);
        } else {
            if (!images.isEmpty())
                images.remove(imgPosition);
            imgPosition = 0;
            imgTotal = images.size();
            imageHolder.setImage(null);
        }

    }

    @FXML
    void moveEnd() throws MalformedURLException {
        imgPosition = images.size() - 1;
        String imgThumb = images.get(imgPosition).toURI().toURL().toString();
        Image imageLoad = new Image(imgThumb);
        imageHolder.setImage(imageLoad);

    }

    @FXML
    void moveFirst() throws MalformedURLException {
        imgPosition = 0;
        String imgThumb = images.get(imgPosition).toURI().toURL().toString();
        Image imageLoad = new Image(imgThumb);
        imageHolder.setImage(imageLoad);

    }

    @FXML
    void moveNext() throws MalformedURLException {
        if (imgPosition < imgTotal - 1) {
            imgPosition++;
            String imgThumb = images.get(imgPosition).toURI().toURL().toString();
            Image imageLoad = new Image(imgThumb);
            imageHolder.setImage(imageLoad);
        }

    }

    @FXML
    void movePrevious() throws MalformedURLException {
        if (imgPosition > 0) {
            imgPosition--;
            String imgThumb = images.get(imgPosition).toURI().toURL().toString();
            Image imageLoad = new Image(imgThumb);
            imageHolder.setImage(imageLoad);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "العنوان الشخصي", "الهاتف");
        initializeTable();
        imageHolder.setOnMouseClicked(event -> {
            AnchorPane addUserPane = null;
            try {
                addUserPane = FXMLLoader.load(getClass().getResource("/home/fxml/editImage.fxml"));
            } catch (IOException ignored) {
            }
            currentImage = imageHolder.getImage();
            editImage = getSpecialDialog(addUserPane);
            editImage.show();


        });

    }


    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }

    private void initializeTable() {
        idCol = new JFXTreeTableColumn<>("رقم التسجيل");
        idCol.setPrefWidth(80);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id);

        firstnameCol = new JFXTreeTableColumn<>("الإسم");
        firstnameCol.setPrefWidth(150);
        firstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCol = new JFXTreeTableColumn<>("اللقب");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory(param -> param.getValue().getValue().lastname);

        dateOfBirthCol = new JFXTreeTableColumn<>("تاريخ الملاد");
        dateOfBirthCol.setPrefWidth(120);
        dateOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthday);

        placeOfBirthCol = new JFXTreeTableColumn<>("مكان الملاد");
        placeOfBirthCol.setPrefWidth(100);
        placeOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthplace);


        addressCol = new JFXTreeTableColumn<>("العنوان");
        addressCol.setPrefWidth(120);
        addressCol.setCellValueFactory(param -> param.getValue().getValue().addresse);

        phoneCol = new JFXTreeTableColumn<>("الهاتف");
        phoneCol.setPrefWidth(75);
        phoneCol.setCellValueFactory(param -> param.getValue().getValue().phone);

        //updateTable();

        treeTableView.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol);
        treeTableView.setShowRoot(false);
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void updateTable() {
    }
}