package tlsys.model;

public class Employe {

    private int ID;
    private String nom;
    private String dateEmbauche;
    private String dateDepart;
    private int NAS;
    private int numeroPoste;
    private double tauxHoraireBase;
    private double tauxHoraireTempsSupplementaire;

    public Employe(int iD, String nom, String dateEmbauche, String dateDepart, int nAS, int numeroPoste,
            double tauxHoraireBase, double tauxHoraireTempsSupplementaire) {
        ID = iD;
        this.nom = nom;
        this.dateEmbauche = dateEmbauche;
        this.dateDepart = dateDepart;
        NAS = nAS;
        this.numeroPoste = numeroPoste;
        this.tauxHoraireBase = tauxHoraireBase;
        this.tauxHoraireTempsSupplementaire = tauxHoraireTempsSupplementaire;
    }

    @Override
    public String toString() {
        return "Employe [ID=" + ID + ", nom=" + nom + "]";
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

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getNAS() {
        return NAS;
    }

    public void setNAS(int nAS) {
        NAS = nAS;
    }

    public int getNumeroPoste() {
        return numeroPoste;
    }

    public void setNumeroPoste(int numeroPoste) {
        this.numeroPoste = numeroPoste;
    }

    public double getTauxHoraireBase() {
        return tauxHoraireBase;
    }

    public void setTauxHoraireBase(double tauxHoraireBase) {
        this.tauxHoraireBase = tauxHoraireBase;
    }

    public double getTauxHoraireTempsSupplementaire() {
        return tauxHoraireTempsSupplementaire;
    }

    public void setTauxHoraireTempsSupplementaire(double tauxHoraireTempsSupplementaire) {
        this.tauxHoraireTempsSupplementaire = tauxHoraireTempsSupplementaire;
    }

}
