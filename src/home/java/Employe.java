package home.java;

import java.util.Date;

public class Employe {

    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String adresse;
    private String numTelephone;
    private String socialSecurityNumber;
    private String diplome;
    private String experience;
    private String itar;
    private String renouvlement_de_contrat;
    private Date date_debut;
    private String fonction;
    private String classe;
    private String regimeScolaire;
    private String celibacyTitle;
    private int maleChild, femaleChild;
    private int statuSocial;
    private String remarque;


    public Employe(int id, String nom, String prenom, Date dateNaissance, String lieuNaissance, String adresse,
                   String numTelephone, String socialSecurityNumber, String diplome, String experience, String itar,
                   String renouvlement_de_contrat, Date date_debut, String fonction, String classe, String regimeScolaire, int statuSocial, String celibacyTitle,
                   int maleChild, int femaleChild, String remarque) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.adresse = adresse;
        this.numTelephone = numTelephone;
        this.socialSecurityNumber = socialSecurityNumber;
        this.diplome = diplome;
        this.experience = experience;
        this.itar = itar;
        this.renouvlement_de_contrat = renouvlement_de_contrat;
        this.date_debut = date_debut;
        this.fonction = fonction;
        this.classe = classe;
        this.regimeScolaire = regimeScolaire;
        this.statuSocial = statuSocial;
        this.celibacyTitle = celibacyTitle;
        this.maleChild = maleChild;
        this.femaleChild = femaleChild;

        this.remarque = remarque;
    }

    public Employe() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getNom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nom;
    }


    public void setNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nom = value;
    }


    public String getPrenom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.prenom;
    }


    public void setPrenom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.prenom = value;
    }


    public String getItar() {
        return this.itar;
    }

    public void setItar(String value) {
        this.itar = value;
    }


    public Date getDateNaissance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.dateNaissance;
    }


    public void setDateNaissance(Date value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.dateNaissance = value;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public void setLieuNaissance(String value) {
        this.lieuNaissance = value;
    }


    public String getAdresse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.adresse;
    }


    public void setAdresse(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.adresse = value;
    }


    public String getNumTelephone() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.numTelephone;
    }


    public void setNumTelephone(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.numTelephone = value;
    }

    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String value) {
        this.socialSecurityNumber = value;
    }


    public String getDiplome() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.diplome;
    }


    public void setDiplome(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.diplome = value;
    }


    public String getExperience() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.experience;
    }


    public void setExperience(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.experience = value;
    }


    public String getRenouvlement_de_contrat() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.renouvlement_de_contrat;
    }


    public void setRenouvlement_de_contrat(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.renouvlement_de_contrat = value;
    }

    public Date getDate_debut() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.date_debut;
    }


    public void setDate_debut(Date value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.date_debut = value;
    }


    public String getFonction() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.fonction;
    }


    public void setFonction(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.fonction = value;
    }

    public String getCelibacyTitle() {
        return this.celibacyTitle;
    }

    public void setCelibacyTitle(String value) {
        this.celibacyTitle = value;
    }

    public int getMaleChild() {
        return this.maleChild;
    }

    public void setMaleChild(int value) {
        this.maleChild = value;
    }

    public int getFemaleChild() {
        return this.femaleChild;
    }

    public void setFemaleChild(int value) {
        this.femaleChild = value;
    }


    public int getStatuSocial() {
        return this.statuSocial;
    }

    public boolean estmarier() {
        return statuSocial == 1;
    }
    public void setStatuSocial(int statuSocial) {
        this.statuSocial = statuSocial;
    }

    public String getRegimeScolaire() {
        return regimeScolaire;
    }

    public void setRegimeScolaire(String regimeScolaire) {
        this.regimeScolaire = regimeScolaire;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}