package home.java;


import java.util.Date;


public class Eleve {

    private String nom;
    private String preNom;
    private Date dateNaissance;
    private int numeroInscription;
    private String classe;
    private String anneeScolaire;
    private Regime regimeScolaire;
    private String adresse;
    private String numeroTelephone;
    private String travailPere;
    private String travailMere;
    private String bonDeSante;
    private String note;


    public Eleve(String nom, String preNom,Date dateNaissance,int numeroInscription,String classe,String anneeScolaire,
                 Regime regimeScolaire,String adresse,String numeroTelephone,String travailPere,String travailMere,
                 String bonDeSante,String note) {
        this.nom=nom;
        this.preNom=preNom;
        this.dateNaissance=dateNaissance;
        this.numeroInscription=numeroInscription;
        this.classe=classe;
        this.anneeScolaire=anneeScolaire;
        this.regimeScolaire=regimeScolaire;
        this.adresse=adresse;
        this.numeroTelephone=numeroTelephone;
        this.travailPere=travailPere;
        this.travailMere=travailMere;
        this.bonDeSante=bonDeSante;
        this.note=note;

    }


    Regime getRegimeScolaire() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.regimeScolaire;
    }

    void setRegimeScolaire(Regime value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.regimeScolaire = value;
    }

    String getNom() {
        return this.nom;
    }

    void setNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nom = value;
    }

    String getPreNom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.preNom;
    }

    void setPreNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.preNom = value;
    }

    Date getDateNaissance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.dateNaissance;
    }


    void setDateNaissance(Date value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.dateNaissance = value;
    }


    int getNumeroInscription() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.numeroInscription;
    }


    void setNumeroInscription(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.numeroInscription = value;
    }


    String getClasse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.classe;
    }


    void setClasse(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.classe = value;
    }


    String getAnneeScolaire() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.anneeScolaire;
    }


    void setAnneeScolaire(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.anneeScolaire = value;
    }


    String getAdresse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.adresse;
    }


    void setAdresse(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.adresse = value;
    }


    String getNumeroTelephone() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.numeroTelephone;
    }


    void setNumeroTelephone(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.numeroTelephone = value;
    }


    String getTravailPere() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.travailPere;
    }


    void setTravailPere(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.travailPere = value;
    }


    String getTravailMere() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.travailMere;
    }


    void setTravailMere(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.travailMere = value;
    }


    String getBonDeSante() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.bonDeSante;
    }


    void setBonDeSante(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.bonDeSante = value;
    }


    String getNote() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.note;
    }


    void setNote(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.note = value;
    }

}