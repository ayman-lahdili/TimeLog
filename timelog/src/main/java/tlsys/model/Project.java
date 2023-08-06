package tlsys.model;

import java.util.List;

public class Project {

    private JsonFileManager fm;

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

        fm = TimeLogModel.fm;
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

    public boolean setName(String name) {
        System.out.println("setnamemmme");
        boolean res = fm.setName(this, name);
        this.name = name;
        return res;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public boolean setDateDebut(String dateDebut) {
        boolean res = fm.setDateDebut(this, dateDebut);
        this.dateDebut = dateDebut;
        return res;
    }

    public String getDateFin() {
        return dateFin;
    }

    public boolean setDateFin(String dateFin) {
        boolean res = fm.setDateFin(this, dateFin);
        this.dateDebut = dateFin;
        return res;
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
