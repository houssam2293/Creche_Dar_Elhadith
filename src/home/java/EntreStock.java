package home.java;

import java.util.Date;

public class EntreStock {
    private String id;
    private String nom;
    private int quantite;
    private double prix;
    private Date dateFab;
    private Date dateExp;

    public EntreStock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateFab() {
        return dateFab;
    }

    public void setDateFab(Date dateFab) {
        this.dateFab = dateFab;
    }

    public Date getDateExp() {
        return dateExp;
    }

    public void setDateExp(Date dateExp) {
        this.dateExp = dateExp;
    }
}
