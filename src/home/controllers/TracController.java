package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.dbDir.*;
import home.java.ClasseModel;
import home.java.Eleve;
import home.java.Employe;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class TracController implements Initializable {

    static JFXDialog charityUserDialog;

    private ObservableList<String> typeChart =
            FXCollections.observableArrayList(
                    "بيانات المصاريف الكلية", "بيانات الدخل الكلي", "بيانات المخزن"
            );

    @FXML
    private StackPane root;
    @FXML
    Pane studentBox, employeeBox, fraisBox;

    @FXML
    private VBox studentPane;
    @FXML
    private AnchorPane choosePane, employeeAnchor, studentAnchor, fraisAnchor;
    @FXML
    private VBox fraisPane;

    @FXML
    private JFXTextField searchStudentField;
    @FXML
    private Label errorLabelEmploye, errorLabelStudent, errorLabelFrais;

    @FXML
    private HBox searchToolsBox;
    @FXML
    private JFXComboBox<String> comboStudentSearch;

    @FXML
    private JFXTreeTableView<ManageEmployeeController.TableEmployee> tableEmployeeTrac;

    @FXML
    private JFXTreeTableColumn<ManageEmployeeController.TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol, addressCol, phoneCol, socialSecurNumbCol,
            diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, remarqueCol, nombreEFCol;


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
    private JFXComboBox<String> comboClasse;
    @FXML
    private JFXTreeTableView<EleveController.TableEleve> tableStudentTrac;
    @FXML
    private JFXTreeTableColumn<EleveController.TableEleve, String> SidCol, SfirstnameCol, SlastNameCol, SClassCol, SdateOfBirthCol, SplaceOfBirthCol, SaddressCol, SphoneCol;
    @FXML
    private PieChart pieChartGenderStudent;
    @FXML
    private PieChart pieChartRegimeStudent;
    @FXML
    private PieChart pieChartSchoolYear;
    @FXML
    private PieChart pieChartFraisSortants;
    @FXML
    private PieChart pieChartFraisEntrants;
    @FXML
    private PieChart pieChartStock;
    @FXML
    private BarChart barChartFrais;
    @FXML
    private CategoryAxis XAxis;
    @FXML
    private NumberAxis YAxis;
    @FXML
    private Label Entrant, Sortant;
    @FXML
    private Label header1, header2, subheader1, subheader2, subheader3, subheader4;
    @FXML
    private Label subheader12, subheader22, subheader32, subheader42;

    @FXML
    private JFXComboBox<String> comboFrais;

    static JFXDialog detailChart;


    private EmployeDB employeDB;
    private EleveDB eleveDB;
    private fraisDB fraisDB;
    private StockDB stockDB;
    private List<ClasseModel> clsDB;

    @FXML
    void LesFrais() {
        double fraisEmploye = fraisDB.getFraisEmploye();
        double percentEmploye = fraisDB.countFraisEmploye();
        double fraisCharity = fraisDB.getFraisCharity();
        double percentCharity = fraisDB.countFraisCharity();
        double fraisEleve = fraisDB.getFraisEleve();
        double percentEleve = fraisDB.countFraisEleve();
        double fraisStock = fraisDB.getFraisStock();
        double percentStock = fraisDB.countFraisStock();
        double totalEat = stockDB.getStockTotal(1);
        double totalStuff = stockDB.getStockTotal(2);
        double totalOther = stockDB.getStockTotal(3);
        double percentEat = stockDB.percentStockTotal(1);
        double percentStuff = stockDB.percentStockTotal(2);
        double percentOther = stockDB.percentStockTotal(3);
        double TotalGain = fraisDB.getFraisEntrant();
        double TotalCost = fraisDB.getFraisSortant();

        Entrant.setText(String.valueOf(TotalGain));
        Sortant.setText(String.valueOf(TotalCost));
        switch (comboFrais.getSelectionModel().getSelectedItem()) {
            case "بيانات المخزن":
                initPieStock();
                header1.setText("• تكاليف المخزن:");
                subheader1.setText("مصاريف الطعام تمثل: ");
                subheader12.setText("(" + percentEat + "%)" + " " + totalEat);
                subheader2.setText("مصاريف الأدوات تمثل: ");
                subheader22.setText("(" + percentStuff + "%)" + " " + totalStuff);
                subheader3.setText("مصاريف الأخرى تمثل: ");
                subheader32.setText("(" + percentOther + "%)" + " " + totalOther);
                break;
            case "بيانات الدخل الكلي":
                initPieFraisEntrants();
                header1.setText("• تكاليف التلاميذ:");
                header2.setText("• هيبات:");
                subheader1.setText("تمثل: ");
                subheader12.setText("(" + percentEleve + "%)" + " " + fraisEleve);
                subheader4.setText("تمثل: ");
                subheader42.setText("(" + percentCharity + "%)" + " " + fraisCharity);
                break;
            case "بيانات المصاريف الكلية":
                initPieFraisSortants();
                header1.setText("• أجور العمال:");
                header2.setText("• مصاريف المخزن:");
                subheader1.setText("تمثل: ");
                subheader12.setText("(" + percentEmploye + "%)" + " " + fraisEmploye);
                subheader4.setText("تمثل: ");
                subheader42.setText("(" + percentStock + "%)" + " " + fraisStock);
                break;
        }
    }

    @FXML
    void btnFilterClasse() {
        String classe = comboClasse.getSelectionModel().getSelectedItem();
        ClasseModel selectedClasse = new ClasseModel();
        for (ClasseModel classeModel : clsDB) {
            if (classeModel.getClassNam().equals(classe)) {
                selectedClasse.setClassNam(classeModel.getClassNam());
                selectedClasse.setId(classeModel.getId());
                selectedClasse.setClassRom(classeModel.getClassRom());
                selectedClasse.setmaxNbrElev(classeModel.getmaxNbrElev());
                selectedClasse.setremarque(classeModel.getremarque());
            }
        }
        tableStudentTrac.setPredicate(eleve -> {
            return eleve.getValue().classroom.getValue().toLowerCase().equals(selectedClasse.getClassNam().toLowerCase());
        });

    }

    @FXML
    void btnBackward() {
        employePane.setVisible(false);
        studentPane.setVisible(false);
        fraisPane.setVisible(false);
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
    void showFrais() {

        fraisPane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(fraisPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        pieChartFraisSortants.setVisible(true);
        initPieFraisSortants();
        LesFrais();
    }

    @FXML
    void showStudent() {
        btnSearchToolsEmploye();

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

        remarqueCol = new JFXTreeTableColumn<>("ملاحضات");
        remarqueCol.setPrefWidth(200);
        remarqueCol.setCellValueFactory(param -> param.getValue().getValue().remarque);

        updateTable();

        searchEmployeField.textProperty().addListener(e -> filterSearchTable());
        comboEmployeSearchBy.setOnAction(e -> filterSearchTable());

        tableEmployeeTrac.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol, addressCol, phoneCol, socialSecurNumbCol, jobCol, diplomeCol, itarCol, dateFirstEmploCol, experienceCol, contractRenCol, regimCol, marierCol, nomCelebCol, nombreEMCol, nombreEFCol, remarqueCol);
        tableEmployeeTrac.setShowRoot(false);

    }

    @FXML
    void updateTable() {
        errorLabelEmploye.setText("");
        ObservableList<ManageEmployeeController.TableEmployee> employes = FXCollections.observableArrayList();
        ObservableList<EleveController.TableEleve> eleves = FXCollections.observableArrayList();
        comboEmployeSearchBy.setValue(null);
        comboStudentSearch.setValue(null);

        List<Employe> employeeDB = new EmployeDB().getEmployee();
        if (employeeDB == null) {
            errorLabelEmploye.setText("Connection Failed !");
        } else {
            for (Employe employe : employeeDB) {
                employes.add(new ManageEmployeeController.TableEmployee(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                        employe.getLieuNaissance(), employe.getAdresse(), employe.getExperience(), employe.getNumTelephone(), employe.getSocialSecurityNumber(),
                        employe.getDiplome(), employe.getItar(), employe.getDate_debut(), employe.getFonction(), employe.getClasse(), employe.getRenouvlement_de_contrat(), employe.getRegimeScolaire(),
                        employe.getRemarque(), employe.estmarier(),
                        employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
            }
        }
        List<Eleve> eleveDB = new EleveDB().getEleve();
        if (eleveDB == null) {
            errorLabelStudent.setText("Connection Failed !");
        } else {
            for (Eleve eleve : eleveDB) {
                eleves.add(new EleveController.TableEleve(eleve.getId(), eleve.getGender(), eleve.getNom(), eleve.getPrenom(), eleve.getClasse(), eleve.getDateNaissance(),
                        eleve.getLieuNaissance(), eleve.getAdresse(), eleve.getPhone(), eleve.getRemarque(), eleve.getAnneeScolaire(), eleve.getRegime(), eleve.getPrenomPere(),
                        eleve.getNomMere(), eleve.getPrenomMere(), eleve.getTravailPere(), eleve.getTravailMere(), eleve.getWakil(), eleve.getMaladie(), eleve.getTranches(), eleve.getMontantRestant()));
            }
        }

        final TreeItem<ManageEmployeeController.TableEmployee> treeItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
        final TreeItem<EleveController.TableEleve> treeItemEleve = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            tableEmployeeTrac.setRoot(treeItem);
            tableStudentTrac.setRoot(treeItemEleve);
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
    void updateStudentChart() {
        if (pieChartRegimeStudent.isVisible()) {
            pieChartRegimeStudent.setVisible(false);
            pieChartGenderStudent.setVisible(true);
            initChartGenderStudent();
            pieChartSchoolYear.setVisible(false);

        } else if (pieChartGenderStudent.isVisible()) {
            pieChartRegimeStudent.setVisible(false);
            pieChartGenderStudent.setVisible(false);
            pieChartSchoolYear.setVisible(true);
            initChartSchoolYear();
        } else if (pieChartSchoolYear.isVisible()) {
            pieChartRegimeStudent.setVisible(true);
            initChartRegimeStudent();
            pieChartGenderStudent.setVisible(false);
            pieChartSchoolYear.setVisible(false);
        }
        updateTable();
        comboClasse.getSelectionModel().select(null);
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


    private void changeViewFrais(String item) {

        if (item.equals("بيانات الدخل الكلي")) {
            pieChartFraisEntrants.setVisible(true);
            pieChartStock.setVisible(false);
            pieChartFraisSortants.setVisible(false);
            LesFrais();
            header2.setVisible(true);
            subheader2.setVisible(false);
            subheader22.setVisible(false);
            subheader3.setVisible(false);
            subheader32.setVisible(false);
            subheader4.setVisible(true);
            subheader42.setVisible(true);
        } else if (item.equals("بيانات المخزن")) {
            pieChartFraisEntrants.setVisible(false);
            pieChartFraisSortants.setVisible(false);
            pieChartStock.setVisible(true);
            LesFrais();
            header2.setVisible(false);
            subheader2.setVisible(true);
            subheader22.setVisible(true);
            subheader3.setVisible(true);
            subheader32.setVisible(true);
            subheader4.setVisible(false);
            subheader42.setVisible(false);

        } else if (item.equals("بيانات المصاريف الكلية")) {
            pieChartFraisSortants.setVisible(true);
            pieChartFraisEntrants.setVisible(false);
            pieChartStock.setVisible(false);
            LesFrais();
            header2.setVisible(true);
            subheader2.setVisible(false);
            subheader22.setVisible(false);
            subheader3.setVisible(false);
            subheader32.setVisible(false);
            subheader4.setVisible(true);
            subheader42.setVisible(true);
        }
    }


    private void initPieFraisSortants() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("أجور العمال", fraisDB.countFraisEmploye()));
        data.add(new PieChart.Data("مصاريف المخزن", fraisDB.countFraisStock()));

        pieChartFraisSortants.setData(data);
        pieChartFraisSortants.setTitle("المصاريف");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "% $1"), " ", d.getName())
                )
        );

    }

    private void initPieFraisEntrants() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("تكاليف التلاميذ", fraisDB.countFraisEleve()));
        data.add(new PieChart.Data("هيبات", fraisDB.countFraisCharity()));

        pieChartFraisEntrants.setData(data);
        pieChartFraisEntrants.setTitle("الدخل");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "%$1"), " ", d.getName())
                )
        );

    }

    private void initPieStock() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("طعام", stockDB.countStock(1)));
        data.add(new PieChart.Data("كتب و كراريس", stockDB.countStock(2)));
        data.add(new PieChart.Data("أخرى", stockDB.countStock(3)));

        pieChartStock.setData(data);
        pieChartStock.setTitle("تفاصيل المخزن");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat(d.getName(), " ", (d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " دج")
                )
        );

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

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );
    }

    private void initChartRegimeStudent() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("صباح", eleveDB.countRegimeEleve("صباح")));
        data.add(new PieChart.Data("مساء", eleveDB.countRegimeEleve("مساء")));
        data.add(new PieChart.Data("صباح+مساء", eleveDB.countRegimeEleve("صباح+مساء")));
        data.add(new PieChart.Data("صباح+نصف داخلي", eleveDB.countRegimeEleve("صباح+نصف داخلي")));
        data.add(new PieChart.Data("اليوم كامل", eleveDB.countRegimeEleve("اليوم كامل")));

        pieChartRegimeStudent.setData(data);
        pieChartRegimeStudent.setTitle("عدد التلاميذ في مختلف فترات الدراسة");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );
    }

    private void initChartGenderStudent() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("ذكر", eleveDB.countGenderEleve(1)));
        data.add(new PieChart.Data("أنثى", eleveDB.countGenderEleve(2)));

        pieChartGenderStudent.setData(data);
        pieChartGenderStudent.setTitle("عدد التلاميذ حسب الجنس");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );
    }

    private void initChartSchoolYear() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        data.add(new PieChart.Data("السنة الاولى", eleveDB.countSchoolYear(1)));
        data.add(new PieChart.Data("السنة الثانية", eleveDB.countSchoolYear(2)));

        pieChartSchoolYear.setData(data);
        pieChartSchoolYear.setTitle("عدد التلاميذ حسب السنة الدراسية");

        data.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXRippler rippler = new JFXRippler(studentBox);
        JFXRippler rippler1 = new JFXRippler(employeeBox);
        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        employeeAnchor.getChildren().add(rippler1);
        studentAnchor.getChildren().add(rippler);

        comboStudentSearch.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد", "مكان الملاد", "العنوان الشخصي", "الهاتف");

        comboEmployeSearchBy.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان الشخصي", "الهاتف",
                "رقم الضمان الإجتماعي", "الشهادات", "تاريخ أول تعيين", "الخبرة", "حالة التعاقد", "فترة العمل", "الحالة العائلية", "لقب العزوبة", "عدد بنون", "عدد بنات");


        clsDB = new ClasseDB().getClasse();
        ArrayList<String> classes = new ArrayList<>();
        for (ClasseModel classeModel : clsDB) {
            classes.add(classeModel.getClassNam());
        }
        comboClasse.getItems().addAll(classes);

        comboFrais.setItems(typeChart);
        comboFrais.getSelectionModel().selectFirst();
        comboFrais.setOnAction(event -> changeViewFrais(comboFrais.getSelectionModel().getSelectedItem()));


        employeDB = new EmployeDB();
        initializeTableEmploye();
        initPaiChart();

        eleveDB = new EleveDB();
        initializeTableStudent();
        initChartRegimeStudent();
        initChartGenderStudent();
        initChartSchoolYear();

        fraisDB = new fraisDB();
        initPieFraisSortants();
        initPieFraisEntrants();

        stockDB = new StockDB();
        initPieStock();

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
                case 18:
                    return employee.getValue().classe.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
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
                            employee.getValue().nombreEF.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase()) ||
                            employee.getValue().classe.getValue().toLowerCase().contains(searchEmployeField.getText().toLowerCase());
            }
        });
    }


    private void initializeTableStudent() {
        SidCol = new JFXTreeTableColumn<>("رقم التسجيل");
        SidCol.setPrefWidth(120);
        SidCol.setCellValueFactory(param -> param.getValue().getValue().id);

        SfirstnameCol = new JFXTreeTableColumn<>("الإسم");
        SfirstnameCol.setPrefWidth(150);
        SfirstnameCol.setCellValueFactory(param -> param.getValue().getValue().firstname);

        SlastNameCol = new JFXTreeTableColumn<>("اللقب");
        SlastNameCol.setPrefWidth(150);
        SlastNameCol.setCellValueFactory(param -> param.getValue().getValue().lastname);

        SClassCol = new JFXTreeTableColumn<>("القسم");
        SClassCol.setPrefWidth(75);
        SClassCol.setCellValueFactory(param -> param.getValue().getValue().classroom);

        SdateOfBirthCol = new JFXTreeTableColumn<>("تاريخ الملاد");
        SdateOfBirthCol.setPrefWidth(100);
        SdateOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthday);

        SplaceOfBirthCol = new JFXTreeTableColumn<>("مكان الملاد");
        SplaceOfBirthCol.setPrefWidth(100);
        SplaceOfBirthCol.setCellValueFactory(param -> param.getValue().getValue().birthplace);


        SaddressCol = new JFXTreeTableColumn<>("العنوان الشخصي");
        SaddressCol.setPrefWidth(120);
        SaddressCol.setCellValueFactory(param -> param.getValue().getValue().addresse);

        SphoneCol = new JFXTreeTableColumn<>("الهاتف");
        SphoneCol.setPrefWidth(75);
        SphoneCol.setCellValueFactory(param -> param.getValue().getValue().phone);


        updateTable();

        searchStudentField.textProperty().addListener(e -> FilterSearchTableStudent());
        comboStudentSearch.setOnAction(e -> FilterSearchTableStudent());

        tableStudentTrac.getColumns().addAll(SidCol, SfirstnameCol, SlastNameCol, SClassCol, SdateOfBirthCol, SplaceOfBirthCol, SaddressCol, SphoneCol);
        tableStudentTrac.setShowRoot(false);

    }

    private void FilterSearchTableStudent() {
        tableStudentTrac.setPredicate(eleve -> {
            switch (comboStudentSearch.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 1:
                    return eleve.getValue().lastname.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 2:
                    return eleve.getValue().firstname.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 3:
                    return eleve.getValue().classroom.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 4:
                    return eleve.getValue().birthday.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 5:
                    return eleve.getValue().birthplace.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 6:
                    return eleve.getValue().addresse.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                case 7:
                    return eleve.getValue().phone.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());
                default:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().lastname.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().firstname.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().classroom.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().birthday.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().birthplace.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().addresse.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase()) ||
                            eleve.getValue().phone.getValue().toLowerCase().contains(searchStudentField.getText().toLowerCase());

            }
        });
    }
}
