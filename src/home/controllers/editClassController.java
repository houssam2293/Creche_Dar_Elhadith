package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.dbDir.ClasseDB;
import home.java.ClasseModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class editClassController implements Initializable {

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

    static ClasseModel classSelected;

    public void btnEdit(ActionEvent actionEvent) {
        ClasseModel cls = new ClasseModel();
        cls.setId(Integer.valueOf(id.getText()));
        cls.setClassNam(ClassNam.getText().trim().toLowerCase());
        cls.setClassRom(ClassRom.getText().trim().toLowerCase());
        cls.setmaxNbrElev(Integer.valueOf(maxNbrElev.getText()));
        cls.setremarque(remarque.getText().trim().toLowerCase());


        int status = new ClasseDB().editClasse(cls);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Employer does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Employer");
                break;
            case 1:
                Notifications.create()
                        .title("تمت الإضافة بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();

                ClassController.editUserDialog.close();
        }

    }



    public void btnClos(ActionEvent actionEvent) {
        ClassController.editUserDialog.close();
    }

    public void btnClose(MouseEvent mouseEvent) {
        ClassController.editUserDialog.close();
    }
    public void initialize(URL location, ResourceBundle resources) {

        id.setText(String.valueOf(classSelected.getId()));
        ClassNam.setText(classSelected.getClassNam());
        ClassRom.setText(classSelected.getClassRom());
        maxNbrElev.setText(String.valueOf(classSelected.getmaxNbrElev()));
        remarque.setText(classSelected.getremarque());

    }
}
