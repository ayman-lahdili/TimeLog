package tlsys.model;

import java.util.List;

public class Employe {

    private JsonFileManager fm;

    private int ID;
    private String nom;
    private String dateEmbauche;
    private String dateDepart;
    private int NAS;
    private int numeroPoste;
    private double tauxHoraireBase;
    private double tauxHoraireTempsSupplementaire;
    private List<Project> projectsAssignesList;

    public Employe(int iD, String nom, String dateEmbauche, String dateDepart, int nAS, int numeroPoste,
            double tauxHoraireBase, double tauxHoraireTempsSupplementaire, List<Project> projetsAssignesList) {
        ID = iD;
        this.nom = nom;
        this.dateEmbauche = dateEmbauche;
        this.dateDepart = dateDepart;
        NAS = nAS;
        this.numeroPoste = numeroPoste;
        this.tauxHoraireBase = tauxHoraireBase;
        this.tauxHoraireTempsSupplementaire = tauxHoraireTempsSupplementaire;
        this.projectsAssignesList = projetsAssignesList;

        fm = TimeLogModel.fm;
    }

    @Override
    public String toString() {
        return "Employe [ID=" + ID + ", nom=" + nom + "]";
    }

    public int getID() {
        return ID;
    }

    public boolean setID(int iD) {
        ID = iD;
        return fm.setID(this, ID);
    }

    public String getNom() {
        return nom;
    }

    public boolean setNom(String nom) {
        this.nom = nom;
        return fm.setNom(this, nom);   
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

    public List<Project> getProjectsAssignesList() {
        return projectsAssignesList;
    }

    public boolean setProjectsAssignesList(List<Project> projetsIDAssignesList) {
        this.projectsAssignesList = projetsIDAssignesList;
        return fm.setProjectsAssignesList(this, projetsIDAssignesList);
    }

}
