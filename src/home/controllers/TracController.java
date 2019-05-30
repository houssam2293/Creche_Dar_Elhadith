package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TracController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane choosePane,employeeAnchor,studentAnchor;

    @FXML
    private VBox studentPane;

    @FXML
    private Label errorLabelEmploye,errorLabelStudent;

    @FXML
    private JFXTextField searchStudentField;

    @FXML
    private JFXComboBox<String> comboStudentSearchBy;

    @FXML
    private HBox searchToolsBox;

    @FXML
    private JFXComboBox<String> comboStudentSectionFilter;

    @FXML
    private JFXTreeTableView<ManageEmployeeController.TableEmployee> tableEmployeeTrac;

    @FXML
    private JFXTreeTableColumn<ManageEmployeeController.TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol, addressCol, phoneCol, socialSecurNumbCol,
            diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, nombreEFCol;


    @FXML
    private PieChart pieChartEmploye;

    @FXML
    private PieChart pieChartEmployeRigime;

    @FXML
    private VBox employePane;

    @FXML
    private JFXTextField searchEmployeField;

    @FXML
    private JFXComboBox<String> comboEmployeSearchBy;

    @FXML
    private JFXTreeTableView tableStudentTrac;

    @FXML
    private PieChart pieChartStudent;

    @FXML
    private PieChart pieChartRigimeStudent;

    @FXML
    Pane studentBox,employeeBox;

    static JFXDialog detailChart;

    private EmployeDB employeDB;


    @FXML
    void btnFilterStudent() {

    }


    @FXML
    void btnViewStatisticStudent() {

    }

    @FXML
    void btnBackward() {
        employePane.setVisible(false);
        studentPane.setVisible(false);
        choosePane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(choosePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void showEmploye() {

        employePane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(employePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    @FXML
    void showStudent() {
        btnSearchToolsEmploye();
        initializeTableEmploye();

        studentPane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(studentPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    private void initializeTableEmploye() {
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

        searchEmployeField.textProperty().addListener(e -> filterSearchTable());
        comboEmployeSearchBy.setOnAction(e -> filterSearchTable());

        tableEmployeeTrac.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, socialSecurNumbCol, jobCol, diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, nombreEFCol);
        tableEmployeeTrac.setShowRoot(false);

    }

    @FXML
    void updateTable() {
        errorLabelEmploye.setText("");
        ObservableList<ManageEmployeeController.TableEmployee> employes = FXCollections.observableArrayList();
        comboEmployeSearchBy.setValue(null);

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabelEmploye.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(new ManageEmployeeController.TableEmployee(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                        employe.getLieuNaissance(), employe.getAdresse(), employe.getExperience(), employe.getNumTelephone(), employe.getSocialSecurityNumber(),
                        employe.getDiplome(), employe.getItar(), employe.getDate_debut(), employe.getFonction(), employe.getRenouvlement_de_contrat(), employe.getRegimeScolaire(), employe.estmarier(),
                        employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
            }
        }

        final TreeItem<ManageEmployeeController.TableEmployee> treeItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
        try {
            tableEmployeeTrac.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }

    private void btnSearchToolsEmploye() {
    }

    @FXML
    void updateTableEmploye() {
        initPaiChart();
        updateTable();
    }

    @FXML
    void updateTableUser() {

    }

    @FXML
    void viewChartEmploye() {
        /*int index = tableEmployeeTrac.getSelectionModel().getSelectedIndex(); // selected index
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
        }*/

        try {
            AnchorPane loder = FXMLLoader.load(getClass().getResource("/fxml/viewStatisticForm.fxml"));
            detailChart = new JFXDialog(root, loder, JFXDialog.DialogTransition.CENTER);
            detailChart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            //System.out.println("Error Msg: " + ex.getMessage());
        }

    }

    private void initPaiChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("صباح", employeDB.countEmploye("صباح")));
        data.add(new PieChart.Data("مساء", employeDB.countEmploye("مساء")));
        data.add(new PieChart.Data("صباح+مساء", employeDB.countEmploye("صباح+مساء")));
        data.add(new PieChart.Data("صباح+نصف داخلي", employeDB.countEmploye("صباح+نصف داخلي")));
        data.add(new PieChart.Data("اليوم كامل", employeDB.countEmploye("اليوم كامل")));

        pieChartEmployeRigime.setData(data);
        pieChartEmployeRigime.setTitle("عدد العمال في مختلف فترات العمل");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXRippler rippler = new JFXRippler(studentBox);
        JFXRippler rippler1 = new JFXRippler(employeeBox);
        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        employeeAnchor.getChildren().add(rippler1);
        studentAnchor.getChildren().add(rippler);

        comboEmployeSearchBy.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد", "فترة العمل", "الحالة العائلية", "لقب العزوبة", "عدد بنون", "عدد بنات");


        employeDB = new EmployeDB();
        initializeTableEmploye();
        initPaiChart();

    }

    private void filterSearchTable() {
        tableEmployeeTrac.setPredicate(employee -> {
            switch (comboEmployeSearchBy.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 1:
                    return employee.getValue().lastname.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 2:
                    return employee.getValue().firstname.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 3:
                    return employee.getValue().birthday.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 4:
                    return employee.getValue().birthplace.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 5:
                    return employee.getValue().job.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 6:
                    return employee.getValue().addresse.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 7:
                    return employee.getValue().phone.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 8:
                    return employee.getValue().socialSN.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 9:
                    return employee.getValue().diplom.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 10:
                    return employee.getValue().firstdaywor.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 11:
                    return employee.getValue().experience.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 12:
                    return employee.getValue().renouvlementcotract.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 13:
                    return employee.getValue().regime.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 14:
                    return employee.getValue().marier.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 15:
                    return employee.getValue().nomCeleb.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 16:
                    return employee.getValue().nombreEM.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                case 17:
                    return employee.getValue().nombreEF.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
                default:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().lastname.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().firstname.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().birthday.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().birthplace.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().job.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().addresse.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().phone.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().socialSN.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().diplom.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().firstdaywor.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().experience.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().renouvlementcotract.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().regime.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().marier.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().nomCeleb.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().nombreEM.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().nombreEF.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
            }
        });
    }
}
