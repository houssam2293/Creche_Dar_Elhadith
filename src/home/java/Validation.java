package home.java;

import com.jfoenix.controls.JFXTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean arabValid(JFXTextField textfield) {
        String regex = "^[\\u0621-\\u064A ]+$";//only arabic lettres ok
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textfield.getText());
        return matcher.matches();
    }
    public boolean frenshValid(JFXTextField textfield) {
        String regex = "^[[a-zA-Z ]+('[a-zA-Z]+)+?$]+$";//only frensh lettres ok
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textfield.getText());
        return matcher.matches();
    }
    public boolean phoneValid(JFXTextField textfield) {
        Pattern patter = Pattern.compile("^[05/06/07/0][0-9]{9}"); // only number starting with 05/06/07/0 and lenght of 9 ok
        Matcher matcher = patter.matcher(textfield.getText());
        return( matcher.find() && matcher.group().equals(textfield.getText()));

    }
    public boolean isNumber(JFXTextField textfield) {
        Pattern patter = Pattern.compile("^[0-9]+('[0-9]+)?$"); // only number
        Matcher matcher = patter.matcher(textfield.getText());
        return matcher.matches();

    }
    public boolean alphanumValid(JFXTextField textfield) {
        Pattern patter = Pattern.compile("^[a-zA-Z0-9-]+('[a-zA-Z0-9]+)?$"); // only alphanum
        Matcher matcher = patter.matcher(textfield.getText());
        return matcher.matches();
    }

    public boolean isDouble(JFXTextField textfield) {
        Pattern patter = Pattern.compile("^[0-9.]+('[0-9]+)?$"); // only number
        Matcher matcher = patter.matcher(textfield.getText());
        return matcher.matches();

    }

}
