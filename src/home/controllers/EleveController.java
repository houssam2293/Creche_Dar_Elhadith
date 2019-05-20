package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.EleveDB;
import home.java.Eleve;
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
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static home.controllers.EditEleveFormController.eleveSelected;

public class EleveController implements Initializable {

    @FXML
    public Label titleLabel;
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "رقم التسجيل", "الإسم", "اللقب", "القسم", "تاريخ الملاد",
                    "مكان الملاد",  "الهاتف", "ملاحظات"
            );

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXTreeTableView<TableEleve> treeTableView;
    @FXML
    private Label errorLabel;

    @FXML
    private JFXComboBox<String> combo;

    @FXML // Cols of table
    public JFXTreeTableColumn<TableEleve, String> idCol, firstnameCol, lastNameCol, classRoomCol,
            dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, remarqueCol;

    static JFXDialog addUserDialog, editUserDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //combo.setItems(options);
        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف", "ملاحظات");
        initializeTable();
    }


    @FXML
    public void addEleve(ActionEvent actionEvent) {
        errorLabel.setText("");
        AnchorPane addElevePane = null;
        try {
            addElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/addEleveForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addElevePane);
        addUserDialog.show();
    }

    @FXML
    public void editEleve(ActionEvent actionEvent) {
        errorLabel.setText("");
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

        eleveSelected = new Eleve();
        eleveSelected.setId(Integer.parseInt(id));
        eleveSelected.setPrenom(firstnameCol.getCellData(index));
        eleveSelected.setNom(lastNameCol.getCellData(index));
        eleveSelected.setClasse(classRoomCol.getCellData(index));
        eleveSelected.setDateNaissance(java.sql.Date.valueOf(dateOfBirthCol.getCellData(index)));
        eleveSelected.setLieuNaissance(placeOfBirthCol.getCellData(index));
        eleveSelected.setAdresse(addressCol.getCellData(index));
        eleveSelected.setPhone(phoneCol.getCellData(index));
        eleveSelected.setRemarque(remarqueCol.getCellData(index));


        AnchorPane editElevePane = null;
        try {
            editElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/editEleveForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        editUserDialog = getSpecialDialog(editElevePane);
        editUserDialog.show();
    }

    @FXML
    public void removeEleve(ActionEvent actionEvent) {
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


    id = Integer.valueOf(idCol.getCellData(treeTableView.getSelectionModel().getSelectedIndex()));
    JFXDialogLayout content = new JFXDialogLayout();
    Text headerText = new Text("تأكيد العملية");
    Text contentText = new Text("هل أنت متأكد من مسح البيانات؟");
    headerText.setStyle("-fx-font-size: 19px");
    contentText.setStyle("-fx-font-size: 18px");

    content.setHeading(headerText);
    content.setBody(contentText);

    JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

    JFXButton btnOk = new JFXButton("نعم");
    btnOk.setOnAction(e -> {
        int status = new EleveDB().removeEleve(id);
        System.out.println("status : " + status);
        if (status == -1) {
            errorLabel.setText("Connection Failed !");
        } else {
            Notifications notification = Notifications.create()
                    .title("تمت العملية بنجاح                               ")
                    .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();
            updateTable();
        }
        dialog.close();
    });

    JFXButton btnNo = new JFXButton("لا");
    btnOk.setPrefSize(120, 40);
    btnNo.setPrefSize(120, 40);
    btnOk.setStyle("-fx-font-size: 18px");
    btnNo.setStyle("-fx-font-size: 18px");

    content.setActions(btnOk, btnNo);

    dialog.getStylesheets().add("/home/resources/css/main.css");
    btnNo.setOnAction(e -> dialog.close());
    dialog.show();

}


    @FXML
    public void printEleve (ActionEvent actionEvent){

    }


    public void updateNotes (MouseEvent rightClick){



    }

    @FXML
    private void updateTable() {
        errorLabel.setText("");
        ObservableList<TableEleve> eleves = FXCollections.observableArrayList();
        combo.setValue(null);

        List<Eleve> studentDB = new EleveDB().getEleve();
        if (studentDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Eleve eleve : studentDB) {
                eleves.add(new TableEleve(eleve.getId(), eleve.getNom().toUpperCase(), eleve.getPrenom().toUpperCase(), eleve.getClasse(), eleve.getDateNaissance(),
                        eleve.getLieuNaissance(), eleve.getAdresse(), eleve.getPhone(), eleve.getRemarque()));
            }
        }

        final TreeItem<TableEleve> treeItem = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            treeTableView.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }


    static class TableEleve extends RecursiveTreeObject<TableEleve> {
        StringProperty id, firstname, lastname, classroom, birthday, birthplace;
        StringProperty addresse, phone, remarque;


        TableEleve(int id, String firstname, String lastname, String classroom, Date birthday, String birthplace, String addresse, String phone,
                   String remarque) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.lastname = new SimpleStringProperty(String.valueOf(lastname));
            this.classroom = new SimpleStringProperty(String.valueOf(classroom));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.birthplace = new SimpleStringProperty(String.valueOf(birthplace));
            this.addresse = new SimpleStringProperty(String.valueOf(addresse));
            this.phone = new SimpleStringProperty(String.valueOf(phone));
            this.remarque = new SimpleStringProperty(String.valueOf(remarque));


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

        classRoomCol = new JFXTreeTableColumn<>("القسم");
        classRoomCol.setPrefWidth(100);
        classRoomCol.setCellValueFactory(param -> param.getValue().getValue().classroom);



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

        remarqueCol = new JFXTreeTableColumn<>("ملاحظات");
        remarqueCol.setPrefWidth(150);
        remarqueCol.setCellValueFactory(param -> param.getValue().getValue().remarque);

        updateTable();

        searchField.textProperty().addListener(e -> filterSearchTable());
        combo.setOnAction(e -> filterSearchTable());

        treeTableView.getColumns().addAll(idCol, firstnameCol, lastNameCol, classRoomCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, remarqueCol);
        treeTableView.setShowRoot(false);
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }



    private void filterSearchTable() {
        treeTableView.setPredicate(eleve -> {
            switch (combo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 1:
                    return eleve.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 2:
                    return eleve.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 3:
                    return eleve.getValue().classroom.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 4:
                    return eleve.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 5:
                    return eleve.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 6:
                    return eleve.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 7:
                    return eleve.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 8:
                    return eleve.getValue().remarque.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                default:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().classroom.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleve.getValue().remarque.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                            }
        });
    }



}
