package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class ManageAccountController implements Initializable {

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                    "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                    "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد"
            );

    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;
    @FXML
    private  JFXTreeTableView<TableEmployee> treeTableView;

    @FXML
    private JFXComboBox<String> combo;

    @FXML // Cols of table
    public JFXTreeTableColumn<TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol, addressCol, phoneCol, socialSecurNumbCol,
            diplomeCol, dateFirstEmploCol, experienceCol, contractRenCol;

    public static JFXDialog addUserDialog, editUserDialog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //combo.setItems(options);
        combo.getItems().addAll(new String[]{"رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد","الحالة المدنية","بنون","بنات"});
        initializeTable();
    }
    @FXML
    void addUser(ActionEvent event) {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/fxml/AddUserForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    @FXML
    void editUser(ActionEvent event) {

    }

    @FXML
    void removeUser(ActionEvent event) {

    }

    @FXML
    void updateTable() {
        errorLabel.setText("");
        ObservableList<TableEmployee> employes = FXCollections.observableArrayList();

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(new TableEmployee(employe.getId(),employe.getNom().toUpperCase(),employe.getPrenom().toUpperCase(),employe.getDateNaissance(),
                                                employe.getLieuNaissance(),employe.getAdresse(),employe.getExperience(),employe.getNumTelephone(),employe.getSocialSecurityNumber(),
                                                employe.getDiplome(),employe.getDate_debut(),employe.getFonction(),employe.getRenouvlement_de_contrat(),employe.estmarier(),
                                                employe.getCelibacyTitle(),employe.getMaleChild(),employe.getFemaleChild()));
            }
        }

       final TreeItem<TableEmployee> treeItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
        try {
            treeTableView.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }


    class TableEmployee extends RecursiveTreeObject<TableEmployee> {
        StringProperty id, firstname, lastname, birthday, birthplace, job, experience;
        StringProperty addresse, phone, socialSN, diplom, firstdaywor, renouvlementcotract;
        StringProperty marier, nomCeleb, nombreEM, nombreEF;

        public TableEmployee(int id, String firstname, String lastname, Date birthday, String birthplace, String addresse,String experience,
                              String phone, String socialSN, String diplom,
                             Date firstdaywor, String job, String renouvlementcotract, boolean marier, String nomCeleb,
                             int nombreEM, int nombreEF) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.lastname = new SimpleStringProperty(String.valueOf(lastname));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.birthplace = new SimpleStringProperty(String.valueOf(birthplace));
            this.job = new SimpleStringProperty(String.valueOf(job));
            this.experience = new SimpleStringProperty(String.valueOf(experience));
            this.addresse = new SimpleStringProperty(String.valueOf(addresse));
            this.phone = new SimpleStringProperty(String.valueOf(phone));
            this.socialSN = new SimpleStringProperty(String.valueOf(socialSN));
            this.diplom = new SimpleStringProperty(String.valueOf(diplom));
            this.firstdaywor = new SimpleStringProperty(String.valueOf(firstdaywor));
            this.renouvlementcotract = new SimpleStringProperty(String.valueOf(renouvlementcotract));
            this.marier = new SimpleStringProperty((marier) ? "متزوج" : "أعزب");
            this.nomCeleb = new SimpleStringProperty(String.valueOf(nomCeleb));
            this.nombreEM = new SimpleStringProperty(String.valueOf(nombreEM));
            this.nombreEF = new SimpleStringProperty(String.valueOf(nombreEF));

        }
    }

    public void initializeTable() {
        idCol = new JFXTreeTableColumn<>("رقم التسجيل");
        idCol.setPrefWidth(120);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id);

        firstnameCol = new JFXTreeTableColumn<>("الإسم");
        firstnameCol.setPrefWidth(150);
        firstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCol = new JFXTreeTableColumn<>("اللقب");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory(param -> param.getValue().getValue().lastname);

        dateOfBirthCol = new JFXTreeTableColumn<>("تاريخ الملاد");
        dateOfBirthCol.setPrefWidth(100);
        dateOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthday);

        placeOfBirthCol = new JFXTreeTableColumn<>("مكان الملاد");
        placeOfBirthCol.setPrefWidth(100);
        placeOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthplace);

        jobCol = new JFXTreeTableColumn<>("المهنة");
        jobCol.setPrefWidth(100);
        jobCol.setCellValueFactory(param -> param.getValue().getValue().job);

        addressCol = new JFXTreeTableColumn<>("العنوان الشخصي");
        addressCol.setPrefWidth(120);
        addressCol.setCellValueFactory(param -> param.getValue().getValue().addresse);

        phoneCol = new JFXTreeTableColumn<>("الهاتف");
        phoneCol.setPrefWidth(75);
        phoneCol.setCellValueFactory(param -> param.getValue().getValue().phone);

        socialSecurNumbCol = new JFXTreeTableColumn<>("رقم الضمان الإجتماعي");
        socialSecurNumbCol.setPrefWidth(150);
        socialSecurNumbCol.setCellValueFactory(param -> param.getValue().getValue().socialSN);

        diplomeCol = new JFXTreeTableColumn<>("الشهادات");
        diplomeCol.setPrefWidth(120);
        diplomeCol.setCellValueFactory(param -> param.getValue().getValue().diplom);

        dateFirstEmploCol = new JFXTreeTableColumn<>("تاريخ أول تعيين");
        dateFirstEmploCol.setPrefWidth(75);
        dateFirstEmploCol.setCellValueFactory(param -> param.getValue().getValue().firstdaywor);

        experienceCol = new JFXTreeTableColumn<>("الخبرة");
        experienceCol.setPrefWidth(75);
        experienceCol.setCellValueFactory(param -> param.getValue().getValue().experience);

        contractRenCol = new JFXTreeTableColumn<>("حالة التعاقد");
        contractRenCol.setPrefWidth(120);
        contractRenCol.setCellValueFactory(param -> param.getValue().getValue().renouvlementcotract);

        updateTable();

        treeTableView.getColumns().addAll(idCol,firstnameCol,lastNameCol,dateOfBirthCol,placeOfBirthCol,addressCol,phoneCol,socialSecurNumbCol,jobCol,diplomeCol,dateFirstEmploCol,experienceCol,contractRenCol);
        treeTableView.setShowRoot(false);

    }


    public JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> {
            updateTable();
        });
        return dialog;
    }
}
