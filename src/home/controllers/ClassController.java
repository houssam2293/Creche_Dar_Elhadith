package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.ClasseDB;
import home.dbDir.EleveDB;
import home.dbDir.EmployeDB;
import home.java.ClasseModel;
import home.java.Eleve;
import home.java.Employe;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static home.controllers.editClassController.classSelected;


public class ClassController implements Initializable {

    public HBox lesclass;
    public JFXButton edit;
    public JFXButton remove;
    public JFXButton cree;
    public VBox ClassHome;
    private String clss;

    @FXML
    private StackPane Class;
    @FXML
    private JFXTreeTableView<TableEleve> tableEleve;
    @FXML
    private JFXListView<String> ClassSelect;

    @FXML
    private Label prof;


    @FXML // Cols of table
    public JFXTreeTableColumn<TableEleve, String> idCole, nameCole,dateOfBirthCole,remarqueCol;

    static JFXDialog addUserDialog, editUserDialog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clss : clsDB) {
                ClassSelect.getItems().add(clss.getClassNam());

            }
        }
    }

    @FXML
    public void creeClass(ActionEvent actionEvent) {

        AnchorPane addClassPane = null;
        try {
            addClassPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/addClassForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addClassPane);
        addUserDialog.show();
    }




    @FXML
    public void editClass(ActionEvent actionEvent) {
        String cls = ClassSelect.getSelectionModel().getSelectedItem();
        System.out.println(clss);
        if (cls == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clsse : clsDB) {
                if (clsse.getClassNam().compareTo(cls)==0){
                    classSelected=new ClasseModel();
                    classSelected.setId(clsse.getId());
                    classSelected.setClassNam(clsse.getClassNam());
                    classSelected.setClassRom(clsse.getClassRom());
                    classSelected.setmaxNbrElev(clsse.getmaxNbrElev());
                    classSelected.setremarque(clsse.getremarque());
                }
            }

            }
        AnchorPane editUserPane = null;
        try {
            editUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/editClassForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        editUserDialog = getSpecialDialog(editUserPane);
        editUserDialog.show();


        updateListview();
    }



    @FXML
    public void choseClass(MouseEvent mouseEvent) {
        clss= ClassSelect.getSelectionModel().getSelectedItem();
        tableEleve.setVisible(true);
        tableEleve.getColumns().clear();
        prof.setText(prof(clss));
        initializeTableEleve(clss);

    }

    @FXML
    public void removeClass(ActionEvent actionEvent) {
        String clss = ClassSelect.getSelectionModel().getSelectedItem();
        System.out.println(clss);
        if (clss == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("تأكيد العملية");
        Text contentText = new Text("هل أنت متأكد من مسح البيانات؟");
        headerText.setStyle("-fx-font-size: 19px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);

        JFXDialog dialog = new JFXDialog(Class, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("نعم");
        btnOk.setOnAction(e -> {
            int status = new ClasseDB().removClasse(clss);
            System.out.println("status : " + status);
            if (status == -1) {
                System.out.println("Connection Failed !");
            } else {
                Notifications notification = Notifications.create()
                        .title("تمت العملية بنجاح                               ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT);
                notification.darkStyle();
                notification.show();
                updateListview();
                tableEleve.setVisible(false);

            }
            dialog.close();

        });

        JFXButton btnNo = new JFXButton("لا");
        btnOk.setPrefSize(120, 40);
        btnNo.setPrefSize(120, 40);
        btnOk.setStyle("-fx-font-size: 18px");
        btnNo.setStyle("-fx-font-size: 18px");

        content.setActions(btnOk, btnNo);

        dialog.getStylesheets().add("/home/resources/css/main.css");
        btnNo.setOnAction(e -> dialog.close());
        dialog.show();
    }

    @FXML
    void printClass() {
        Text textArea = new Text();
        textArea.setText("Printing test!    Printing test!\nPrinting test!  Printing test!  Printing test!\nPrinting test!  Printing test!  Printing test!  Printing test!");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(null);
        Printer printer= job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        textArea.setScaleX(0.85);
        textArea.setScaleY(0.85);
        textArea.setTranslateX(50);
        textArea.setTranslateY(10);
        job.printPage(pageLayout,textArea);
        job.endJob();
        textArea.setScaleX(1);
        textArea.setScaleY(1);
        textArea.setTranslateX(0);
        textArea.setTranslateY(0);
    }


    public void initializeTableEleve(String nome) {
        idCole = new JFXTreeTableColumn<>("الرقم");
        idCole.setPrefWidth(80);
        idCole.setCellValueFactory(param -> param.getValue().getValue().id);

        nameCole = new JFXTreeTableColumn<>("الإسم و اللقب ");
        nameCole.setPrefWidth(350);
        nameCole.setCellValueFactory(param -> param.getValue().getValue().firstname);


        dateOfBirthCole = new JFXTreeTableColumn<>("تاريخ الميلاد");
        dateOfBirthCole.setPrefWidth(150);
        dateOfBirthCole.setCellValueFactory(param -> param.getValue().getValue().birthday);

        remarqueCol = new JFXTreeTableColumn<>("ملاحظات");
        remarqueCol.setPrefWidth(350);
        remarqueCol.setCellValueFactory(param -> param.getValue().getValue().remarque);


        updateTable(nome);


        tableEleve.getColumns().addAll(idCole, nameCole,  dateOfBirthCole,  remarqueCol);
        tableEleve.setShowRoot(false);
        tableEleve.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateTable(String clss) {
        ObservableList<TableEleve> eleves = FXCollections.observableArrayList();

        List<Eleve> studentDB = new EleveDB().getEleve();
        if (studentDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Eleve eleve : studentDB) {
                if(eleve.getClasse().compareTo(clss)==0)
                eleves.add(new TableEleve(eleve.getId(), eleve.getNom().toUpperCase()+" " +eleve.getPrenom().toUpperCase(),
                        eleve.getDateNaissance(), eleve.getRemarque()));
            }
        }

        final TreeItem<TableEleve> treeItem = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            tableEleve.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }


    static class TableEleve extends RecursiveTreeObject<TableEleve> {
        StringProperty id, firstname, birthday,remarque;


        public TableEleve(int id, String firstname,   Date birthday, String remarque) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.remarque = new SimpleStringProperty(String.valueOf(remarque));
        }
    }



    public JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(Class, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateListview());

        return dialog;
    }

    @FXML
    private void updateListview() {
        ClassSelect.getItems().clear();
        List<ClasseModel> clsDB = new ClasseDB().getClasse();
        if (clsDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (ClasseModel clss : clsDB) {
                ClassSelect.getItems().add(clss.getClassNam());

            }
        }
    }


    private String prof(String cls) {
        List<Employe> EmplyDB = new EmployeDB().getEmployee();
        if (EmplyDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Employe empl : EmplyDB) {
                if (cls.equals(empl.getClasse()))
                    return empl.getNom()+" "+empl.getPrenom();
            }

            }
        return " ";
    }
}
