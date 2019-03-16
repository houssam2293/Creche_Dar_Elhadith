package home.java;

import java.util.Date;


public class Personnel extends Employe {
    private int id;


    private Date date_debut;


    private String fonction;


    int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }


    void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }

    Date getDate_debut() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.date_debut;
    }


    void setDate_debut(Date value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.date_debut = value;
    }


    String getFonction() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.fonction;
    }


    void setFonction(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.fonction = value;
    }

}