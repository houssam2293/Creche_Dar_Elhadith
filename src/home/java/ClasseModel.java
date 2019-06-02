package home.java;



public class ClasseModel {
    private int Id;
    private String ClassNam;
    private String ClassRom;
    private int maxNbrElev;
    private String remarque;


    public ClasseModel(){

    }
    public ClasseModel(int Id, String ClassNam, String ClassRom, int maxNbrElev, String remarque){
        this.Id=Id;
        this.ClassNam=ClassNam;
        this.ClassRom=ClassRom;
        this.maxNbrElev=maxNbrElev;
        this.remarque=remarque;
    }

    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.Id;
    }

    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.Id = value;
    }

    public String getClassNam() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.ClassNam;
    }

    public void setClassNam(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.ClassNam = value;
    }


    public String getClassRom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.ClassRom;
    }

    public void setClassRom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.ClassRom = value;
    }


    public String getremarque() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.remarque;
    }

    public void setremarque(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.remarque = value;
    }

    public int getmaxNbrElev() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.maxNbrElev;
    }
    public void setmaxNbrElev(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.maxNbrElev = value;
    }


}
