package home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import home.java.FileVisitorImpl;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private HBox parent;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private VBox sidebar;

    @FXML
    private VBox homePane;
    @FXML
    private VBox calBox, tracBox, manageStudentBox, pointBox, manageEmployeBox;

    private AnchorPane calendarPane, settingsPane, images;
    private StackPane tracPane, stock, manageStudentPane, pointag, manageEmployePane, archiv;
    @FXML
    private StackPane rightPane, classePane;
    @FXML
    private Pane paneSlider;

    @FXML
    private HBox boxesPane, boxHome, boxStudent, boxClasse, boxTrac, boxImages, boxCalandar, boxPoint, boxEmploye, boxArchive, boxStock, boxSettings, boxAbout;
    @FXML
    private FontAwesomeIconView iconHome, iconAccount;
    @FXML
    private MaterialDesignIconView iconAbout, iconPoint, iconClasse, iconStudent, iconStock, iconArchive;
    @FXML
    private MaterialIconView iconImages;
    @FXML
    private Icons525View iconSettings;
    @FXML
    private OctIconView iconTrac, iconCalandar;

    public static JFXDialog aboutDialog;


    @FXML
    private ImageView imgSlider;
    @FXML
    private Label dateLabel;

    private final byte NUMBER_IMAGE_SLIDER = 3;
    private int counter = 1;

    public MainController() {
    }

    /*---------------------------------------------------------*/


    @FXML
    public void expandSidebar() {
        sidebar.setPrefWidth((sidebar.getWidth() == 50) ? 200 : 50);
    }

    @FXML
    public void homeClicked() {
        styleBox(0);
        homePane.setVisible(true);
        sidebar.setPrefWidth(50);
    }

    @FXML
    public void calndarClicked() {
        calBoxClicked();
    }

    @FXML
    public void traceabilityClicked() {
        styleBox(2);
        setNode(tracPane);
    }

    @FXML
    public void studentClicked() {
        styleBox(1);
        setNode(manageStudentPane);
    }

    @FXML
    void studentBoxClicked(MouseEvent event) {
        studentClicked();
    }

    @FXML
    public void calBoxClicked() {
        styleBox(3);
        setNode(calendarPane);
    }

    @FXML
    public void EmployeeClicked() {
        styleBox(4);
        setNode(manageEmployePane);
    }

    @FXML
    void ArchiveClicked(MouseEvent event) {
        styleBox(10);
        setNode(archiv);
    }

    @FXML
    public void pointClicked() {
        pointageClicked();
    }

    @FXML
    public void settingsClicked() {
        styleBox(5);
        setNode(settingsPane);
    }

    @FXML
    public void logoutClicked() {
        Stage stage;
        Parent root = null;
        //get reference - stage
        stage = (Stage) holderPane.getScene().getWindow();
        stage.close();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/home/resources/fxml/login.fxml"));
        } catch (IOException ignored) {
        }

        //create a new scene with root and set the stage
        assert root != null;
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("مؤسسة دار الحديث");
        stage.getIcons().add(new Image("/home/resources/images/logo.png"));
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    public void imagesClicked() {
        setNode(images);
        styleBox(11);
    }


    @FXML
    public void aboutClicked() {
        styleBox(8);

        if (aboutDialog.isVisible()) {
            aboutDialog.close();
        } else
            aboutDialog.show();
    }


    @FXML
    public void stockBoxClicked() {
        setNode(stock);
        styleBox(9);
    }

    @FXML
    void classeClicked(MouseEvent event) {
        styleBox(6);
        setNode(classePane);
    }


    @FXML
    public void pointageClicked() {
        styleBox(7);
        setNode(pointag);
    }

    private void payCheck() {
        String dateEmp = new SimpleDateFormat("dd").format(new Date());
        if (dateEmp.compareTo("25") == 0) {
            JFXDialogLayout content = new JFXDialogLayout();
            Text headerText = new Text("دفع أجور العمال\n\n");
            Text contentText = new Text("يرجى دفع أجور العمال لهذا الشهر \n\n إلى إدارة العمال؟");
            headerText.setStyle("-fx-font-size: 19px");
            contentText.setStyle("-fx-font-size: 18px");

            content.setHeading(headerText);
            content.setBody(contentText);

            JFXDialog dialog = new JFXDialog(rightPane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton btnOk = new JFXButton("نعم");
            btnOk.setOnAction(e -> {
                dialog.close();
                EmployeeClicked();
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

        String dateTarifs = new SimpleDateFormat("dd-MM").format(new Date());
        if (dateTarifs.compareTo("01-09") == 0) {
            JFXDialogLayout content = new JFXDialogLayout();
            Text headerText = new Text("تحديد أسعار التسجيل\n\n");
            Text contentText = new Text("تحديد أسعار التسجيل لهذه السنة \n\n إلى الإعدادات؟");
            headerText.setStyle("-fx-font-size: 19px");
            contentText.setStyle("-fx-font-size: 18px");

            content.setHeading(headerText);
            content.setBody(contentText);

            JFXDialog dialog = new JFXDialog(rightPane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton btnOk = new JFXButton("نعم");
            btnOk.setOnAction(e -> {
                dialog.close();
                settingsClicked();
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


    }

    private void sliderAutoChangePictures() {
        // Make auto change the slider in duration

        Timeline sliderTimer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setNode(imgSlider);
            ft.setDuration(new Duration(5000));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(0);
            ft.setAutoReverse(true);
            ft.play();
            imgSlider.setImage(new Image("/home/resources/images/slider/" + counter + ".png"));
            if (++counter > NUMBER_IMAGE_SLIDER) {
                counter = 1;
            }
        }),
                new KeyFrame(Duration.seconds(4))
        );
        sliderTimer.setCycleCount(Animation.INDEFINITE);
        sliderTimer.play();

        // initialize Clock Showing in home
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy      HH:mm:ss");
            Date date = new Date();
            dateLabel.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


    }

    private void checkDataSaved() {
        String date = new SimpleDateFormat("dd").format(new Date());
        if (date.compareTo("01") == 0) {
            JFXDialogLayout content = new JFXDialogLayout();
            Text headerText = new Text("عملية حفظ البيانات \n\n");
            Text contentText = new Text("يرجى حفظ ملف قاعدة البيانات على فلاشة \n\n(في حالة تلف أو ضياع البيانات يمكنك استرجاعها من هذا الملف )");
            headerText.setStyle("-fx-font-size: 19px");
            contentText.setStyle("-fx-font-size: 18px");

            content.setHeading(headerText);
            content.setBody(contentText);

            JFXDialog dialog = new JFXDialog(rightPane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton btnOk = new JFXButton("نعم");
            btnOk.setOnAction(e -> {
                dialog.close();
                saveData();
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
    }

    private void initDir() {
        String year;
        if (Calendar.getInstance().get(Calendar.MONTH) > 8) {
            year = Calendar.getInstance().get(Calendar.YEAR) + "-" + Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
        } else {
            year = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1) + "-" + Calendar.getInstance().get(Calendar.YEAR);
        }


        File dir = new File(System.getenv("APPDATA") + "\\Archive creche darelhadith\\" + year);
        if (dir.exists()) {
            System.out.println("Dir exist!");
        } else {
            dir.mkdir();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        styleBox(0);
        initDir();
        checkDataSaved();
        payCheck();

        imgSlider.fitWidthProperty().bind(holderPane.widthProperty());
        imgSlider.fitHeightProperty().bind(paneSlider.heightProperty());


        JFXRippler rippler = new JFXRippler(calBox);
        JFXRippler rippler1 = new JFXRippler(manageEmployeBox);
        JFXRippler rippler2 = new JFXRippler(manageStudentBox);
        JFXRippler rippler3 = new JFXRippler(tracBox);
        JFXRippler rippler4 = new JFXRippler(pointBox);

        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler2.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler3.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler4.setMaskType(JFXRippler.RipplerMask.RECT);
        boxesPane.getChildren().addAll(rippler, rippler1, rippler2, rippler3, rippler4);
        //sliderAutoChangePictures();
        holderPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                ((Stage) holderPane.getScene().getWindow()).setFullScreen(true);
            }
        });

        try {
            settingsPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/settings.fxml"));
            manageEmployePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/manageEmployee.fxml"));
            tracPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/trac.fxml"));
            calendarPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/calendar.fxml"));
            manageStudentPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/eleve.fxml"));
            archiv = FXMLLoader.load(getClass().getResource("/home/resources/fxml/archive.fxml"));
            stock = FXMLLoader.load(getClass().getResource("/home/resources/fxml/stock.fxml"));
            pointag = FXMLLoader.load(getClass().getResource("/fxml/pointage.fxml"));
            images = FXMLLoader.load(getClass().getResource("/home/resources/fxml/traitmentImage.fxml"));
            classePane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/classe.fxml"));
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/home/resources/fxml/about.fxml"));
            aboutDialog = new JFXDialog(rightPane, aboutPane, JFXDialog.DialogTransition.TOP);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setNode(Node node) {
        homePane.setVisible(false);
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
        sidebar.setPrefWidth(50);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void styleBox(int index) {
        iconHome.setFill(Paint.valueOf("#4a4949"));
        iconStudent.setFill(Paint.valueOf("#4a4949"));
        iconTrac.setFill(Paint.valueOf("#4a4949"));
        iconPoint.setFill(Paint.valueOf("#4a4949"));
        iconCalandar.setFill(Paint.valueOf("#4a4949"));
        iconAccount.setFill(Paint.valueOf("#4a4949"));
        iconSettings.setFill(Paint.valueOf("#4a4949"));
        iconArchive.setFill(Paint.valueOf("#4a4949"));
        iconClasse.setFill(Paint.valueOf("#4a4949"));
        iconAbout.setFill(Paint.valueOf("#4a4949"));
        iconStock.setFill(Paint.valueOf("#4a4949"));
        iconImages.setFill(Paint.valueOf("#4a4949"));

        boxHome.setStyle("-fx-border: 0");
        boxStudent.setStyle("-fx-border: 0");
        boxTrac.setStyle("-fx-border: 0");
        boxPoint.setStyle("-fx-border: 0");
        boxCalandar.setStyle("-fx-border: 0");
        boxEmploye.setStyle("-fx-border: 0");
        boxArchive.setStyle("-fx-border: 0");
        boxClasse.setStyle("-fx-border: 0");
        boxSettings.setStyle("-fx-border: 0");
        boxAbout.setStyle("-fx-border: 0");
        boxStock.setStyle("-fx-border: 0");
        boxImages.setStyle("-fx-border: 0");

        switch (index) {
            case 0:
                boxHome.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconHome.setFill(Paint.valueOf("#2196f3"));
                break;
            case 1:
                boxStudent.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconStudent.setFill(Paint.valueOf("#2196f3"));
                break;
            case 2:
                boxTrac.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconTrac.setFill(Paint.valueOf("#2196f3"));
                break;
            case 3:
                boxCalandar.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconCalandar.setFill(Paint.valueOf("#2196f3"));
                break;
            case 4:
                boxEmploye.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconAccount.setFill(Paint.valueOf("#2196f3"));
                break;
            case 5:
                boxSettings.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconSettings.setFill(Paint.valueOf("#2196f3"));
                break;
            case 6:
                boxClasse.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconClasse.setFill(Paint.valueOf("#2196f3"));
                break;
            case 7:
                boxPoint.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconPoint.setFill(Paint.valueOf("#2196f3"));
                break;
            case 8:
                boxAbout.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconAbout.setFill(Paint.valueOf("#2196f3"));
                break;
            case 9:
                boxStock.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconStock.setFill(Paint.valueOf("#2196f3"));
                break;
            case 10:
                boxArchive.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconArchive.setFill(Paint.valueOf("#2196f3"));
                break;
            case 11:
                boxImages.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconImages.setFill(Paint.valueOf("#2196f3"));
                break;

        }
    }

    private void saveData() {
        String path = null;
        Stage stg = new Stage();
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(stg);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        if (file != null) {
            try {

                path = file.getAbsolutePath();
                path = path.replace('\\', '/');
                path = path + "_" + date + ".sql";

            } catch (Exception e) {
                e.printStackTrace();
            }

            Process p = null;
            try {
                Path startingDir = Paths.get("C:\\");
                String fileName = "mysqldump.exe";
                FileVisitorImpl visitor = new FileVisitorImpl();
                visitor.setStartDir(startingDir);
                visitor.setFileName(fileName);
                Files.walkFileTree(startingDir, visitor);
                String filePathe = visitor.getFilePath();
                System.out.println("mysqldump.exe reside in : " + filePathe);
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec(filePathe + " -uroot -proot --add-drop-database -B creche_dar_elhadith -r" + path);

                int processComplete = p.waitFor();
                if (processComplete == 0) {
                    System.out.println("Backup Created Succuss");
                } else {
                    System.out.println("Can't Create backup");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
