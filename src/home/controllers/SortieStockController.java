package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.dbDir.ConnectionClasse;
import home.dbDir.StockDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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

    @FXML
    private Label lab5;

    static StockDB stock;

    private double x = 0;


    @FXML
    void close(MouseEvent event) {
        StockController.addUserDialog.close();
    }



    @FXML
    void imprimer(ActionEvent event) {


        TextArea textArea = new TextArea();
        textArea.setFont(Font.font("sanSerif", 12));
        textArea.setEditable(false);
        textArea.setText("imprimer");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(null);
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        textArea.minWidth(pageLayout.getPrintableWidth());
        textArea.minHeight(pageLayout.getPrintableHeight());
        job.printPage(pageLayout, root);


        job.endJob();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        label5.setText(dateFormat.format(date));
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            //return null;
        }
        String sql = "select * from stock ";
        try {
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(sql);
            double x = 0;
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
            while (rst.next()) {
                x=x+rst.getDouble("prixTotale");


            }
            lab1.setText(String.valueOf(x));
            lab3.setText(String.valueOf(x * 30));


        } catch (SQLException ex) {
            //return null;
        }

       /* ArrayList<Eleve> elevDB = new EleveDB().getEleve();
        for(Eleve elv :elevDB)


        String nbr = "SELECT count(id) from eleve\n" ;

        try {
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(nbr);
            double y = 0;
            y =y +rst.getDouble("count(id)");
System.out.println("aaa"+y);
            System.out.println("jjj"+rst);


        } catch (SQLException ex) {
            //return null;
        }*/

        String sqle = "SELECT * from eleve";
        try {
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(sqle);
            double y = 0;
            while (rst.next()) {
                y = y + 1;


            }
            lab4.setText(String.valueOf(x / y));



        } catch (SQLException ex) {
            //return null;
        }


    }


}

