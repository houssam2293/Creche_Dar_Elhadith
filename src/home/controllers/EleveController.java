package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EleveController implements Initializable {

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                    "مكان الملاد", "الهاتف"
            );

    @FXML
    private StackPane elev;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;


    @FXML
    private JFXTextField searchField;
    @FXML
    public JFXTreeTableView<TableEleve> treeTableView;

    @FXML
    private JFXComboBox<String> combo;

    @FXML // Cols of table
    public JFXTreeTableColumn<TableEleve, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol;

    public static JFXDialog addUserDialog, editUserDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //combo.setItems(options);
        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "العنوان الشخصي", "الهاتف");
        initializeTable();
    }


    public void updateTable(MouseEvent mouseEvent) {

    }

    public void addElev(ActionEvent actionEvent) {
        //errorLabel.setText("");
        AnchorPane addElevPane = null;
        try {
            addElevPane = FXMLLoader.load(getClass().getResource("/home/fxml/addEleveForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addElevPane);
        addUserDialog.show();
    }

    public void editElev(ActionEvent actionEvent) {
        /*errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        */


        AnchorPane addElevPane = null;
        try {
            addElevPane = FXMLLoader.load(getClass().getResource("/home/fxml/addEleveForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addElevPane);
        addUserDialog.show();
    }

    public void removeElev(ActionEvent actionEvent) {
        errorLabel.setText("");
        int id;
        if (idCol.getCellData(treeTableView.getSelectionModel().getSelectedIndex()) == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد مسحه                                 ")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle()
                    .showWarning();
            return;
        }
    }

    class TableEleve extends RecursiveTreeObject<TableEleve> {
        StringProperty id, firstname, lastname, birthday, birthplace;
        StringProperty addresse, phone;


        public TableEleve(int id, String firstname, String lastname, Date birthday, String birthplace, String addresse,
                          String phone) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.lastname = new SimpleStringProperty(String.valueOf(lastname));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.birthplace = new SimpleStringProperty(String.valueOf(birthplace));
            this.addresse = new SimpleStringProperty(String.valueOf(addresse));
            this.phone = new SimpleStringProperty(String.valueOf(phone));


        }
    }

    private void initializeTable() {
        idCol = new JFXTreeTableColumn<>("رقم التسجيل");
        idCol.setPrefWidth(120);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id);

        firstnameCol = new JFXTreeTableColumn<>("الإسم");
        firstnameCol.setPrefWidth(150);
        firstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCol = new JFXTreeTableColumn<>("اللقب");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory(param -> param.getValue().getValue().lastname);

        dateOfBirthCol = new JFXTreeTableColumn<>("تاريخ الملاد");
        dateOfBirthCol.setPrefWidth(100);
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

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(elev, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> {
            updateTable();
        });
        return dialog;
    }

    private void updateTable() {
    }

}
