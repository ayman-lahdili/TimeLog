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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Le package model permet d'acceder au donnée json et effectue la logic du programme
 */
public class TimeLogModel {

    private String ressourcePath;
    private Timer timer;
    private int NPE;
    private List<String> defaultDisciplinesNameList;
    private List<Project> projectList;
    private List<Employe> employeList;
    private List<EmployeLog> employeLogList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        // Load data from the JSON files when the TimeLogModel object is created
        loadTimer();
        loadRessourcePath();
        loadProjectData();
        loadEmployeData();
        loadAdministratorData();
        loadEmployeLogData();
    }

    public void loadTimer() {
        timer = new Timer();
    }

    public void loadRessourcePath() {
        String osName = System.getProperty("os.name").split(" ", 2)[0];
        switch (osName) {
            case "Linux":
                ressourcePath = "timelog/src/main/ressources/";
                break;
            case "Windows":
                ressourcePath = "timelog\\src\\main\\ressources\\";
                break;
        }
    }

    public void loadSystemPropreties() {
        try (FileReader fileReader = new FileReader(ressourcePath+"systemProperties.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            JSONObject systemProperties = (JSONObject) jsonObject.get("systemPropreties");

            NPE = ((Long) systemProperties.get("NPE")).intValue();
            defaultDisciplinesNameList = (List<String>) systemProperties.get("defaultDisciplines");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeData() {
        employeList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(ressourcePath + "employees.json")) {
            JSONParser parser = new JSONParser();
            // Parse the JSON object
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray employeesArray = (JSONArray) jsonObject.get("employees");

            for (Object obj : employeesArray) {
                JSONObject employeeObject = (JSONObject) obj;
                int id = ((Long) employeeObject.get("id")).intValue();
                String name = (String) employeeObject.get("name");
                String dateEmbauche = (String) employeeObject.get("dateEmbauche");
                String dateDepart = (String) employeeObject.get("dateDepart");
                int nas = ((Long) employeeObject.get("nas")).intValue();
                int numeroPoste = ((Long) employeeObject.get("numeroPoste")).intValue();
                double tauxHoraireBase = (double) employeeObject.get("tauxHoraireBase");
                double tauxHoraireTempsSupplementaire = (double) employeeObject.get("tauxHoraireTempsSupplementaire");
                List<Integer> projetsAssignes = (List<Integer>) employeeObject.get("projetsAssignes");

                // Create the Employe object and add it to the employeList
                Employe employe = new Employe(id, name, dateEmbauche, dateDepart, nas, numeroPoste, tauxHoraireBase,
                        tauxHoraireTempsSupplementaire, projetsAssignes);
                employeList.add(employe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAdministratorData() {
        administratorList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(ressourcePath + "administrators.json")) {
            JSONParser parser = new JSONParser();
            // Parse the JSON object
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray employeesArray = (JSONArray) jsonObject.get("administrators");

            for (Object obj : employeesArray) {
                JSONObject employeeObject = (JSONObject) obj;
                String name = (String) employeeObject.get("name");
                String password = (String) employeeObject.get("password");
                // Create the Employe object and add it to the employeList
                Administrator admin = new Administrator(name, password);
                administratorList.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProjectData() {
        projectList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(ressourcePath + "projects.json")) {
            JSONParser parser = new JSONParser();
            JSONObject rootObject = (JSONObject) parser.parse(fileReader);
            JSONArray projectsArray = (JSONArray) rootObject.get("projects");

            for (Object obj : projectsArray) {
                JSONObject projectObject = (JSONObject) obj;
                String name = (String) projectObject.get("name");
                int ID = ((Long) projectObject.get("ID")).intValue();
                String dateDebut = (String) projectObject.get("dateDebut");
                String dateFin = (String) projectObject.get("dateFin");

                JSONArray disciplinesArray = (JSONArray) projectObject.get("disciplines");
                List<Discipline> disciplinesList = new ArrayList<>();

                for (Object disciplineObj : disciplinesArray) {
                    JSONObject disciplineObject = (JSONObject) disciplineObj;
                    String disciplineName = (String) disciplineObject.get("name");
                    int heuresBudgetees = ((Long) disciplineObject.get("heuresBudgetees")).intValue();
                    int heuresTotalesConsacre = ((Long) disciplineObject.get("heuresTotalesConsacre")).intValue();

                    Discipline discipline = new Discipline(disciplineName, heuresBudgetees, heuresTotalesConsacre);
                    disciplinesList.add(discipline);
                }

                Project project = new Project(ID, name, dateDebut, dateFin, disciplinesList);
                projectList.add(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeLogData() {
        employeLogList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(ressourcePath + "employeelogs.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray employeeLogsArray = (JSONArray) jsonObject.get("employeeLogs");

            for (Object obj : employeeLogsArray) {
                JSONObject employeLogObject = (JSONObject) obj;
                int employeeID = ((Long) employeLogObject.get("employeeID")).intValue();
                int projectID = ((Long) employeLogObject.get("projectID")).intValue();
                String disciplineName = (String) employeLogObject.get("disciplineName");
                Instant startDateTime = Instant.parse((String) employeLogObject.get("startDateTime"));
                Instant endDateTime = Instant.parse((String) employeLogObject.get("endDateTime"));

                EmployeLog employeLog = new EmployeLog(getEmployeByID(employeeID), getProjectByID(projectID),
                        getProjectByID(projectID).getDisciplineByName(disciplineName), startDateTime, endDateTime);
                employeLogList.add(employeLog);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveEmployeLog(EmployeLog log) {
        try {
            // Read existing JSON content from employeelog.json
            JSONParser parser = new JSONParser();
            JSONArray employeeLogsArray;

            try (FileReader fileReader = new FileReader(ressourcePath + "employeelogs.json")) {
                JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
                employeeLogsArray = (JSONArray) jsonObject.get("employeeLogs");
            } catch (Exception e) {
                // If the file doesn't exist or is empty, create a new array
                employeeLogsArray = new JSONArray();
                e.printStackTrace();
            }

            // Create a new JSONObject for the EmployeLog data
            JSONObject employeLogObject = new JSONObject();
            employeLogObject.put("employeeID", log.getEmployeeID().getID());
            employeLogObject.put("projectID", log.getProjectName().getID());
            employeLogObject.put("disciplineName", log.getDisciplineName().getName());
            employeLogObject.put("startDateTime", log.getStartDateTime().toString());
            employeLogObject.put("endDateTime", log.getEndDateTime().toString());

            // Append the new EmployeLog JSONObject to the JSONArray
            employeeLogsArray.add(employeLogObject);

            // Create a new JSONObject to hold the employeeLogs array
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeLogs", employeeLogsArray);

            // Write the updated JSONArray back to employeelog.json
            try (FileWriter fileWriter = new FileWriter(ressourcePath + "employeelogs.json")) {
                fileWriter.write(jsonObject.toJSONString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public int getNPE() {
        return NPE;
    }

    public List<String> getdefaultDisciplinesNameList() {
        return defaultDisciplinesNameList;
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

    public String getRapportEtatGlobale() {
        String rapport = "";
        List<Project> projectList = getProjectList();

        if (projectList.size() == 0) {
            return null;
        } else {
            for (Project project: projectList) {
                rapport += getRapportEtatProjet(project.getID()) + "\n";
            }
        }

        return rapport;
    }

    public String getRapportEtatProjet(int ID) {
        Project project = getProjectByID(ID);
        List<Discipline> disciplines = project.getDisciplinesList();

        int nbreHeuresDesign1 = 0; //TODO double
        int pctDesign1 = 0;

        int nbreHeuresDesign2 = 0;
        int pctDesign2 = 0;

        int nbreHeuresImplementation = 0;
        int pctImplementation = 0;

        int nbreHeuresTest = 0;
        int pctTest = 0;

        int nbreHeuresDeploiement = 0;
        int pctDeploiement = 0;

        int nbreHeuresTotales = 0;
        int nbreHeuresBudgeteesTotales = 0;
        int pctProjet = 0;

        for (int i = 0; i < disciplines.size(); i++) {
            int heuresBudgetees = disciplines.get(i).getHeuresBudgetees();
            nbreHeuresBudgeteesTotales += heuresBudgetees;

            if (disciplines.get(i).getName() == "Design1") {
                nbreHeuresDesign1 = disciplines.get(i).getHeuresTotalesConsacre();
                pctDesign1 = (nbreHeuresDesign1 / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Design2") {
                nbreHeuresDesign2 = disciplines.get(i).getHeuresTotalesConsacre();
                pctDesign2 = (nbreHeuresDesign2 / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Implémentation") {
                nbreHeuresImplementation = disciplines.get(i).getHeuresTotalesConsacre();
                pctImplementation = (nbreHeuresImplementation / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Test") {
                nbreHeuresTest = disciplines.get(i).getHeuresTotalesConsacre();
                pctTest = (nbreHeuresTest / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Déploiement") {
                nbreHeuresDeploiement = disciplines.get(i).getHeuresTotalesConsacre();
                pctDeploiement = (nbreHeuresDesign1 / heuresBudgetees) * 100;
            }
        }

        nbreHeuresTotales = nbreHeuresDesign1 + nbreHeuresDesign2 + nbreHeuresImplementation + nbreHeuresTest
                + nbreHeuresDeploiement;

        pctProjet = (nbreHeuresTotales / nbreHeuresBudgeteesTotales) * 100;

        return "Project" + ID + "{" +
                "Nombre d’heures travaillées pour Design1 = " + nbreHeuresDesign1 + '\'' +
                ", Pourcentage d’avancement pour Design1 = " + pctDesign1 + '\'' +
                ", Nombre d’heures travaillées pour Design2 = " + nbreHeuresDesign2 + '\'' +
                ", Pourcentage d’avancement pour Design2 = " + pctDesign2 + '\'' +
                ", Nombre d’heures travaillées pour Implémentation = " + nbreHeuresImplementation + '\'' +
                ", Pourcentage d’avancement pour Implémentation = " + pctImplementation + '\'' +
                ", Nombre d’heures travaillées pour Test = " + nbreHeuresTest + '\'' +
                ", Pourcentage d’avancement pour Test = " + pctTest + '\'' +
                ", Nombre d’heures travaillées pour Déploiement = " + nbreHeuresDeploiement + '\'' +
                ", Pourcentage d’avancement pour Déploiement = " + pctDeploiement + '\'' +
                ", Nombre d’heures travaillées pour Projet1 = " + nbreHeuresTotales + '\'' +
                ", Pourcentage d’avancement pour Projet1 = " + pctProjet +
                "}";
    }

    
    public String getRapportEtatEmploye(String debut, String fin) {
        String rapport = "";
        List<Employe> employeList = getEmployeList();

        for(int i = 0; i<employeList.size(); i++){
            rapport += getTalonPaieEmploye(i, debut, fin) + "\n";
        }

        return rapport;
     }
     
    public String getTalonPaieEmploye(int ID) {
        return "";
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

}
