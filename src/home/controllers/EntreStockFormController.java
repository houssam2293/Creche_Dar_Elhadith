package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.StockDB;
import home.dbDir.fraisDB;
import home.java.EntreStock;
import home.java.Frais;
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

public class EntreStockFormController implements Initializable {

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

    @FXML
    void add() {
        EntreStock entreStock = new EntreStock();
        entreStock.setNom(nomField.getText());
        if (comboProd.getValue().equals("طعام"))
            entreStock.setTypeProduit(1);
        else if (comboProd.getValue().equals("كتب و كراريس"))
            entreStock.setTypeProduit(2);
        else if (comboProd.getValue().equals("أخرى"))
            entreStock.setTypeProduit(3);
        entreStock.setId(Integer.parseInt(idField.getText()));
        entreStock.setDateExp(Date.valueOf(dateExpField.getValue()));
        entreStock.setDateFab(Date.valueOf(dateFabField.getValue()));
        entreStock.setQuantite(Integer.parseInt(quantiteField.getText()));
        entreStock.setPrix(Double.parseDouble(prixField.getText()));
        entreStock.setFournisseur(fournisseurField.getText());

        int status = new StockDB().addStock(entreStock);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Stock exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Stock");
                break;
            case 1:
                Frais fraisStock = new Frais();
                double total = (Double.parseDouble(prixField.getText())) * (Integer.parseInt(quantiteField.getText()));
                fraisStock.setFraisStock(total);
                int status2 = new fraisDB().addFraisStock(fraisStock);
                switch (status2) {
                    case -1:
                        System.out.println("Error connecting to DB!");
                        break;
                    case 2:
                        System.out.println("Error Stock exist!");
                        break;
                    case 0:
                        System.out.println("Unknown Error failed to add Stock");
                        break;
                    case 1:
                        Notifications.create()
                                .title("تمت الإضافة بنجاح                                   ")
                                .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                                .hideAfter(Duration.millis(2000))
                                .position(Pos.BOTTOM_RIGHT)
                                .darkStyle()
                                .show();

                        StockController.addUserDialog.close();
                }

        }
    }

    @FXML
    void close() {
        StockController.addUserDialog.close();
    }

    @FXML
    void delete() {
        idField.setText(null);
        comboProd.getSelectionModel().select(null);
        nomField.setText(null);
        dateExpField.setValue(null);
        dateFabField.setValue(null);
        quantiteField.setText(null);
        prixField.setText(null);
        fournisseurField.setText(null);
    }

    @FXML
    void printStock() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                add();
            }
        });

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                close();
            }
        });

        comboProd.setItems(typeProduit);


    }
}
