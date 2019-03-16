package home.java;

import java.util.Date;


public class Enseignants extends Employe {

    private Regime regime;


    private Date date_debut;


    private int id;


    Regime getRegime() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.regime;
    }


    void setRegime(Regime value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.regime = value;
    }


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

}

