package home.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.StockDB;
import home.dbDir.fraisDB;
import home.java.EntreStock;
import home.java.Frais;
import home.java.Validation;
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
import java.time.LocalDate;
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

    void valider() {


        idField.setOnKeyReleased(t -> {
            if (new Validation().isNumber(idField)) {
                idField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                idField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        prixField.setOnKeyReleased(t -> {
            if (new Validation().isDouble(prixField)) {
                prixField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                prixField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        nomField.setOnKeyReleased(t -> {
            if (new Validation().arabValid(nomField) || new Validation().frenshValid(nomField)) {
                nomField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                nomField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });
        quantiteField.setOnKeyReleased(t -> {
            if (new Validation().isNumber(quantiteField)) {
                quantiteField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                quantiteField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });

        fournisseurField.setOnKeyReleased(t -> {
            if (new Validation().arabValid(fournisseurField)) {
                fournisseurField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
            } else {
                fournisseurField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
            }
        });


        dateExpField.setOnAction(t -> {
            LocalDate date1 = dateExpField.getValue();
            LocalDate date2 = dateFabField.getValue();
            int a = date1.compareTo(date2);

            if (a > -1) {
                dateExpField.setStyle(" -fx-border-color: #8CC25E ; -fx-border-width: 0 0 4 0");
                System.out.println("wah!");


            } else {
                dateExpField.setStyle("-fx-effect: innershadow(three-pass-box, red, 6 , 0.5, 1, 1);");
                System.out.println("laah!");
            }
        });
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

        root.setOnKeyReleased(e -> valider());
        root.setOnMouseClicked(e -> valider());

        comboProd.setItems(typeProduit);


    }
}
