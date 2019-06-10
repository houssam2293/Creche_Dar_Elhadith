package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.dbDir.ClasseDB;
import home.java.ClasseModel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddClassController {
    @FXML
    private VBox root;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField ClassNam;
    @FXML
    private JFXTextField ClassRom;

    @FXML
    private JFXTextField maxNbrElev;
    @FXML
    private JFXTextField remarque;


    @FXML
    void btnAdd() {

        ClasseModel cls = new ClasseModel();
        cls.setId(Integer.valueOf(id.getText()));
        cls.setClassNam(ClassNam.getText().trim().toLowerCase());
        cls.setClassRom(ClassRom.getText().trim().toLowerCase());
        cls.setmaxNbrElev(Integer.valueOf(maxNbrElev.getText()));
        cls.setremarque(remarque.getText().trim().toLowerCase());

        int status = new ClasseDB().addClasse(cls);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error ClasseModel!");
                Notifications.create()
                        .title("القسم موجودظ                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/warn.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();
                break;
            case 0:
                System.out.println("Unknown Error failed to add ClasseModel");
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                ClassController.addUserDialog.close();
        }

    }

    @FXML
    void btnClear() {
        id.setText(null);
        ClassNam.setText(null);
        ClassRom.setText(null);
        maxNbrElev.setText(null);
        remarque.setText(null);
    }

    @FXML
    void btnClose() {
        ClassController.addUserDialog.close();

    }


    public void initialize(URL location, ResourceBundle resources) {


        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)) {
                btnAdd();
            }
        });

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(ESCAPE)) {
                btnClose();
            }
        });


    }
}
