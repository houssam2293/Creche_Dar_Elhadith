package home.java;

public class Salle {
    private int id;


    private String libelle;


    private int capacite;

    public Salle(int id, String libelle, int capacite) {

        this.id = id;
        this.libelle = libelle;
        this.capacite = capacite;
    }
    public Salle() {}


    public int getId() {
        return this.id;
    }


    public void setId(int value) {
        this.id = value;
    }


    public String getLibelle() {
        return this.libelle;
    }


    public void setLibelle(String value) {
        this.libelle = value;
    }


    public int getCapacite() {
        return this.capacite;
    }


    public void setCapacite(int value) {
        this.capacite = value;
    }
}
