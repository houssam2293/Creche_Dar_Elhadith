package home.java;


public class Frais {
    private final static Frais instance = new Frais();
    private double fraisEleve;
    private double fraisCharity;
    private double fraisEmploye;
    private double fraisStock;

    public Frais(double fraisEleve, double fraisCharity, double fraisEmploye, double fraisStock, double fraisMoving) {

        this.fraisEleve = fraisEleve;
        this.fraisCharity = fraisCharity;
        this.fraisEmploye = fraisEmploye;
        this.fraisStock = fraisStock;

    }


    public Frais() {

    }

    public static Frais getInstance() {
        return instance;
    }

    public double getFraisEleve() {
        return fraisEleve;
    }

    public void setFraisEleve(double fraisEleve) {
        this.fraisEleve = fraisEleve;
    }

    public double getFraisCharity() {
        return fraisCharity;
    }

    public void setFraisCharity(double fraisCharity) {
        this.fraisCharity = fraisCharity;
    }

    public double getFraisEmploye() {
        return fraisEmploye;
    }

    public void setFraisEmploye(double fraisEmploye) {
        this.fraisEmploye = fraisEmploye;
    }

    public double getFraisStock() {
        return fraisStock;
    }

    public void setFraisStock(double fraisStock) {
        this.fraisStock = fraisStock;
    }

}