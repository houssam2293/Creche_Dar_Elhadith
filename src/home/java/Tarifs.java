package home.java;


public class Tarifs {
    private final static Tarifs instance = new Tarifs();
    private int tarifsSets;
    private double Matin;
    private double Aprem;
    private double MatAprem;
    private double Demi;
    private double Complet;

    public Tarifs(int tarifsSets, double Matin, double Aprem, double MatAprem, double Demi, double Complet) {

        this.tarifsSets = tarifsSets;
        this.Matin = Matin;
        this.Aprem = Aprem;
        this.MatAprem = MatAprem;
        this.Demi = Demi;
        this.Complet = Complet;

    }

    public Tarifs() {

    }

    public static Tarifs getInstance() {
        return instance;
    }

    public double getMatin() {
        return Matin;
    }

    public void setMatin(double matin) {
        Matin = matin;
    }

    public double getAprem() {
        return Aprem;
    }

    public void setAprem(double aprem) {
        Aprem = aprem;
    }

    public double getMatAprem() {
        return MatAprem;
    }

    public void setMatAprem(double matAprem) {
        MatAprem = matAprem;
    }

    public double getDemi() {
        return Demi;
    }

    public void setDemi(double demi) {
        Demi = demi;
    }

    public double getComplet() {
        return Complet;
    }

    public void setComplet(double full) {
        Complet = full;
    }

    public int getTarifsSets() {
        return tarifsSets;
    }

    public void setTarifsSets(int tarifsSets) {
        this.tarifsSets = tarifsSets;
    }
}