package tlsys.model;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


/*
 * Le package model permet d'acceder au donnée json et effectue la logic du programme
 */
public class TimeLogModel {

    protected static JsonFileManager fm;

    private Timer timer;
    private int NPE;
    private List<Discipline> defaultDisciplinesNameList; // TODO
    private List<Project> projectList;
    private List<Employe> employeList;
    private List<EmployeLog> employeLogList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        fm = new JsonFileManager(this);
        
        NPE = fm.loadNPE();
        defaultDisciplinesNameList = fm.loadDefaultDisciplineList();
        projectList = fm.loadProjectData();
        employeList = fm.loadEmployeData();
        administratorList = fm.loadAdministratorData();
        employeLogList = fm.loadEmployeLogData();

        loadTimer();
    }

    public void loadTimer() {
        timer = new Timer();
    }

    public Employe authenticateEmploye(int ID, String username) {
        for (Employe employe : employeList) {
            if (employe.getID() == ID && employe.getNom().equals(username)) {
                return employe;
            }
        }
        return null; // Return null si invalide
    }

    public Administrator authenticateAdministrator(String name, String password) {
        for (Administrator admin : administratorList) {

            if (admin.getName().equals(name) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null; // Return null si invalide
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<Employe> getEmployeList() {
        return employeList;
    }

    public List<EmployeLog> getEmployeLogsList() {
        return employeLogList;
    }

    public EmployeLog startTask(Employe employeeID, Project project, Discipline discipline) {
        return new EmployeLog(employeeID, project, discipline, timer.start(), null);
    }

    public EmployeLog endTask(EmployeLog log) {
        log.setEndDateTime(timer.stop());
        employeLogList.add(log);
        post(log);
        Discipline discipline = log.getDiscipline();
        discipline.setHeuresTotalesConsacre(log.getProject(), discipline.getHeuresTotalesConsacre() + timer.getDurationInHours());
        return log;
    }

    public Employe getEmployeByID(int ID) {
        for (Employe employe : employeList) {
            if (employe.getID() == ID) {
                return employe;
            }
        }
        return null;
    }

    public Project getProjectByID(int ID) {
        for (Project project : projectList) {
            if (project.getID() == ID) {
                return project;
            }
        }
        return null;
    }

    public List<Discipline> getdefaultDisciplineList() {
        return defaultDisciplinesNameList;
    }
    
    public int getNPE() {
        return NPE; //TODO
    }

    public Employe createEmploye(int iD, String nom, String dateEmbauche, String dateDepart, int nAS, int numeroPoste,
    double tauxHoraireBase, double tauxHoraireTempsSupplementaire) {
        return new Employe(iD, nom, dateEmbauche, dateDepart, nAS, numeroPoste, tauxHoraireBase, tauxHoraireTempsSupplementaire, new ArrayList<Project>());
    }

    public Discipline createNewDiscipline(String name, int heuresBudgetees) {
        return new Discipline(name, heuresBudgetees, 0.0);
    }

    public Project createProject(int iD, String name, String dateDebut, String dateFin, List<Discipline> disciplinesList) {
        return new Project(iD, name, dateDebut, dateFin, disciplinesList);
    }

    public boolean post(Object obj) {
        if (obj instanceof EmployeLog) {
            return fm.post((EmployeLog) obj);
        }
        if (obj instanceof Employe) {
            return fm.post((Employe) obj);
        }
        if (obj instanceof Project) {
            return fm.post((Project) obj);
        }
        return false;
    }

    public boolean delete(Object obj) {
        if (obj instanceof Employe) {
            
            return fm.delete((Employe) obj);
        }
        if (obj instanceof Project) {
            return fm.delete((Project) obj);
        }
        return false;
    }

    public boolean setNPE(int newNPE) {
        return fm.setNPE(newNPE);
    }

}
