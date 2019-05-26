package home.controllers;

import com.jfoenix.controls.*;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @FXML // Cols of table
    private TableColumn idCol, genreEmpCol, namEmpCol,
            dateCol, presenceCol, heureEntCol, heureSortCol;

    static JFXDialog addUserDialog;
    private ObservableList<Pointage> data;


    private boolean donneSaved= false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donneSaved= false;
        //combo.setItems(options);
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
        errorLabel.setText("No connection to database!!");


        ObservableList<Pointage> dataRows = FXCollections.observableArrayList();
                for(Pointage poi : data)
                {
                    dataRows.add(poi);
                    if (poi.getConfirm().isSelected())
                    System.out.println("l'id est"+poi.getId()+" nom:"+poi.getfullNam()+ "emploie est:"+poi.gettype() +" temp entrée:"+poi.getTempEnt().getValue().toString().substring(0,5)+
                            " temp de sortit"+poi.getTempSor().getValue().toString().substring(0,5)+" la remarque est:"+poi.getRemarkk().getText()+" est present aujourdh'hui");
                    else
                        System.out.println("l'id est"+poi.getId()+" nom:"+poi.getfullNam()+ "emploie est:"+poi.gettype() +" temp entrée:"+poi.getTempEnt().getValue().toString().substring(0,5)+
                                " temp de sortit"+poi.getTempSor().getValue().toString().substring(0,5)+" la remarque est:"+poi.getRemarkk().getText()+"est absent aujourdh'ui");

                    //System.out.println("la valeur 11est:"+poi.getTempEnt().getValue().toString().substring(0,5));


                }

        donneSaved = true; // si les donnée sont stocké dans BDD
       /* Notifications notification = Notifications.create()
                .title("تمت العملية بنجاح                               ")
                .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                .hideAfter(Duration.millis(2000))
                .position(Pos.BOTTOM_RIGHT);
        notification.darkStyle();
        notification.show();
        return;*/

    }

    @FXML
    void sortir(ActionEvent event) {
      //  errorLabel.setText("");
            if (!donneSaved){
            System.out.println("Index is null !");
            Notifications.create()
                    .title("  يرجى حفظ البيانات قبل الخروج  ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
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
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        //  dialog.setOnDialogClosed((event) -> updateTable());
        return new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
    }
    public void updateTable(MouseEvent mouseEvent) {
        initializeTable();
    }



    private void initializeTable() {
      //  tableview = new TableView();

        TableColumn idCol = new TableColumn("رقم التسجيل");
        idCol.setPrefWidth(120);

        TableColumn NameCol = new TableColumn("اسم و لقب العامل");
        NameCol.setPrefWidth(120);

        TableColumn typeEmp = new TableColumn("الوظيفة");
        typeEmp.setPrefWidth(120);

        TableColumn timeEnt = new TableColumn("توقيت الدخول");
        timeEnt.setPrefWidth(150);
        TableColumn timeSor = new TableColumn("توقيت الخروج");
        timeSor.setPrefWidth(150);

        TableColumn remarq = new TableColumn("ملاحظات");
        TableColumn actionCol = new TableColumn("تسجيل الدخول");
        actionCol.setPrefWidth(120);

        tableview.getColumns().addAll(idCol, NameCol, typeEmp, timeEnt, timeSor, remarq,actionCol );

        data = FXCollections.observableArrayList(
                new Pointage(1,"محمد", "موظف","لا توجد ملاحظات","", ""),
                new Pointage(2,"جمال", "معلم","لا توجد ملاحظات","", ""),
                new Pointage(3,"ريتاج", "معلم", "لا توجد ملاحظات","",""),
                new Pointage(4,"أمين", "موظف", "لا توجد ملاحظات","",""),
                new Pointage(5,"شيماء", "عامل نظافة", "لا توجد ملاحظات","","")
        );
        idCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("id")
        );

        NameCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("fullName")
        );
        typeEmp.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("type")
        );
        timeEnt.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("tempEnt")
        );
        timeSor.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("tempSor"));

        remarq.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("remarkk")
        );
       actionCol.setCellValueFactory(
                new PropertyValueFactory<Pointage,String>("confirm")
        );

        tableview.setItems(data);
    }


}
