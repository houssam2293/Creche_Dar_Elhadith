package home.java;

import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalTime;


public class PointageModel {
    private int id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private JFXTimePicker tempEnt;
    private CheckBox remark;
    private TextField remarkk;
    private Label label;


    public PointageModel(int id, String fName, String lName, String remarque, String value) {
        this.id = id;
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.tempEnt = new JFXTimePicker();
        this.tempEnt.setValue(LocalTime.now());
        this.remark = new CheckBox();
        this.remarkk = new TextField();
        this.remarkk.promptTextProperty().setValue("لا توجد ملاحظات");

    }

    public PointageModel(int id, String fName, String lName, String remarque) {
        this.id = id;
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.tempEnt = new JFXTimePicker();
        this.tempEnt.setValue(LocalTime.now());
        this.remark = new CheckBox();
        this.remarkk = new TextField(remarque);
        this.label = new Label();
        this.label.setText(remarque);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String fName) {
        lastName.set(fName);
    }

    public JFXTimePicker getTempEnt() {
        return tempEnt;
    }

    public void setTempEnt(JFXTimePicker remark) {
        this.tempEnt = remark;
    }


    public TextField getRemarkk() {
        return remarkk;
    }

    public void setRemarkk(TextField remark) {
        this.remarkk = remark;
    }


    public CheckBox getRemark() {
        return remark;
    }

    public void setRemark(CheckBox remark) {
        this.remark = remark;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }



}