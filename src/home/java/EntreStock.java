package home.java;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.Date;

public class EntreStock extends RecursiveTreeObject<EntreStock> {
    private int id;
    private int typeProduit;
    private String nom;
    private int quantite;
    private double prix;
    private Date dateFab;
    private Date dateExp;
    private String fournisseur;
    private double prixTotale;

    public EntreStock(int id, int typeProduit, String nom, int quantite, double prix, Date dateFab, Date dateExp, String fournisseur, Double prixTotale) {
       this.id=id;
        this.typeProduit = typeProduit;
        this.nom=nom;
        this.quantite=quantite;
        this.prix=prix;
        this.dateFab=dateFab;
        this.dateExp=dateExp;
        this.fournisseur=fournisseur;
        this.prixTotale=prixTotale;
    }
    public EntreStock(){


    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        this.dateExp = dateExp;}

        public String getFournisseur() {
            return fournisseur;
        }

        public void setFournisseur(String fournisseur) {
            this.fournisseur = fournisseur;
        }


    public double getPrixTotale() {
        return prixTotale;
    }

    public void setPrixTotale(double prixTotale) {
        this.prixTotale = prixTotale;
    }

    public int getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(int typeProduit) {
        this.typeProduit = typeProduit;
    }

    public String getProdectName() {
        String name = null;
        if (typeProduit == 1) name = "طعام";
        else if (typeProduit == 2) name = "كتب و كراريس";
        else if (typeProduit == 3) name = "أخرى";

        return name;
    }

    public int getProdect(String item) {
        int name = -1;
        switch (item) {
            case "طعام":
                name = 1;
                break;
            case "كتب و كراريس":
                name = 2;
                break;
            case "أخرى":
                name = 3;
                break;
        }
        return name;
    }
}
