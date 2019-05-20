package home.java;

import java.time.LocalTime;
import java.util.Date;

import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.TextField;



public class Pointage {

    private int id;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty type;
    private JFXTimePicker tempEnt;
    private JFXTimePicker tempSor;
    private TextField remarkk;
    private CheckBox confirm;



    public Pointage(int id, String fullName, String type,String remark, String tempEnt, String tempSor) {
        this.id = id;
        this.fullName = new SimpleStringProperty(fullName);
        this.type = new SimpleStringProperty(type);
        this.tempEnt = new JFXTimePicker();
        this.tempEnt.setValue(LocalTime.now());
        this.tempSor = new JFXTimePicker();
        this.tempSor.setValue(LocalTime.of(16,30));
        this.remarkk = new TextField(remark);
        this.confirm = new CheckBox();
        confirm.setSelected(true);

    }

    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public String getfullNam() {
        return fullName.get();
    }
    public void setfullNam(String fName) {
        fullName.set(fName);
    }

    public String gettype() {
        return type.get();
    }
    public void setType(String fName) {
        type.set(fName);
    }


    public JFXTimePicker getTempEnt() {
        return tempEnt;
    }
    public void setTempEnt(JFXTimePicker remark) {
        this.tempEnt = remark;
    }

    public JFXTimePicker getTempSor() {
        return tempSor;
    }
    public void setTempSor(JFXTimePicker remark) {
        this.tempSor = remark;
    }

    public TextField getRemarkk() {
        return remarkk;
    }
    public void setRemarkk(TextField remark) {
        this.remarkk = remark;
    }

    public CheckBox getConfirm(){return confirm; }
    public void setConfirm(CheckBox confirm){this.confirm= confirm; }
}
