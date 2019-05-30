package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.EmployeDB;
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

import static home.controllers.EditEmployeeFormController.employeeSelected;


public class ManageEmployeeController implements Initializable {


    @FXML
    private StackPane root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXTreeTableView<TableEmployee> treeTableView;

    @FXML
    private JFXComboBox<String> combo;


    @FXML
    private JFXTreeTableColumn<TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol, addressCol, phoneCol, socialSecurNumbCol,
            diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, nombreEFCol;

    static JFXDialog addUserDialog, editUserDialog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combo.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد", "فترة العمل", "الحالة العائلية", "لقب العزوبة", "عدد بنون", "عدد بنات");
        initializeTable();
    }

    @FXML
    void addUser(ActionEvent event) {
        errorLabel.setText("");
        AnchorPane addUserPane = null;
        try {
            addUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/addEmployeForm.fxml"));
        } catch (IOException ignored) {
        }
        addUserDialog = getSpecialDialog(addUserPane);
        addUserDialog.show();
    }

    @FXML
    void editUser(ActionEvent event) {
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

        employeeSelected = new Employe();
        employeeSelected.setId(Integer.parseInt(id));
        employeeSelected.setPrenom(firstnameCol.getCellData(index));
        employeeSelected.setNom(lastNameCol.getCellData(index));
        employeeSelected.setDateNaissance(java.sql.Date.valueOf(dateOfBirthCol.getCellData(index)));
        employeeSelected.setLieuNaissance(placeOfBirthCol.getCellData(index));
        employeeSelected.setAdresse(addressCol.getCellData(index));
        employeeSelected.setNumTelephone(phoneCol.getCellData(index));
        employeeSelected.setSocialSecurityNumber(socialSecurNumbCol.getCellData(index));
        employeeSelected.setDiplome(diplomeCol.getCellData(index));
        employeeSelected.setExperience(experienceCol.getCellData(index));
        employeeSelected.setItar(itarCol.getCellData(index));
        employeeSelected.setRenouvlement_de_contrat(contractRenCol.getCellData(index));
        employeeSelected.setFonction(jobCol.getCellData(index));
        employeeSelected.setRegimeScolaire(regimCol.getCellData(index));
        employeeSelected.setDate_debut(java.sql.Date.valueOf(dateFirstEmploCol.getCellData(index)));
        if (marierCol.getCellData(index).toLowerCase().equals("متزوج")) {
            employeeSelected.setStatuSocial(1);
            employeeSelected.setCelibacyTitle(nomCelebCol.getCellData(index));
            employeeSelected.setMaleChild(Integer.parseInt(nombreEMCol.getCellData(index)));
            employeeSelected.setFemaleChild(Integer.parseInt(nombreEFCol.getCellData(index)));
        } else employeeSelected.setStatuSocial(0);

        AnchorPane editUserPane = null;
        try {
            editUserPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/EditEmployeeForm.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        editUserDialog = getSpecialDialog(editUserPane);
        editUserDialog.show();

    }

    @FXML
    void removeUser(ActionEvent event) {
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
            int status = new EmployeDB().deleteEmployee(id);
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
    void updateTable() {
        errorLabel.setText("");
        ObservableList<TableEmployee> employes = FXCollections.observableArrayList();
        combo.setValue(null);

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabel.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(new TableEmployee(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                        employe.getLieuNaissance(), employe.getAdresse(), employe.getExperience(), employe.getNumTelephone(), employe.getSocialSecurityNumber(),
                        employe.getDiplome(), employe.getItar(), employe.getDate_debut(), employe.getFonction(), employe.getRenouvlement_de_contrat(), employe.getRegimeScolaire(), employe.estmarier(),
                        employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
            }
        }

        final TreeItem<TableEmployee> treeItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
        try {
            treeTableView.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }


    static class TableEmployee extends RecursiveTreeObject<TableEmployee> {
        StringProperty id, firstname, lastname, birthday, birthplace, job, experience;
        StringProperty addresse, phone, socialSN, diplom, itar, firstdaywor, renouvlementcotract;
        StringProperty regime;
        StringProperty marier;
        StringProperty nomCeleb;
        StringProperty nombreEM;
        StringProperty nombreEF;

        TableEmployee(int id, String firstname, String lastname, Date birthday, String birthplace, String addresse, String experience,
                      String phone, String socialSN, String diplom, String itar,
                      Date firstdaywor, String job, String renouvlementcotract, String regime, boolean marier, String nomCeleb,
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
            this.itar = new SimpleStringProperty(String.valueOf(itar));
            this.firstdaywor = new SimpleStringProperty(String.valueOf(firstdaywor));
            this.renouvlementcotract = new SimpleStringProperty(String.valueOf(renouvlementcotract));
            this.regime = new SimpleStringProperty(regime);
            this.marier = new SimpleStringProperty((marier) ? "متزوج" : "أعزب");
            this.nomCeleb = new SimpleStringProperty(String.valueOf(nomCeleb));
            this.nombreEM = new SimpleStringProperty(String.valueOf(nombreEM));
            this.nombreEF = new SimpleStringProperty(String.valueOf(nombreEF));

        }
    }

    private void initializeTable() {
        idCol = new JFXTreeTableColumn<>("رقم التسجيل");
        idCol.setPrefWidth(120);
        idCol.setCellValueFactory(param -> param.getValue().getValue().id);

        firstnameCol = new JFXTreeTableColumn<>("اللقب");
        firstnameCol.setPrefWidth(150);
        firstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCol = new JFXTreeTableColumn<>("الإسم");
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

        itarCol = new JFXTreeTableColumn<>("الإطار");
        itarCol.setPrefWidth(120);
        itarCol.setCellValueFactory(param -> param.getValue().getValue().itar);

        dateFirstEmploCol = new JFXTreeTableColumn<>("تاريخ أول تعيين");
        dateFirstEmploCol.setPrefWidth(120);
        dateFirstEmploCol.setCellValueFactory(param -> param.getValue().getValue().firstdaywor);

        experienceCol = new JFXTreeTableColumn<>("الخبرة");
        experienceCol.setPrefWidth(75);
        experienceCol.setCellValueFactory(param -> param.getValue().getValue().experience);

        contractRenCol = new JFXTreeTableColumn<>("حالة التعاقد");
        contractRenCol.setPrefWidth(120);
        contractRenCol.setCellValueFactory(param -> param.getValue().getValue().renouvlementcotract);

        regimCol = new JFXTreeTableColumn<>("فترة العمل");
        regimCol.setPrefWidth(120);
        regimCol.setCellValueFactory(param -> param.getValue().getValue().regime);

        marierCol = new JFXTreeTableColumn<>("الحالة العائلية");
        marierCol.setPrefWidth(100);
        marierCol.setCellValueFactory(param -> param.getValue().getValue().marier);

        nomCelebCol = new JFXTreeTableColumn<>("لقب العزوبة");
        nomCelebCol.setPrefWidth(150);
        nomCelebCol.setCellValueFactory(param -> param.getValue().getValue().nomCeleb);

        nombreEMCol = new JFXTreeTableColumn<>("عدد بنون");
        nombreEMCol.setPrefWidth(75);
        nombreEMCol.setCellValueFactory(param -> param.getValue().getValue().nombreEM);

        nombreEFCol = new JFXTreeTableColumn<>("عدد بنات");
        nombreEFCol.setPrefWidth(75);
        nombreEFCol.setCellValueFactory(param -> param.getValue().getValue().nombreEF);

        updateTable();

        searchField.textProperty().addListener(e -> filterSearchTable());
        combo.setOnAction(e -> filterSearchTable());

        treeTableView.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, socialSecurNumbCol, jobCol, diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, nombreEFCol);
        treeTableView.setShowRoot(false);
    }

    @FXML
    void printFile() {
        Text textArea = new Text();
        textArea.setText("Printing test!    Printing test!\nPrinting test!  Printing test!  Printing test!\nPrinting test!  Printing test!  Printing test!  Printing test!");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPrintDialog(null);
        Printer printer= job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        textArea.minWidth(pageLayout.getPrintableWidth());
        textArea.minHeight(pageLayout.getPrintableHeight());
        job.printPage(pageLayout,textArea);
        job.endJob();

    }

    private JFXDialog getSpecialDialog(AnchorPane content) {
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed((event) -> updateTable());
        return dialog;
    }

    private void filterSearchTable() {
        treeTableView.setPredicate(employee -> {
            switch (combo.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 1:
                    return employee.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 2:
                    return employee.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 3:
                    return employee.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 4:
                    return employee.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 5:
                    return employee.getValue().job.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 6:
                    return employee.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 7:
                    return employee.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 8:
                    return employee.getValue().socialSN.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 9:
                    return employee.getValue().diplom.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 10:
                    return employee.getValue().firstdaywor.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 11:
                    return employee.getValue().experience.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 12:
                    return employee.getValue().renouvlementcotract.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 13:
                    return employee.getValue().regime.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 14:
                    return employee.getValue().marier.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 15:
                    return employee.getValue().nomCeleb.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 16:
                    return employee.getValue().nombreEM.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                case 17:
                    return employee.getValue().nombreEF.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                default:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().lastname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().firstname.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().birthday.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().birthplace.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().job.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().addresse.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().phone.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().socialSN.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().diplom.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().firstdaywor.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().experience.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().renouvlementcotract.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().regime.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().marier.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().nomCeleb.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().nombreEM.getValue().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            employee.getValue().nombreEF.getValue().toLowerCase().contains(searchField.getText().toLowerCase());
            }
        });
    }
}
