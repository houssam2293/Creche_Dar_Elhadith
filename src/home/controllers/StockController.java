package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.StockDB;
import home.java.EntreStock;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    @FXML
    private StackPane root;
    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXComboBox<String> combo;

    @FXML
    private TableView<EntreStock> tableView;

    @FXML
    private TableColumn<EntreStock,String> idColumn;

    @FXML
    private TableColumn<EntreStock,String> nomColumn;

    @FXML
    private TableColumn<EntreStock,String> dateFabColumn;

    @FXML
    private TableColumn<EntreStock,String> dateExpColumn;

    @FXML
    private TableColumn<EntreStock,String> quntiteColumn;

    @FXML
    private TableColumn<EntreStock,String> prixColumn;
    static JFXDialog addUserDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory("nom"));
        prixColumn.setCellValueFactory(new PropertyValueFactory("prix"));
        quntiteColumn.setCellValueFactory(new PropertyValueFactory("quantite"));
        dateFabColumn.setCellValueFactory(new PropertyValueFactory("dateFab"));
        dateExpColumn.setCellValueFactory(new PropertyValueFactory("dateExp"));

        combo.getItems().addAll("رقم السلعة","نوع السلعة","تاريخ انتهاء الصلاحية","تاريخ الانتاج");
        initializeTable();
    }

    private void initializeTable() {


    }

    @FXML
    void addstock() {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/fxml/entreStockForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    @FXML
    void editstock() {


    }

    @FXML
    void removestock() {

    }

    @FXML
    void updateTable() {
        tableView.setItems(FXCollections.observableList(StockDB.getStockDB().getListeStock()));
    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }


}
