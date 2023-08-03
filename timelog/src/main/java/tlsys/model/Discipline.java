package tlsys.model;

public class Discipline {

    private String name;
    private int heuresBudgetees;
    private int heuresTotalesConsacre;

    public Discipline(String name, int heuresBudgetees, int heuresTotalesConsacre) {
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

    public int getHeuresBudgetees() {
        return heuresBudgetees;
    }

    public void setHeuresBudgetees(int heuresBudgetees) {
        this.heuresBudgetees = heuresBudgetees;
    }

    public int getHeuresTotalesConsacre() {
        return heuresTotalesConsacre;
    }

    public void setHeuresTotalesConsacre(int heuresTotalesConsacre) {
        this.heuresTotalesConsacre = heuresTotalesConsacre;
    }

}
