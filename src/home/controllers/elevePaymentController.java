package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import home.dbDir.EleveDB;
import home.dbDir.fraisDB;
import home.java.Eleve;
import home.java.Frais;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class elevePaymentController implements Initializable {

    static Eleve paidEleve;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField payment;
    @FXML
    private JFXTextField tranches;
    @FXML
    private JFXButton btnOk;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label id;
    @FXML
    private Label tranchesRestantes;
    @FXML
    private Label montantRestant;

    @FXML
    void btnClose() {

        EleveController.paymentUserDialog.close();

    }

    @FXML
    void btnAdd() {
        Frais frais = new Frais();
        frais.setFraisEleve(Double.valueOf(payment.getText()));
        int status = new fraisDB().addFraisEleve(frais);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Frais does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Frais");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                //EleveController.paymentUserDialog.close();
        }

        int id = paidEleve.getId();
        double montantPaid = Double.parseDouble(payment.getText());
        int tranchePaid;
        if ((paidEleve.getMontantRestant() - montantPaid) == 0)
            tranchePaid = paidEleve.getTranches();
        else
            tranchePaid = Integer.parseInt(tranches.getText());

        int status2 = new EleveDB().deductTranche(id, tranchePaid, montantPaid);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Eleve does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to update Eleve");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                EleveController.paymentUserDialog.close();
        }


    }


    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                ManageEmployeeController.paymentUserDialog.close();
            }
        });


        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ENTER)) {
                btnAdd();
            }
        });

        name.setText(paidEleve.getNom());
        id.setText(String.valueOf(paidEleve.getId()));
        tranchesRestantes.setText(String.valueOf(paidEleve.getTranches()));
        montantRestant.setText(String.valueOf(paidEleve.getMontantRestant()));
        date.setText(String.valueOf(LocalDate.now()));


    }

}
