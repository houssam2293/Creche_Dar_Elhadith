package home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DemmandeController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTreeTableView<?> table;

    @FXML
    private JFXDatePicker dateDemmande;

    @FXML
    private Label Label1;

    @FXML
    private Label label5;



    @FXML
    void changeDemmande(ActionEvent event) {

    }

    @FXML
    void close(MouseEvent event) {
        StockController.addUserDialog.close();
    }

    @FXML
    void deleteDemmande(ActionEvent event) {

    }

    @FXML
    void imprimerDemmande(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        label5.setText(dateFormat.format(date));
    }
}
