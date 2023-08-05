package tlsys.model;

public class Discipline {

    private String name;
    private double heuresBudgetees; //TODO Change
    private double heuresTotalesConsacre;

    public Discipline(String name, double heuresBudgetees, double heuresTotalesConsacre) {
        this.name = name;
        this.heuresBudgetees = heuresBudgetees;
        this.heuresTotalesConsacre = heuresTotalesConsacre;
    }

    @Override
    public String toString() {
        return "Discipline [name=" + name + ", heuresBudgetees=" + heuresBudgetees + ", heuresTotalesConsacre="
                + heuresTotalesConsacre + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeuresBudgetees() {
        return heuresBudgetees;
    }

    public boolean setHeuresBudgetees(Project project, int heuresBudgetees) {
        this.heuresBudgetees = heuresBudgetees;
        return false;
    }

    public double getHeuresTotalesConsacre() {
        return heuresTotalesConsacre;
    }

    public void setHeuresTotalesConsacre(int heuresTotalesConsacre) {
        this.heuresTotalesConsacre = heuresTotalesConsacre;
    }

}
