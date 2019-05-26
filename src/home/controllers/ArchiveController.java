package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.java.Eleve;
import home.java.Employe;
import home.controllers.ManageEmployeeController;
import home.controllers.EleveController;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArchiveController implements Initializable {

    @FXML
    public StackPane archiv;

    @FXML
    public JFXButton cree;

    @FXML
    public JFXButton serch;

    @FXML
    public Label errorLabelStudent1;


    private int anee=2019;
    private String nomes;

    ArrayList<String> asmah=new ArrayList<>();
    @FXML
    private StackPane root;

    @FXML
    private AnchorPane choosePane;

    @FXML
    private VBox ArchivHome,anees,anee1,anee2,anee3,anee4,anee5;

    @FXML
    private VBox studentPane;

    @FXML
    private Label errorLabelEmploye,errorLabelStudent,aneeselect;

    @FXML
    private JFXTextField searchStudentField,prenostudent1,namstudent1;

    @FXML
    private JFXDatePicker birthDate1;


    @FXML
    private JFXComboBox<?> comboStudentSearchBy;

    @FXML
    private HBox searchToolsBox,lesAnee;

    @FXML
    private JFXComboBox<?> comboStudentSectionFilter;

    @FXML
    private JFXTreeTableView<?> tableUserTrac;

    @FXML
    private PieChart pieChartEmploye;

    @FXML
    private PieChart pieChartEmployeRigime;

    @FXML
    private VBox employePane;

    @FXML
    private JFXTextField searchEmployer,searchEleve;

    @FXML
    private JFXComboBox<?> comboEmployeSearchBy;

    @FXML
    private JFXTreeTableView<?> tableEmployeTrac;

    @FXML
    private JFXTreeTableView<ManageEmployeeController.TableEmployee> tableemployer;

    @FXML // Cols of table
    private JFXTreeTableColumn<ManageEmployeeController.TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol;
    @FXML
    private JFXComboBox<String> comboEleve,comboEmployer;
    @FXML
    private PieChart pieChartStudent;

    @FXML
    private JFXListView<String> yearSelect;

    @FXML
    private PieChart pieChartRigimeStudent;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXTreeTableView<?> tableEleveTrac;
    @FXML
    private JFXTreeTableView<EleveController.TableEleve> tableEleve;



    @FXML // Cols of table
    public JFXTreeTableColumn<EleveController.TableEleve, String> idCole, firstnameCole,classRoomCol, lastNameCole,
            dateOfBirthCole, placeOfBirthCole, jobCole, addressCol,remarqueCol, phoneCol;
    public static JFXDialog addUserDialog, editUserDialog;



    public void initialize(URL url, ResourceBundle rb)  {


        for(int i=0 ;i<100;i++) {
           yearSelect.getItems().add(anee + "-" + (++anee));
       }


        comboEleve.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "العنوان ");

        comboEmployer.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة","العنوان ","الهاتف ");

        searchEleve.textProperty().addListener(e -> SearchTableEleve());
        comboEleve.setOnAction(e -> SearchTableEleve());

        searchEmployer.textProperty().addListener(e -> SearchTableEmployer());
        comboEmployer.setOnAction(e -> SearchTableEmployer());
  }
    @FXML
    void choseanee() {

        nomes= yearSelect.getSelectionModel().getSelectedItem();
        aneeselect.setText(nomes);
        employePane.setVisible(false);
        studentPane.setVisible(false);
        choosePane.setVisible(true);
        ArchivHome.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(choosePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    @FXML
    void creeAnee() {
        int az=2120;
        yearSelect.getItems().add(az + "-" + (++az));
        az++;

        }

    @FXML
    void backtoanee() {


    }

    @FXML
    void btnFilterStudent() {

    }

    @FXML
    void btnSearchToolsStudent() {

    }

    @FXML
    void btnViewStatisticStudent() {

    }

    @FXML
    void btnBackward() {
       /* tableEleve.getColumns().removeAll();
        tableemployer.getColumns().removeAll();
        tableEleve.getRoot().getChildren().remove();
       */
        employePane.setVisible(false);
        studentPane.setVisible(false);
        choosePane.setVisible(false);
        ArchivHome.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(choosePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        removetreetable();

    }

    @FXML
    void showEmploye() {
        employePane.setVisible(true);
        choosePane.setVisible(false);
        ArchivHome.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(employePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        initializeTable(nomes);


    }

    @FXML
    void showStudent() {

        btnSearchToolsEmploye();
        initializeTableEmploye();
        ArchivHome.setVisible(false);
        studentPane.setVisible(true);
        choosePane.setVisible(false);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(studentPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        initializeTableEleve(nomes);

    }

    private void initializeTableEmploye() {

    }

    private void btnSearchToolsEmploye() {
    }

    @FXML
    void updateTableEmploye() {

    }

    @FXML
    void updateTableEleve() {

    }
    @FXML
    void updateTableUser() {

    }

    @FXML
    void viewChartEmploye() {

    }

    public void searchAvanc(ActionEvent actionEvent) {
    }


    private void initializeTable(String nome) {
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
        updateTable(nome);



        //noinspection deprecation
        tableemployer.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol,  jobCol);
        tableemployer.setShowRoot(false);
        tableemployer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }



    private void initializeTableEleve(String nome) {
        idCole = new JFXTreeTableColumn<>("رقم التسجيل");
        idCole.setPrefWidth(120);
        idCole.setCellValueFactory(param -> param.getValue().getValue().id);

        firstnameCole = new JFXTreeTableColumn<>("الإسم");
        firstnameCole.setPrefWidth(150);
        firstnameCole.setCellValueFactory(param -> param.getValue().getValue().firstname);

        lastNameCole = new JFXTreeTableColumn<>("اللقب");
        lastNameCole.setPrefWidth(150);
        lastNameCole.setCellValueFactory(param -> param.getValue().getValue().lastname);

        classRoomCol = new JFXTreeTableColumn<>("القسم");
        classRoomCol.setPrefWidth(100);
        classRoomCol.setCellValueFactory(param -> param.getValue().getValue().classroom);

        dateOfBirthCole = new JFXTreeTableColumn<>("تاريخ الملاد");
        dateOfBirthCole.setPrefWidth(100);
        dateOfBirthCole.setCellValueFactory(param -> param.getValue().getValue().birthday);

        placeOfBirthCole = new JFXTreeTableColumn<>("مكان الملاد");
        placeOfBirthCole.setPrefWidth(100);
        placeOfBirthCole.setCellValueFactory(param -> param.getValue().getValue().birthplace);

        addressCol = new JFXTreeTableColumn<>("العنوان");
        addressCol.setPrefWidth(120);
        addressCol.setCellValueFactory(param -> param.getValue().getValue().addresse);

        phoneCol = new JFXTreeTableColumn<>("الهاتف");
        phoneCol.setPrefWidth(75);
        phoneCol.setCellValueFactory(param -> param.getValue().getValue().phone);

        remarqueCol = new JFXTreeTableColumn<>("ملاحظات");
        remarqueCol.setPrefWidth(150);
        remarqueCol.setCellValueFactory(param -> param.getValue().getValue().remarque);


        updateTable(nome);


        tableEleve.getColumns().addAll(idCole, firstnameCole, lastNameCole, classRoomCol, dateOfBirthCole, placeOfBirthCole, addressCol, phoneCol, remarqueCol);
        tableEleve.setShowRoot(false);
        tableEleve.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void updateTable(String nome) {
        ArrayList<Employe> archv = new ArrayList<>();
        ArrayList<Eleve> archvElv = new ArrayList<>();

        ObservableList<ManageEmployeeController.TableEmployee> employes = FXCollections.observableArrayList();
        ObservableList<EleveController.TableEleve> eleves =FXCollections.observableArrayList();
        try {
            File file = new File("c:\\Archive creche darelhadith\\"+nome+".txt");
            FileReader reader = new FileReader(file);
            BufferedReader li=new BufferedReader(reader);
            String line;
            int i=0;

             if((li.readLine()).equals("Eleve"))
             {
                while((line=li.readLine()).compareTo("lafin***")!=0){
                     Eleve E=new Eleve();


                       E.setId(Integer.valueOf(line));

                     String nom = li.readLine();
                     E.setNom(nom);

                     String pnom = li.readLine();
                     E.setPrenom(pnom);

                     String DateNaissance = li.readLine();
                     E.setDateNaissance(Date.valueOf(DateNaissance));

                     String LieuNaissance = li.readLine();
                     E.setLieuNaissance(LieuNaissance);

                     String classe = li.readLine();
                    E.setClasse(classe);

                     String nombreStuding = li.readLine();
                     E.setAnneeScolaire(nombreStuding);

                     String regim = li.readLine();
                     E.setRegime(regim);

                     String Adresse = li.readLine();
                     E.setAdresse(Adresse);

                     String NumTelephone = li.readLine();
                     E.setPhone(NumTelephone);

                     String namfather = li.readLine();
                     E.setPrenomPere(namfather);

                     String nammather = li.readLine();
                     E.setPrenomMere(nammather);

                     String lnammather = li.readLine();
                     E.setNomMere(lnammather);

                     String travaifather = li.readLine();
                     E.setTravailPere(travaifather);

                     String travaimere = li.readLine();
                     E.setTravailMere(travaimere);

                    String malad = li.readLine();
                    E.setMaladie(malad);

                    String remarq = li.readLine();
                    E.setRemarque(remarq);

                    archvElv.add(E);
                    String gg = li.readLine();

                 }
             }

            while ((line = li.readLine()) != null){
                Employe a=new Employe();

                a.setId(Integer.valueOf(line));
                //System.out.println(a.getId());

                String nom = li.readLine();
                a.setNom(nom);

                String pnom = li.readLine();
                a.setPrenom(pnom);

                String DateNaissance = li.readLine();
                a.setDateNaissance(Date.valueOf(DateNaissance));

                String LieuNaissance = li.readLine();
                a.setLieuNaissance(LieuNaissance);

                String Adresse = li.readLine();
                a.setAdresse(Adresse);

                String Experience = li.readLine();
                a.setExperience(Experience);

                String NumTelephone = li.readLine();
                a.setNumTelephone(NumTelephone);

                String SocialSecurityNumber = li.readLine();
                a.setSocialSecurityNumber(SocialSecurityNumber);

                String Diplome = li.readLine();
                a.setDiplome(Diplome);

                String Itar = li.readLine();
                a.setItar(Itar);

                String Date_debut = li.readLine();
                a.setDate_debut(Date.valueOf(Date_debut));

                String Fonction = li.readLine();
                a.setFonction(Fonction);

                String Renouvlement = li.readLine();
                a.setRenouvlement_de_contrat(Renouvlement);

                String Regime = li.readLine();
                a.setRegimeScolaire(Regime);


                String StatuSocial = li.readLine();
                a.setStatuSocial (Integer.valueOf(StatuSocial));

                String CelibacyTitle = li.readLine();
                a.setCelibacyTitle(CelibacyTitle);

                String MaleChild = li.readLine();
                a.setMaleChild(Integer.valueOf(MaleChild));

                String FemaleChild = li.readLine();
                a.setFemaleChild(Integer.valueOf(FemaleChild));
                archv.add(a);

                String p = li.readLine();

            }
            reader.close();


        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());

        }
        for (Employe employe : archv) {
            employes.add(new ManageEmployeeController.TableEmployee(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                    employe.getLieuNaissance(), employe.getAdresse(), employe.getExperience(), employe.getNumTelephone(), employe.getSocialSecurityNumber(),
                    employe.getDiplome(), employe.getItar(), employe.getDate_debut(), employe.getFonction(), employe.getRenouvlement_de_contrat(), employe.getRegimeScolaire(), employe.estmarier(),
                    employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
        }

            final TreeItem<ManageEmployeeController.TableEmployee> treItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
           tableemployer.setRoot(treItem);

        for (Eleve eleve : archvElv) {
            eleves.add(new EleveController.TableEleve(eleve.getId(),eleve.getGender(), eleve.getNom().toUpperCase(), eleve.getPrenom().toUpperCase(), eleve.getClasse(),
                    eleve.getDateNaissance(), eleve.getLieuNaissance(), eleve.getAdresse(), eleve.getPhone(), eleve.getRemarque(),eleve.getAnneeScolaire(),eleve.getRegime(),
                    eleve.getPrenomPere(),eleve.getNomMere(), eleve.getPrenomMere(),eleve.getTravailPere(),eleve.getTravailMere(),eleve.getWakil(),eleve.getMaladie()));
        }


        final TreeItem<EleveController.TableEleve> treeItem = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            tableEleve.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

}

    private void removetreetable(){
        tableemployer.getColumns().clear();
        tableEleve.getColumns().clear();
    }

    private void SearchTableEleve() {
        tableEleve.setPredicate(eleve -> {
            switch (comboEleve.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
                case 1:
                    return eleve.getValue().lastname.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
                case 2:
                    return eleve.getValue().firstname.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
                case 3:
                    return eleve.getValue().birthday.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
                case 4:
                    return eleve.getValue().birthplace.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
                case 5:
                    return eleve.getValue().addresse.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());

                default:
                    return eleve.getValue().id.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase()) ||
                            eleve.getValue().lastname.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase()) ||
                            eleve.getValue().firstname.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase()) ||
                            eleve.getValue().birthday.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase()) ||
                            eleve.getValue().birthplace.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase()) ||
                            eleve.getValue().addresse.getValue().toLowerCase().contains(searchEleve.getText().toLowerCase());
            }
        });
    }


    private void SearchTableEmployer() {
        tableemployer.setPredicate(employee -> {
            switch (comboEmployer.getSelectionModel().getSelectedIndex()) {
                case 0:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 1:
                    return employee.getValue().lastname.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 2:
                    return employee.getValue().firstname.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 3:
                    return employee.getValue().birthday.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 4:
                    return employee.getValue().birthplace.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 5:
                    return employee.getValue().job.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                case 6:
                    return employee.getValue().addresse.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase());
                default:
                    return employee.getValue().id.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().lastname.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().firstname.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().birthday.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().birthplace.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().job.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().addresse.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()) ||
                            employee.getValue().phone.getValue().toLowerCase().contains(searchEmployer.getText().toLowerCase()
                           );
            }
        });
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
}


