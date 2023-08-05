package tlsys.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/*
 * Le package model permet d'acceder au donnée json et effectue la logic du programme
 */
public class TimeLogModel {

    private String ressourcePath;
    JsonFileManager fm;
    private Timer timer;
    private int NPE;
    private List<Discipline> defaultDisciplinesNameList; // TODO
    private List<Project> projectList;
    private List<Employe> employeList;
    private List<EmployeLog> employeLogList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        // Load data from the JSON files when the TimeLogModel object is created
        fm = new JsonFileManager(getRessourcePath(), this);
        
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

    public void saveEmployeLog(EmployeLog log) {
        fm.post(log);
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
        saveEmployeLog(log);
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

    public boolean setNPE(int newNPE) {
        return false; //TODO
    }

    public boolean addEmployee(int iD, String nom, String dateEmbauche, String dateDepart, int nAS, int numeroPoste,
    double tauxHoraireBase, double tauxHoraireTempsSupplementaire) {
        return false;
    }

    public boolean removeEmployee(Employe employe_to_remove) {
        return false;
    }

    public boolean addProject() {
        return false;
    }

    public Discipline createNewDiscipline(String name, int heuresBudgetees) {
        return null;
    }

    public boolean removeProject(Project project_to_remove) {
        return false;
    }

}
