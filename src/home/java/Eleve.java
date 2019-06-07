package home.java;


import java.util.Date;


public class Eleve {
    private final static Eleve instance = new Eleve();
    private int anneeScolaire;


    private int Id;
    private int gender;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String classe;
    private int tranches;
    private String regime;
    private String adresse;
    private String phone;
    private String prenomPere;
    private String prenomMere;
    private String nomMere;
    private String travailPere;
    private String travailMere;
    private String maladie;
    private String wakil;
    private String remarque;
    private double montantRestant;

    public Eleve(int Id, int gender, String nom, String prenom, Date dateNaissance, String lieuNaissance, String classe, int anneeScolaire,
                 String regime, String adresse, String phone, String prenomPere, String prenomMere, String nomMere, String travailPere, String travailMere,
                 String maladie, String wakil, String remarque, int tranches, double montantRestant) {
        this.Id=Id;
        this.gender=gender;
        this.nom=nom;
        this.prenom=prenom;
        this.dateNaissance=dateNaissance;
        this.lieuNaissance=lieuNaissance;
        this.classe=classe;
        this.anneeScolaire=anneeScolaire;
        this.regime=regime;
        this.adresse=adresse;
        this.phone=phone;
        this.prenomPere=prenomPere;
        this.prenomMere=prenomMere;
        this.nomMere=nomMere;
        this.travailPere=travailPere;
        this.travailMere=travailMere;
        this.maladie=maladie;
        this.wakil=wakil;
        this.remarque=remarque;
        this.tranches = tranches;
        this.montantRestant = montantRestant;

    }

    public static Eleve getInstance() {
        return instance;
    }

    public Eleve() {

    }


    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Id;
    }


    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Id = value;
    }

    public String getRegime() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.regime;
    }

    public void setRegime(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.regime = value;
    }

    public String getNom() {
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

    public Date getDateNaissance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.dateNaissance;
    }


    public void setDateNaissance(Date value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.dateNaissance = value;
    }





    public String getClasse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.classe;
    }


    public void setClasse(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.classe = value;
    }

    public int getAnneeScolaire() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.anneeScolaire;
    }


    public void setAnneeScolaire(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.anneeScolaire = value;
    }


    public String getAdresse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.adresse;
    }


    public void setAdresse(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.adresse = value;
    }


    public String getPhone() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.phone;
    }


    public void setPhone(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.phone = value;
    }


    public String getTravailPere() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.travailPere;
    }


    public void setTravailPere(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.travailPere = value;
    }


    public String getTravailMere() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.travailMere;
    }


    public void setTravailMere(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.travailMere = value;
    }


    public String getMaladie() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.maladie;
    }


    public void setMaladie(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.maladie = value;
    }


    public String getRemarque() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.remarque;
    }


    public void setRemarque(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.remarque = value;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getPrenomPere() {
        return prenomPere;
    }

    public void setPrenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
    }

    public String getPrenomMere() {
        return prenomMere;
    }

    public void setPrenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
    }

    public String getNomMere() {
        return nomMere;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getWakil() {
        return wakil;
    }

    public void setWakil(String wakil) {
        this.wakil = wakil;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public int getTranches() {
        return tranches;
    }

    public void setTranches(int tranches) {
        this.tranches = tranches;
    }
}