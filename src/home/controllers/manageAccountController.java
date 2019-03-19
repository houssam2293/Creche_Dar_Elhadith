package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;


public class manageAccountController implements Initializable {

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "الرقم", "رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                    "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                    "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد"
            );

    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;
    @FXML
    public JFXTreeTableView treeTableView;

    @FXML
    private JFXComboBox<String> combo;

    @FXML // Cols of table
    public JFXTreeTableColumn<TableEmploye, String> idCol, serialId, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol, addressCol, phoneCol,socialSecurNumbCol,
            diplomeCol,dateFirstEmploCol,experienceCol,contractRenCol;

    public static JFXDialog addUserDialog, editUserDialog;


    @FXML
    void addUser(ActionEvent event) {

    }

    @FXML
    void editUser(ActionEvent event) {

    }

    @FXML
    void removeUser(ActionEvent event) {

    }

    @FXML
    void updateTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combo.setItems(options);

        initializeTable();
    }

    private void initializeTable() {
    }

    class TableEmploye extends RecursiveTreeObject<TableEmploye> {

    }
}
