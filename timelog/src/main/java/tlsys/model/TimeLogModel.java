package tlsys.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
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
    private List<Project> projectList;
    private List<Employe> employeList;
    private List<EmployeLog> employeLogList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        // Load data from the JSON files when the TimeLogModel object is created
        loadTimer();
        loadRessourcePath();
        loadEmployeData();
        loadAdministratorData();
        loadProjectData();
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

                // Create the Employe object and add it to the employeList
                Employe employe = new Employe(id, name, dateEmbauche, dateDepart, nas, numeroPoste, tauxHoraireBase,
                        tauxHoraireTempsSupplementaire);
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

}
