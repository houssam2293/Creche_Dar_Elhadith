package home.controllers;

import com.jfoenix.controls.*;

import home.dbDir.EmployeDB;
import home.dbDir.PointageDB;
import home.java.Employe;
import home.java.Pointage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class Pointag implements Initializable {


    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label time;

    @FXML
    private JFXTextField searchField;
    @FXML
    private TableView tableview;

    @FXML
    private JFXComboBox<String> combo;

    static JFXDialog listeAbsences;
    ObservableList<Pointage> data ;
    TableColumn idCol,fullNameCol,jobCol,timeEntCol,remarqCol,actionCol;



    private boolean donneSaved= false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donneSaved= false;
        //combo.setItems(options);
       /* searchField.setOnKeyReleased(t -> {
            if(new Validation().alphanumValid(searchField))
                searchField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            else
                searchField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");


        });*/

        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "الوظيفة");
        initializeTable();



        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            time.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    void confirm(ActionEvent event) {
        //Todo add a la table de poitage
        // errorLabel.setText("No connection to database!!");
        int i = 0;
        for(Pointage poi : data) //insertion des données a BDD
        {
            i = new PointageDB().addPointage(poi);

        }
                    /* System.out.println("l'id est"+poi.getId()+" nom:"+poi.getFirstName()+ "emploie est:"+poi.getLastName() +" temp entrée:"+poi.getTempEnt().getValue().toString().substring(0,5)+
                                "  la remarque est:"+poi.getRemarkk().getText()+"est absent aujourdh'ui");*/

        donneSaved = true; // si les donnée sont stocké dans BDD
        switch (i) {
            case -1:System.out.println("Error connecting to DB!");
                errorLabel.setText("No connection to database!!");
                break;
            case 0:System.out.println("Unknown Error failed to add Pointage" );
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();
        }

    }

    @FXML
    void sortir(ActionEvent event) {
        //  errorLabel.setText("");
        if (!donneSaved){
            Notifications.create()
                    .title("  يرجى حفظ البيانات قبل الخروج  ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;}
        else{
            //TODO add pointer to principale menu
        }


    }


    @FXML
    void listeview(ActionEvent event) {

        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/listeAbsence.fxml"));
        } catch (IOException ignored) {
        }
        listeAbsences = getSpecialDialog(addUserPane);
        listeAbsences.show();
    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        //  dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }

    public void updateTable() {
        List<Employe> employes = FXCollections.observableArrayList();
        data = null ;
        data = FXCollections.observableArrayList();

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(employe);
            }
            for (int i=0; i < employes.size();i++){
                Employe ep=employes.get(i);
                data.add(new Pointage(ep.getId(),ep.getNom()+" "+ep.getPrenom(), ep.getFonction(),"remark",""));
            }
        }
        tableview.setItems(data);

    }

    private void initializeTable() {

        idCol = new TableColumn("رقم التسجيل");
        idCol.setPrefWidth(120);
        fullNameCol = new TableColumn("اسم و لقب العامل");
        jobCol = new TableColumn("الوظيفة");
        timeEntCol = new TableColumn("توقيت الدخول");
        timeEntCol.setPrefWidth(120);
        remarqCol = new TableColumn("ملاحظات");
        actionCol = new TableColumn("تسجيل الدخول");
        actionCol.setPrefWidth(120);

        tableview.getColumns().addAll(idCol, fullNameCol, jobCol, timeEntCol, actionCol,remarqCol );
        updateTable();
      /* data = FXCollections.observableArrayList(
                new Pointage(1,"محمد رياض محمد رياض", "موظف","لا توجد ملاحظات",""),
                new Pointage(2,"جمال الدين", "معلم","لا توجد ملاحظات", ""),
                new Pointage(3,"ريتاج امال", "معلم", "لا توجد ملاحظات",""),
                new Pointage(4,"أمين انور", "موظف", "لا توجد ملاحظات",""),
                new Pointage(5,"شيماء نور", "عامل نظافة", "لا توجد ملاحظات","")
        );*/

        idCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("id")
        );
        fullNameCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("firstName")
        );
        jobCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("lastName")
        );
        timeEntCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("tempEnt")
        );
        remarqCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("remarkk")
        );

        actionCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("remark")
        );

        // tableview.setItems(data);
    }


}
