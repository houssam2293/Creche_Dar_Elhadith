package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.dbDir.ConnectionClasse;
import home.dbDir.StockDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SortieStockController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label label5;

    @FXML
    private JFXTextField txt;

    @FXML
    private Label lab1;

    @FXML
    private Label lab2;

    @FXML
    private Label lab3;

    @FXML
    private Label lab4;

    @FXML
    private Label lab;

    static StockDB stock;



    @FXML
    void close(MouseEvent event) {
        StockController.addUserDialog.close();
    }



    @FXML
    void imprimer(ActionEvent event) {
        PrintService[] ps= PrinterJob.lookupPrintServices();
        for(PrintService service:ps){
            System.out.println("- "+service);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        label5.setText(dateFormat.format(date));
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            System.out.println("failed to connect to the server!");
        }
        String sql = "select * from stock ";
        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(sql);
             double x=0;
            while (rst.next()) {
                x=x+rst.getDouble("prixTotale");


            }
            lab2.setText(String.valueOf(x));
        } catch (SQLException ex) {
            //return null;
        }
        String sq = "SELECT * from stock\n" +
                "where dateExp= CURDATE(); ";
        try {
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(sq);
            double x=0;
            while (rst.next()) {
                x=x+rst.getDouble("prixTotale");


            }
            lab1.setText(String.valueOf(x));
            double y=x*30;
            lab3.setText(String.valueOf(y));
           /* double z=Double.parseDouble(txt.getText().trim().toLowerCase());
            if(z<y)
                lab3.setText("anwar belaid");
            else
                lab3.setText("good afternoun");
           System.out.println(z);
*/

        } catch (SQLException ex) {
            //return null;
        }


    }


}

