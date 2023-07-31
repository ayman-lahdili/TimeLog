package tlsys.model;

public class Employe {
    
    private int ID;
    private String nom;
    
    public Employe(int iD, String nom) {
        ID = iD;
        this.nom = nom;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
