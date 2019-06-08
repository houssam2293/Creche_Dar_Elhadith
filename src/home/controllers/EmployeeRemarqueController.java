package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.dbDir.EmployeDB;
import home.java.Employe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeRemarqueController implements Initializable {

    static Employe notedEmployee;
    @FXML
    private VBox root;
    @FXML
    private JFXTextField remarque;
    @FXML
    private Label employeeName;

    @FXML
    void btnCancel() {
        ManageEmployeeController.notesEmployeeDialog.close();
    }

    @FXML
    void btnOk() {
        Employe employe = new Employe();
        employe.setId(notedEmployee.getId());
        employe.setRemarque(remarque.getText());

        int status = new EmployeDB().editNote(employe);
        switch (status) {
            case -1:
                System.out.println("Error connecting to DB!");
                break;
            case 2:
                System.out.println("Error Eleve does not exist!");
                break;
            case 0:
                System.out.println("Unknown Error failed to add Eleve");
                break;
            case 1:
                Notifications.create()
                        .title("تمت التحديث بنجاح                                   ")
                        .graphic(new ImageView(new Image("/home/resources/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle()
                        .show();
                btnCancel();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeName.setText(notedEmployee.getNom() + " " + notedEmployee.getPrenom());
        remarque.setText(notedEmployee.getRemarque());
    }
}
