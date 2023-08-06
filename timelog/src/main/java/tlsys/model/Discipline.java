package tlsys.model;

public class Discipline {

    private JsonFileManager fm;
    
    private String name;
    private double heuresBudgetees; //TODO Change
    private double heuresTotalesConsacre;

    public Discipline(String name, double heuresBudgetees, double heuresTotalesConsacre) {
        this.name = name;
        this.heuresBudgetees = heuresBudgetees;
        this.heuresTotalesConsacre = heuresTotalesConsacre;

        fm = TimeLogModel.fm;
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
        return fm.setHeuresBudgetees(project, this, heuresBudgetees);
    }

    public double getHeuresTotalesConsacre() {
        return heuresTotalesConsacre;
    }

    public boolean setHeuresTotalesConsacre(Project project, int heuresTotalesConsacre) {
        this.heuresTotalesConsacre = heuresTotalesConsacre;
        return fm.setHeuresTotalesConsacre(project, this, heuresBudgetees);
    }

}
