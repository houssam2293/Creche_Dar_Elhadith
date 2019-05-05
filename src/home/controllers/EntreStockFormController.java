package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.StockDB;
import home.java.EntreStock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.Date;

public class EntreStockFormController {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField idField;

    @FXML
    private JFXTextField nomField;

    @FXML
    private JFXDatePicker dateFabField;

    @FXML
    private JFXDatePicker dateExpField;

    @FXML
    private JFXTextField quantiteField;

    @FXML
    private JFXTextField prixField;

    @FXML
    void add(ActionEvent event) {
        EntreStock entreStock = new EntreStock();
        entreStock.setNom(nomField.getText());
        entreStock.setId(idField.getText());
        entreStock.setDateExp(Date.valueOf(dateExpField.getValue()));
        entreStock.setDateFab(Date.valueOf(dateFabField.getValue()));
        entreStock.setQuantite(Integer.parseInt(quantiteField.getText()));
        entreStock.setPrix(Double.parseDouble(prixField.getText()));

        // BASE DE DONNE

        StockDB.getStockDB().getListeStock().add(entreStock);

        StockController.addUserDialog.close();

    }

    @FXML
    void close(MouseEvent event) {
        StockController.addUserDialog.close();
    }

    @FXML
    void delete(ActionEvent event) {
        idField.setText(null);
        nomField.setText(null);
        dateExpField.setValue(null);
        dateFabField.setValue(null);
        quantiteField.setText(null);
        prixField.setText(null);

    }

    @FXML
    void printStock(ActionEvent event) {


    }

}
