package tlsys.model;

import java.util.List;

public class Project {

    private int ID;
    private String name;
    private String dateDebut;
    private String dateFin;
    private List<Discipline> disciplinesList;

    public Project(int iD, String name, String dateDebut, String dateFin, List<Discipline> disciplinesList) {
        this.name = name;
        ID = iD;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.disciplinesList = disciplinesList;
    }

    @Override
    public String toString() {
        return "Project [ID=" + ID + ", name=" + name + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public List<Discipline> getDisciplinesList() {
        return disciplinesList;
    }

    public Discipline getDisciplineByName(String name) {
        for (Discipline discipline : disciplinesList) {
            if (discipline.getName() == name) {
                return discipline;
            }
        }
        return null;
    }

    public void setDisciplinesList(List<Discipline> disciplinesList) {
        this.disciplinesList = disciplinesList;
    }

}
