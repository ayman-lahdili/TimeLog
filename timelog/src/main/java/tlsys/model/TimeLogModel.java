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

    private String ressourcePath;
    private Timer timer;
    private int NPE;
    private List<Discipline> defaultDisciplinesNameList; // TODO
    private List<Project> projectList;
    private List<Employe> employeList;
    private List<EmployeLog> employeLogList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        fm = new JsonFileManager(getRessourcePath(), this);
        
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

    public String getRessourcePath() {
        String osName = System.getProperty("os.name").split(" ", 2)[0];
        switch (osName) {
            case "Linux":
                ressourcePath = "timelog/src/main/ressources/";
                break;
            case "Windows":
                ressourcePath = "timelog\\src\\main\\ressources\\";
                break;
        }
        return ressourcePath;
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

    public String getTalonPaieAllEmploye() {
        String rapport = "";

        return rapport;
    }
    
    public String getTalonPaieEmploye(int ID) {
        String rapport = "";

        return rapport;
    }

    public String getTalonPaieEmploye(int ID, String debut, String fin) {
        LocalDate start = null;
        LocalDate end = null;

        //TODO 
    
        if (debut.equals("default")) {
            LocalDate currentDate = LocalDate.now();
            LocalDate startOfLastWeek = currentDate.minusWeeks(1);
    
            // Find the start of the last odd week
            start = startOfLastWeek.minusDays(startOfLastWeek.getDayOfWeek().getValue() % 2);
            end = LocalDate.now();
        } else {
            start = LocalDate.parse(debut);
            end = LocalDate.parse(fin);
        }
    
        Employe employe = getEmployeByID(ID);
        List<EmployeLog> employeLog = getEmployeLogsList();
        long totaleHeures = 0;
        long totaleHeuresSuppl = 0;
    
        for (EmployeLog log : employeLog) {
            if (log.getEmployeeID() == employe) {
                Instant startTime = log.getStartDateTime();
                Instant endTime = log.getEndDateTime();
    
                // Convert Instant to LocalDateTime
                LocalDateTime startDateTime = startTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endDateTime = endTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
    
                // Check if the log falls within the given start and end dates
                if (!startDateTime.toLocalDate().isBefore(start) && !endDateTime.toLocalDate().isAfter(end)) {
                    Duration duration = Duration.between(startDateTime, endDateTime);
                    long hoursWorked = duration.toHours();
    
                    if (totaleHeures + hoursWorked <= 40) {
                        totaleHeures += hoursWorked;
                    } else {
                        totaleHeuresSuppl += (totaleHeures + hoursWorked) - 40;
                        totaleHeures = 40;
                    }
                }
            }
        }
    
        double salaireBase = employe.getTauxHoraireBase();
        double salaireTempsSupplementaire = employe.getTauxHoraireTempsSupplementaire();
        double salaireBrut = salaireBase * totaleHeures + salaireTempsSupplementaire * totaleHeuresSuppl;
        double salaireNet = salaireBrut * 0.6;  
        
        return "Talon de paie pour employé avec ID : " + ID + "{" +
                "Nombre d’heures travaillées = " + totaleHeures + '\'' +
                ", Nombre d’heures supplémentaires travaillées = " + totaleHeuresSuppl + '\'' +
                ", Salaire brute = " + salaireBrut + '\'' +
                ", Salaire net = " + salaireNet +
                "}";
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
