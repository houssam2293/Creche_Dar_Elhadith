package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.StockDB;
import home.java.EntreStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class EditStockFormController implements Initializable {

    private ObservableList<String> typeProduit =
            FXCollections.observableArrayList(
                    "طعام", "كتب و كراريس", "أخرى"
            );

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
    private JFXTextField fournisseurField;

    @FXML
    private JFXComboBox<String> comboProd;



    static EntreStock stockSelected;


    @FXML
    void close() {
        StockController.editUserDialog.close();
    }

    @FXML
    void edit() {

        EntreStock stock = new EntreStock();
        stock.setId(stockSelected.getId());
        switch (comboProd.getValue()) {
            case "طعام":
                stock.setTypeProduit(1);
                break;
            case "كتب و كراريس":
                stock.setTypeProduit(2);
                break;
            case "أخرى":
                stock.setTypeProduit(3);
                break;
        }
        stock.setNom(nomField.getText().trim().toLowerCase());
        stock.setDateFab(Date.valueOf(dateFabField.getValue()));
        stock.setDateExp(Date.valueOf(dateExpField.getValue()));
        stock.setQuantite(Integer.parseInt(quantiteField.getText().trim().toLowerCase()));
        stock.setPrix(Double.parseDouble(prixField.getText().trim().toLowerCase()));
        stock.setFournisseur(fournisseurField.getText().trim().toLowerCase());
        stock.setPrixTotale(Integer.parseInt(quantiteField.getText().trim().toLowerCase())*Double.parseDouble(prixField.getText().trim().toLowerCase()));

        int status = new StockDB().editStock(stock);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Stock does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Stock");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                StockController.editUserDialog.close();
        }

    }

    @FXML
    void reset() {
       /* EntreStock stock = new EntreStock();
        stockSelected.setId(stockSelected.getId());
        stock.setNom(nomField.getText().trim().toLowerCase());
        stock.setDateFab(Date.valueOf(dateFabField.getValue()));
        stock.setDateExp(Date.valueOf(dateExpField.getValue()));
        stock.setQuantite(Integer.parseInt(quantiteField.getText().trim().toLowerCase()));
        stock.setPrix(Double.parseDouble(prixField.getText().trim().toLowerCase()));
        stock.setNom(fournisseurField.getText().trim().toLowerCase());
        //stock.setPrixTotale(Double.parseDouble(prixField.getText().trim().toLowerCase())*Integer.parseInt(quantiteField.getText().trim().toLowerCase()));
   */
       close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                edit();
            }
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                close();
            }
        });
        comboProd.getItems().addAll("طعام", "كتب و كراريس", "أخرى");




        idField.setText(String.valueOf(stockSelected.getId()));
        if (stockSelected.getTypeProduit() == 1) {
            System.out.println("Type prod : " + stockSelected.getTypeProduit());
            comboProd.getSelectionModel().select(0);
        } else if (stockSelected.getTypeProduit() == 2) {
            System.out.println("Type prod : " + stockSelected.getTypeProduit());
            comboProd.getSelectionModel().select(1);
        } else if (stockSelected.getTypeProduit() == 3) {
            System.out.println("Type prod : " + stockSelected.getTypeProduit());
            comboProd.getSelectionModel().select(2);
        }
        nomField.setText(stockSelected.getNom());
        dateFabField.setValue(java.time.LocalDate.parse(String.valueOf(stockSelected.getDateFab())));
        dateExpField.setValue(java.time.LocalDate.parse(String.valueOf(stockSelected.getDateExp())));
        quantiteField.setText(String.valueOf(stockSelected.getQuantite()));
        prixField.setText(String.valueOf(stockSelected.getPrix()));
        fournisseurField.setText(stockSelected.getNom());
        comboProd.setItems(typeProduit);

    }



}
