package home.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import home.java.CryptoUtils;
import home.java.Eleve;
import home.java.Employe;
import javafx.animation.FadeTransition;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.Notifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static home.controllers.ShowArchDataEmlyController.emplSelected;
import static home.controllers.ShowArchvDataController.eleveSelected;


public class ArchiveController implements Initializable {

    @FXML
    public StackPane archiv;

    @FXML
    public ScrollPane showdataelev;
    @FXML
    public JFXButton cree;



    @FXML
    public Label errorLabelStudent1;


    @FXML // Cols of table
    public JFXTreeTableColumn<TableEleve, String> idCole, firstnameCole, classRoomCol, lastNameCole,
            dateOfBirthCole, placeOfBirthCole, jobCole, addressCol, remarqueCol, phoneCol;
    private String nomes;

    @FXML
    private AnchorPane choosePane;
    private int anee = 2019;
    private int az = 2119;

    @FXML
    private VBox studentPane;
    @FXML
    private VBox ArchivHome;
    @FXML
    private Label errorLabelStudent, aneeselect;



    @FXML
    private VBox employePane;
    @FXML
    private HBox lesAnee;


    @FXML
    private JFXTreeTableView<TableEmployee> tableemployer;

    @FXML // Cols of table
    private JFXTreeTableColumn<TableEmployee, String> idCol, firstnameCol, lastNameCol,
            dateOfBirthCol, placeOfBirthCol, jobCol;
    @FXML
    private JFXTextField searchEmployer, searchEleve;

    @FXML
    private JFXListView<String> yearSelect;


    @FXML
    private JFXTreeTableView<TableEleve> tableEleve;
    @FXML
    private JFXComboBox<String> comboEleve, comboEmployer;
    public static JFXDialog addUserDialog;
    private ArrayList<Employe> archv;
    private ArrayList<Eleve> archvElv;
    private String Matin, Aprem, MatAprem, Demi, Complet;

    public void initialize(URL url, ResourceBundle rb) {


        for (int i = 0; i < 100; i++) {
            yearSelect.getItems().add(anee + "-" + (++anee));
        }


        comboEleve.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "العنوان ");

        comboEmployer.getItems().addAll("رقم التسجيل", "الإسم", "اللقب", "تاريخ الملاد",
                "مكان الملاد", "المهنة", "العنوان ", "الهاتف ");

        searchEleve.textProperty().addListener(e -> SearchTableEleve());
        comboEleve.setOnAction(e -> SearchTableEleve());

        searchEmployer.textProperty().addListener(e -> SearchTableEmployer());
        comboEmployer.setOnAction(e -> SearchTableEmployer());
    }

    @FXML
    void choseanee() {

        nomes = yearSelect.getSelectionModel().getSelectedItem();
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

        yearSelect.getItems().add(az + "-" + (++az));

    }
    @FXML
    void btnBackward() {
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
        tableemployer.getColumns().addAll(idCol, firstnameCol, lastNameCol, dateOfBirthCol, placeOfBirthCol, jobCol);
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
        archv = new ArrayList<>();
        archvElv = new ArrayList<>();
        String key = DigestUtils.shaHex("Bechlaghem Mohammed Sends His Regards!").substring(8);

        ObservableList<TableEmployee> employes = FXCollections.observableArrayList();
        ObservableList<TableEleve> eleves =FXCollections.observableArrayList();
        try {
            File file = new File(System.getenv("APPDATA")+"\\Archive creche darelhadith\\"+nome+"\\"+nome+".encrypted");
            File decryptedFile = new File(file.getParent()+"\\"+nome+".txt");
            CryptoUtils.decrypt(key,file,decryptedFile);
            System.out.println("Decryption working great!");
            FileReader reader = new FileReader(decryptedFile);
            BufferedReader li=new BufferedReader(reader);
            String line;

            if ((li.readLine()).equals("Eleve")) {
                while((line=li.readLine()).compareTo("lafin***")!=0){
                    Eleve E = new Eleve();


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
                    E.setAnneeScolaire(Integer.valueOf(nombreStuding));

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
            //System.out.println(li.readLine());
            if ((li.readLine()).equals("Employer")) {
                while ((line = li.readLine()).compareTo("lafinEmployer***") != 0) {
                    Employe a = new Employe();

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
                    a.setStatuSocial(Integer.valueOf(StatuSocial));

                    String CelibacyTitle = li.readLine();
                    a.setCelibacyTitle(CelibacyTitle);

                    String MaleChild = li.readLine();
                    a.setMaleChild(Integer.valueOf(MaleChild));

                    String FemaleChild = li.readLine();
                    a.setFemaleChild(Integer.valueOf(FemaleChild));
                    archv.add(a);

                    String p = li.readLine();

                }
            }
            if ((line = li.readLine()) != null) {
                Matin = line;

                MatAprem = li.readLine();

                Aprem = li.readLine();

                Demi = li.readLine();

                Complet = li.readLine();

            }
            reader.close();
            decryptedFile.delete();

        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());

        }
        for(int i=0;i<archv.size();i++){
            Employe employe=archv.get(i);
            employes.add( new TableEmployee(employe.getId(), employe.getNom().toUpperCase(), employe.getPrenom().toUpperCase(), employe.getDateNaissance(),
                    employe.getLieuNaissance(), employe.getAdresse(), employe.getExperience(), employe.getNumTelephone(), employe.getSocialSecurityNumber(),
                    employe.getDiplome(), employe.getItar(), employe.getDate_debut(), employe.getFonction(), employe.getRenouvlement_de_contrat(),
                    employe.getRemarque(), employe.estmarier(),
                    employe.getCelibacyTitle(), employe.getMaleChild(), employe.getFemaleChild()));
        }

        final TreeItem<TableEmployee> treItem = new RecursiveTreeItem<>(employes, RecursiveTreeObject::getChildren);
        tableemployer.setRoot(treItem);

        for(int i=0;i<archvElv.size();i++){
            Eleve eleve=archvElv.get(i);
            eleves.add(new TableEleve(eleve.getId(), eleve.getNom().toUpperCase(), eleve.getPrenom().toUpperCase(), eleve.getClasse(), eleve.getDateNaissance(),
                    eleve.getLieuNaissance(), eleve.getAdresse(), eleve.getPhone(), eleve.getRemarque()));;
        }


        final TreeItem<TableEleve> treeItem = new RecursiveTreeItem<>(eleves, RecursiveTreeObject::getChildren);
        try {
            tableEleve.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }

    }

    private void removetreetable() {
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
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        textArea.setScaleX(0.85);
        textArea.setScaleY(0.85);
        textArea.setTranslateX(50);
        textArea.setTranslateY(10);
        job.printPage(pageLayout, textArea);
        job.endJob();
        textArea.setScaleX(1);
        textArea.setScaleY(1);
        textArea.setTranslateX(0);
        textArea.setTranslateY(0);

    }

    @FXML
    public void showDataElev(ActionEvent actionEvent) throws IOException {
        int index = tableEleve.getSelectionModel().getSelectedIndex(); // selected index
        String id = idCole.getCellData(index);
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
        eleveSelected.setPrenom(firstnameCole.getCellData(index));
        eleveSelected.setNom(lastNameCole.getCellData(index));
        eleveSelected.setClasse(classRoomCol.getCellData(index));
        eleveSelected.setDateNaissance(Date.valueOf(dateOfBirthCole.getCellData(index)));
        eleveSelected.setLieuNaissance(placeOfBirthCole.getCellData(index));
        eleveSelected.setAdresse(addressCol.getCellData(index));
        eleveSelected.setPhone(phoneCol.getCellData(index));
        eleveSelected.setRemarque(remarqueCol.getCellData(index));
        List<Eleve> studentDB = archvElv;
        if (studentDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Eleve eleve : studentDB) {
                if (eleve.getId() == Integer.parseInt(id)) {
                    eleveSelected.setPrenomPere(eleve.getPrenomPere());
                    eleveSelected.setNomMere(eleve.getNomMere());
                    eleveSelected.setPrenomMere(eleve.getPrenomMere());
                    eleveSelected.setAnneeScolaire(eleve.getAnneeScolaire());
                }
            }

        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/home/resources/fxml/ShowArchvData.fxml"));
        AnchorPane rootLayout = loader.load();
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);


        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.showAndWait();


    }

    @FXML
    public void showDataEmply(ActionEvent actionEvent) throws IOException {
        int index = tableemployer.getSelectionModel().getSelectedIndex(); // selected index
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

        emplSelected = new Employe();
        emplSelected.setId(Integer.parseInt(id));
        emplSelected.setPrenom(firstnameCol.getCellData(index));
        emplSelected.setNom(lastNameCol.getCellData(index));
        emplSelected.setFonction(jobCol.getCellData(index));
        emplSelected.setDateNaissance(Date.valueOf(dateOfBirthCol.getCellData(index)));
        emplSelected.setLieuNaissance(placeOfBirthCol.getCellData(index));
        //emplSelected.setRemarque(remarqueCol.getCellData(index));
        List<Employe> emplyDB = archv;
        if (emplyDB == null) {
            System.out.println("Connection Failed !");
        } else {
            for (Employe employe : emplyDB) {
                if (employe.getId() == Integer.parseInt(id)) {
                    emplSelected.setAdresse(employe.getAdresse());
                    emplSelected.setRegimeScolaire(employe.getRegimeScolaire());
                    emplSelected.setExperience(employe.getExperience());
                    emplSelected.setDate_debut(employe.getDate_debut());
                }
            }

        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/home/resources/fxml/ShowArchDataEmly.fxml"));
        AnchorPane rootLayout = loader.load();
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);


        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    public void showdatafrais(ActionEvent actionEvent) {

        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("عرض التكاليف لهذه السنة\n\n");

        Text contentText = new Text("صباح  :" + Matin + "\n\n" + "مساء : " + Aprem + "\n\n" + "صباح+مساء : " + MatAprem + "\n\n" + " صباح+نصف داخلي  :" + Demi + "\n\n" + " كامل : " + Complet + "\n\n");
        headerText.setStyle("-fx-font-size: 20px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);


        JFXDialog dialog = new JFXDialog(archiv, content, JFXDialog.DialogTransition.CENTER);
        //dialog.set
        dialog.getStylesheets().add("/home/resources/css/main.css");
        dialog.show();
    }

    static class TableEmployee extends RecursiveTreeObject<TableEmployee> {
        StringProperty id, firstname, lastname, birthday, birthplace, job, experience;
        StringProperty addresse, phone, socialSN, diplom, itar, firstdaywor, renouvlementcotract;
        StringProperty remarque, marier, nomCeleb, nombreEM, nombreEF;

        TableEmployee(int id, String firstname, String lastname, java.util.Date birthday, String birthplace, String addresse, String experience,
                      String phone, String socialSN, String diplom, String itar,
                      java.util.Date firstdaywor, String job, String renouvlementcotract, String remarque, boolean marier, String nomCeleb,
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
            this.remarque = new SimpleStringProperty(String.valueOf(remarque));
            this.marier = new SimpleStringProperty((marier) ? "متزوج" : "أعزب");
            this.nomCeleb = new SimpleStringProperty(String.valueOf(nomCeleb));
            this.nombreEM = new SimpleStringProperty(String.valueOf(nombreEM));
            this.nombreEF = new SimpleStringProperty(String.valueOf(nombreEF));

        }
    }

    static class TableEleve extends RecursiveTreeObject<TableEleve> {
        StringProperty id, firstname, lastname, classroom, birthday, birthplace;
        StringProperty addresse, phone, remarque;


        TableEleve(int id, String firstname, String lastname, String classroom, java.util.Date birthday, String birthplace, String addresse, String phone,
                   String remarque) {

            this.id = new SimpleStringProperty(String.valueOf(id));
            this.firstname = new SimpleStringProperty(String.valueOf(firstname));
            this.lastname = new SimpleStringProperty(String.valueOf(lastname));
            this.classroom = new SimpleStringProperty(String.valueOf(classroom));
            this.birthday = new SimpleStringProperty(String.valueOf(birthday));
            this.birthplace = new SimpleStringProperty(String.valueOf(birthplace));
            this.addresse = new SimpleStringProperty(String.valueOf(addresse));
            this.phone = new SimpleStringProperty(String.valueOf(phone));
            this.remarque = new SimpleStringProperty(String.valueOf(remarque));


        }
    }
}



