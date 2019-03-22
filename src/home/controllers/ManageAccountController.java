package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ManageAccountController implements Initializable {

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
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/fxml/AddUserForm.fxml"));
        } catch (IOException ex) {

        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    @FXML
    void editUser(ActionEvent event) {

    }

    @FXML
    void removeUser(ActionEvent event) {

    }

    @FXML
    void updateTable() {

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

    public JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> {
            updateTable();
        });
        return dialog;
    }
}
