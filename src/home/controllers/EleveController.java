package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.EleveDB;
import home.java.Eleve;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static home.controllers.EditEleveFormController.eleveSelected;
import static home.controllers.EleveRemarqueController.notedEleve;
import static home.controllers.elevePaymentController.paidEleve;
import static home.controllers.elevePrintController.elevePrinted;
import static home.controllers.ficheEleveController.eleveFiled;



public class EleveController<Adding> implements Initializable {

    public static JFXDialog addUserDialog, editUserDialog, notesUserDialog, fileUserDialog, paymentUserDialog, printUserDialog;

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXTreeTableView<TableEleve> treeTableView;
    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton Refresher;
    @FXML
    private JFXButton Adder;
    @FXML
    private JFXButton Editer;
    @FXML
    private JFXButton Remover;
    @FXML
    private JFXButton Ficher;
    @FXML
    private JFXButton Marker;
    @FXML
    private JFXButton Printer;
    @FXML // Cols of table
    public JFXTreeTableColumn<TableEleve, String> idCol, genderCol, firstnameCol, lastNameCol, classRoomCol,
            dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, remarqueCol,
            schoolYearCol, regimeCol, nameFatherCol, nameMotherCol, lastNameMotherCol, workFatherCol, workMotherCol,
            wakilCol, maladieCol, tranchesCol, montantRestantCol;

    @FXML
    private JFXComboBox<String> combo;
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "رقم التسجيل", "الجنس", "الإسم", "اللقب", "القسم", "تاريخ الملاد", "مكان الملاد", "الهاتف", "ملاحظات", "العنوان",
                    "السنة الدراسية", "النظام", "اسم الأب", "اسم الأم", "لقب الأم"
                    , "مهنة الأب", "مهنة الأم", "وكيل", "مرض", "قطع"
            );
    @FXML
    private JFXButton Money;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //combo.setItems(options);
        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "القسم", "العنوان", "الهاتف", "ملاحظات", "قطع");
        initializeTable();

        Refresher.setTooltip(new Tooltip("تحديث"));
        Adder.setTooltip(new Tooltip("إضافة"));
        Editer.setTooltip(new Tooltip("تعديل"));
        Remover.setTooltip(new Tooltip("إزالة"));
        Ficher.setTooltip(new Tooltip("بطاقة"));
        Marker.setTooltip(new Tooltip("ملاحظة"));
        Printer.setTooltip(new Tooltip("طبع"));
        Money.setTooltip(new Tooltip("دفع"));
    }


    @FXML
    public void addEleve(ActionEvent actionEvent) {
        errorLabel.setText("");
        AnchorPane addElevePane = null;
        try {
            addElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/addEleveForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addElevePane);
        addUserDialog.show();
    }

    @FXML
    public void editEleve(ActionEvent actionEvent) {
        errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        eleveSelected = new Eleve();
        eleveSelected.setId(Integer.parseInt(id));
        eleveSelected.setGender(Integer.parseInt(genderCol.getCellData(index)));
        eleveSelected.setPrenom(firstnameCol.getCellData(index));
        eleveSelected.setNom(lastNameCol.getCellData(index));
        eleveSelected.setClasse(classRoomCol.getCellData(index));
        eleveSelected.setDateNaissance(java.sql.Date.valueOf(dateOfBirthCol.getCellData(index)));
        eleveSelected.setLieuNaissance(placeOfBirthCol.getCellData(index));
        eleveSelected.setAdresse(addressCol.getCellData(index));
        eleveSelected.setPhone(phoneCol.getCellData(index));
        eleveSelected.setRemarque(remarqueCol.getCellData(index));
        eleveSelected.setAnneeScolaire(Integer.parseInt(schoolYearCol.getCellData(index)));
        eleveSelected.setRegime(regimeCol.getCellData(index));
        eleveSelected.setPrenomPere(nameFatherCol.getCellData(index));
        eleveSelected.setPrenomMere(nameMotherCol.getCellData(index));
        eleveSelected.setNomMere(lastNameMotherCol.getCellData(index));
        eleveSelected.setTravailPere(workFatherCol.getCellData(index));
        eleveSelected.setTravailMere(workMotherCol.getCellData(index));
        eleveSelected.setWakil(wakilCol.getCellData(index));
        eleveSelected.setMaladie(maladieCol.getCellData(index));


        AnchorPane editElevePane = null;
        try {
            editElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/editEleveForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        editUserDialog = getSpecialDialog(editElevePane);
        editUserDialog.show();
    }

    @FXML
    public void removeEleve(ActionEvent actionEvent) {
        errorLabel.setText("");
        int id;
        if (idCol.getCellData(treeTableView.getSelectionModel().getSelectedIndex()) == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد مسحه                                 ")
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle()
                    .showWarning();
            return;
        }


    id = Integer.valueOf(idCol.getCellData(treeTableView.getSelectionModel().getSelectedIndex()));
    JFXDialogLayout content = new JFXDialogLayout();
    Text headerText = new Text("تأكيد العملية");
    Text contentText = new Text("هل أنت متأكد من مسح البيانات؟");
    headerText.setStyle("-fx-font-size: 19px");
    contentText.setStyle("-fx-font-size: 18px");

    content.setHeading(headerText);
    content.setBody(contentText);

    JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

    JFXButton btnOk = new JFXButton("نعم");
    btnOk.setOnAction(e -> {
        int status = new EleveDB().removeEleve(id);
        System.out.println("status : " + status);
        if (status == -1) {
            errorLabel.setText("Connection Failed !");
        } else {
            Notifications notification = Notifications.create()
                    .title("تمت العملية بنجاح                               ")
                    .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();
            updateTable();
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
    public void ficheEleve(ActionEvent actionEvent){
        errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        eleveFiled = new Eleve();
        eleveFiled.setId(Integer.parseInt(id));
        eleveFiled.setGender(Integer.parseInt(genderCol.getCellData(index)));
        eleveFiled.setPrenom(firstnameCol.getCellData(index));
        eleveFiled.setNom(lastNameCol.getCellData(index));
        eleveFiled.setClasse(classRoomCol.getCellData(index));
        eleveFiled.setDateNaissance(java.sql.Date.valueOf(dateOfBirthCol.getCellData(index)));
        eleveFiled.setLieuNaissance(placeOfBirthCol.getCellData(index));
        eleveFiled.setAdresse(addressCol.getCellData(index));
        eleveFiled.setPhone(phoneCol.getCellData(index));
        eleveFiled.setRemarque(remarqueCol.getCellData(index));
        eleveFiled.setAnneeScolaire(Integer.parseInt(schoolYearCol.getCellData(index)));
        eleveFiled.setRegime(regimeCol.getCellData(index));
        eleveFiled.setPrenomPere(nameFatherCol.getCellData(index));
        eleveFiled.setPrenomMere(nameMotherCol.getCellData(index));
        eleveFiled.setNomMere(lastNameMotherCol.getCellData(index));
        eleveFiled.setTravailPere(workFatherCol.getCellData(index));
        eleveFiled.setTravailMere(workMotherCol.getCellData(index));
        eleveFiled.setWakil(wakilCol.getCellData(index));
        eleveFiled.setMaladie(maladieCol.getCellData(index));


        AnchorPane ficheElevePane = null;
        try {
            ficheElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/ficheEleve.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fileUserDialog = getSpecialDialog(ficheElevePane);
        fileUserDialog.show();
    }



    @FXML
    public void notes (ActionEvent actionEvent){
        errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        notedEleve = new Eleve();
        notedEleve.setId(Integer.parseInt(id));
        notedEleve.setRemarque(remarqueCol.getCellData(index));

        AnchorPane notesElevePane = null;
        try {
            notesElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/eleveRemarque.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        notesUserDialog = getSpecialDialog(notesElevePane);
        notesUserDialog.show();
    }

    @FXML
    public void print(ActionEvent actionEvent) {
        errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        elevePrinted = new Eleve();
        elevePrinted.setId(Integer.parseInt(id));
        elevePrinted.setPrenom(firstnameCol.getCellData(index));
        elevePrinted.setNom(lastNameCol.getCellData(index));
        elevePrinted.setClasse(classRoomCol.getCellData(index));
        elevePrinted.setAdresse(addressCol.getCellData(index));
        elevePrinted.setPhone(phoneCol.getCellData(index));


        AnchorPane printElevePane = null;
        try {
            printElevePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/elevePrintForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        printUserDialog = getSpecialDialog(printElevePane);
        printUserDialog.show();
    }


    @FXML
    public void payerTranche(ActionEvent actionEvent) {

        errorLabel.setText("");
        int index = treeTableView.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCol.getCellData(index);
        String tranches = tranchesCol.getCellData(index);
        String montantRestant = montantRestantCol.getCellData(index);
        if (id == null) {
            System.out.println("Index is null !");
            Notifications.create()
                    .title("يرجى تحديد الحقل المراد تحديثه                                ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }
        paidEleve = new Eleve();
        paidEleve.setId(Integer.parseInt(id));
        paidEleve.setNom(lastNameCol.getCellData(index));
        paidEleve.setTranches(Integer.parseInt(tranches));
        paidEleve.setMontantRestant(Double.parseDouble(montantRestant));

        if (paidEleve.getMontantRestant() == 0) {
            System.out.println("Already paid !");
            Notifications.create()
                    .title("هذا التلميذ دفع كل حقوق التسجيل                      ")
                    .darkStyle()
                    .hideAfter(Duration.millis(2000))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        } else {

            AnchorPane paymentUserPane = null;
            try {
                paymentUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/elevePayment.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            paymentUserDialog = getSpecialDialog(paymentUserPane);
            paymentUserDialog.show();
        }

    }

    @FXML
    private void updateTable() {
        errorLabel.setText("");
        ObservableList<TableEleve> eleves = FXCollections.observableArrayList();
        combo.setValue(null);

        searchField.setText(null);

        List<Eleve> studentDB = new EleveDB().getEleve();
        if (studentDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Eleve eleve : studentDB) {
                eleves.add(new TableEleve(eleve.getId(), eleve.getGender(), eleve.getNom().toUpperCase(), eleve.getPrenom().toUpperCase(), eleve.getClasse(), eleve.getDateNaissance(),
                        eleve.getLieuNaissance().toUpperCase(), eleve.getAdresse(), eleve.getPhone(), eleve.getRemarque(), eleve.getAnneeScolaire(), eleve.getRegime(),
                        eleve.getPrenomPere(), eleve.getPrenomMere(), eleve.getNomMere(), eleve.getTravailPere(), eleve.getTravailMere(), eleve.getWakil(), eleve.getMaladie(), eleve.getTranches(), eleve.getMontantRestant()));
            }
        }

        final TreeItem<TableEleve> treeItem = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            treeTableView.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }

    private void initializeTable() {
        idCol = new JFXTreeTableColumn<>("رقم التسجيل");
        idCol.setPrefWidth(120);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id);

        genderCol=new JFXTreeTableColumn<>("الجنس");
        genderCol.setPrefWidth(120);
        genderCol.setCellValueFactory(param -> param.getValue().getValue().gender);
        genderCol.setVisible(false);

        firstnameCol = new JFXTreeTableColumn<>("الإسم");
        firstnameCol.setPrefWidth(150);
        firstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCol = new JFXTreeTableColumn<>("اللقب");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory(param -> param.getValue().getValue().lastname);

        classRoomCol = new JFXTreeTableColumn<>("القسم");
        classRoomCol.setPrefWidth(100);
        classRoomCol.setCellValueFactory(param -> param.getValue().getValue().classroom);



        dateOfBirthCol = new JFXTreeTableColumn<>("تاريخ الملاد");
        dateOfBirthCol.setPrefWidth(100);
        dateOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthday);

        placeOfBirthCol = new JFXTreeTableColumn<>("مكان الملاد");
        placeOfBirthCol.setPrefWidth(100);
        placeOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthplace);



        addressCol = new JFXTreeTableColumn<>("العنوان");
        addressCol.setPrefWidth(120);
        addressCol.setCellValueFactory(param -> param.getValue().getValue().addresse);

        phoneCol = new JFXTreeTableColumn<>("الهاتف");
        phoneCol.setPrefWidth(75);
        phoneCol.setCellValueFactory(param -> param.getValue().getValue().phone);

        remarqueCol = new JFXTreeTableColumn<>("ملاحظات");
        remarqueCol.setPrefWidth(150);
        remarqueCol.setCellValueFactory(param -> param.getValue().getValue().remarque);

        schoolYearCol=new JFXTreeTableColumn<>("السنة الدراسية");
        schoolYearCol.setPrefWidth(150);
        schoolYearCol.setCellValueFactory(param -> param.getValue().getValue().schoolYear);
        schoolYearCol.setVisible(false);

        regimeCol=new JFXTreeTableColumn<>("النظام");
        regimeCol.setPrefWidth(150);
        regimeCol.setCellValueFactory(param -> param.getValue().getValue().regime);
        regimeCol.setVisible(false);

        nameFatherCol=new JFXTreeTableColumn<>("اسم الأب");
        nameFatherCol.setPrefWidth(150);
        nameFatherCol.setCellValueFactory(param -> param.getValue().getValue().nameFather);
        nameFatherCol.setVisible(false);

        nameMotherCol=new JFXTreeTableColumn<>("اسم الأم");
        nameMotherCol.setPrefWidth(150);
        nameMotherCol.setCellValueFactory(param -> param.getValue().getValue().nameMother);
        nameMotherCol.setVisible(false);

        lastNameMotherCol=new JFXTreeTableColumn<>("لقب الأم");
        lastNameMotherCol.setPrefWidth(150);
        lastNameMotherCol.setCellValueFactory(param -> param.getValue().getValue().lastNameMother);
        lastNameMotherCol.setVisible(false);

        workFatherCol=new JFXTreeTableColumn<>("مهنة الأب");
        workFatherCol.setPrefWidth(150);
        workFatherCol.setCellValueFactory(param -> param.getValue().getValue().workFather);
        workFatherCol.setVisible(false);

        workMotherCol=new JFXTreeTableColumn<>("مهنة الأم");
        workMotherCol.setPrefWidth(150);
        workMotherCol.setCellValueFactory(param -> param.getValue().getValue().workMother);
        workMotherCol.setVisible(false);

        wakilCol=new JFXTreeTableColumn<>("وكيل");
        wakilCol.setPrefWidth(150);
        wakilCol.setCellValueFactory(param -> param.getValue().getValue().wakil);
        wakilCol.setVisible(false);

        maladieCol=new JFXTreeTableColumn<>("مرض");
        maladieCol.setPrefWidth(150);
        maladieCol.setCellValueFactory(param -> param.getValue().getValue().maladie);
        maladieCol.setVisible(false);

        tranchesCol = new JFXTreeTableColumn<>("قطع");
        tranchesCol.setPrefWidth(150);
        tranchesCol.setCellValueFactory(param -> param.getValue().getValue().tranches);
        tranchesCol.setVisible(true);

        montantRestantCol = new JFXTreeTableColumn<>("مبلغ متبقي");
        montantRestantCol.setPrefWidth(150);
        montantRestantCol.setCellValueFactory(param -> param.getValue().getValue().montantRestant);
        montantRestantCol.setVisible(false);


        updateTable();

        searchField.textProperty().addListener(e -> filterSearchTable());
        combo.setOnAction(e -> filterSearchTable());

        treeTableView.getColumns().addAll(idCol, genderCol, firstnameCol, lastNameCol, classRoomCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, remarqueCol,
                schoolYearCol, regimeCol, nameFatherCol, nameMotherCol, lastNameMotherCol, workFatherCol, workMotherCol, wakilCol, maladieCol, tranchesCol, montantRestantCol);
        treeTableView.setShowRoot(false);
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }

    private void filterSearchTable() {

        int index = treeTableView.getSelectionModel().getSelectedIndex();
        treeTableView.setPredicate(eleves -> {
            switch (combo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return eleves.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 1:
                    return eleves.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 2:
                    return eleves.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 3:
                    return eleves.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 4:
                    return eleves.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 5:
                    return eleves.getValue().classroom.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 6:
                    return eleves.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 7:
                    return eleves.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 8:
                    return eleves.getValue().remarque.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 9:
                    return eleves.getValue().tranches.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                default:
                    return eleves.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().classroom.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().remarque.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            eleves.getValue().tranches.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
            }
        });
    }

    static class TableEleve extends RecursiveTreeObject<TableEleve> {
        StringProperty id, gender, firstname, lastname, classroom, birthday, birthplace;
        StringProperty addresse, phone, remarque;
        StringProperty schoolYear, regime, nameFather, nameMother, lastNameMother, workFather, workMother, wakil, maladie, tranches, montantRestant;


        public TableEleve(int id, int gender, String firstname, String lastname, String classroom, Date birthday, String birthplace, String addresse, String phone,
                          String remarque, int schoolYear, String regime, String nameFather, String nameMother,
                          String lastNameMother, String workFather, String workMother, String wakil, String maladie, int tranches, double montantRestant) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.gender = new SimpleStringProperty(String.valueOf(gender));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.lastname = new SimpleStringProperty(String.valueOf(lastname));
            this.classroom = new SimpleStringProperty(String.valueOf(classroom));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.birthplace = new SimpleStringProperty(String.valueOf(birthplace));
            this.addresse = new SimpleStringProperty(String.valueOf(addresse));
            this.phone = new SimpleStringProperty(String.valueOf(phone));
            this.remarque = new SimpleStringProperty(String.valueOf(remarque));
            this.schoolYear = new SimpleStringProperty(String.valueOf(schoolYear));
            this.regime = new SimpleStringProperty(String.valueOf(regime));
            this.nameFather = new SimpleStringProperty(String.valueOf(nameFather));
            this.nameMother = new SimpleStringProperty(String.valueOf(nameMother));
            this.lastNameMother = new SimpleStringProperty(String.valueOf(lastNameMother));
            this.workFather = new SimpleStringProperty(String.valueOf(workFather));
            this.workMother = new SimpleStringProperty(String.valueOf(workMother));
            this.wakil = new SimpleStringProperty(String.valueOf(wakil));
            this.maladie = new SimpleStringProperty(String.valueOf(maladie));
            this.tranches = new SimpleStringProperty(String.valueOf(tranches));
            this.montantRestant = new SimpleStringProperty(String.valueOf(montantRestant));


        }
    }


}
