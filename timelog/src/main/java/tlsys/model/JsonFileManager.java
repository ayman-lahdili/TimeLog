package tlsys.model;

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

public class JsonFileManager {
    
    private String ressourcePath;
    private TimeLogModel model;

    public JsonFileManager(String ressourcePath, TimeLogModel model) {
        this.ressourcePath = ressourcePath;
        this.model = model;
    }

    public int loadNPE() {
        try (FileReader fileReader = new FileReader(ressourcePath+"systemproperties.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            JSONObject systemProperties = (JSONObject) jsonObject.get("systemPropreties");

            int NPE = ((Long) systemProperties.get("NPE")).intValue();
            return NPE;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Discipline> loadDefaultDisciplineList() {
        try (FileReader fileReader = new FileReader(ressourcePath+"systemproperties.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            JSONObject systemProperties = (JSONObject) jsonObject.get("systemPropreties");

            List<Discipline> defaultDisciplineList = (List<Discipline>) systemProperties.get("defaultDisciplines"); //TODO
            return defaultDisciplineList;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employe> loadEmployeData() {
        List<Employe> employeList = new ArrayList<>();

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
                JSONArray projetsAssignes = (JSONArray) employeeObject.get("projetsAssignes");

                List<Project> projetsAssignesList = new ArrayList<Project>();
                for (Object projectIDObject: projetsAssignes) {
                    int projectID = ((Long) projectIDObject).intValue();
                    projetsAssignesList.add(model.getProjectByID((Integer) projectID));
                }

                // Create the Employe object and add it to the employeList
                Employe employe = new Employe(id, name, dateEmbauche, dateDepart, nas, numeroPoste, tauxHoraireBase,
                        tauxHoraireTempsSupplementaire, projetsAssignesList);
                employeList.add(employe);
            }

            return employeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Administrator> loadAdministratorData() {
        List<Administrator> administratorList = new ArrayList<>();

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

            return administratorList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> loadProjectData() {
        List<Project> projectList = new ArrayList<>();

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
            return projectList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EmployeLog> loadEmployeLogData() {
        List<EmployeLog> employeLogList = new ArrayList<>();
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

                EmployeLog employeLog = new EmployeLog(model.getEmployeByID(employeeID), model.getProjectByID(projectID),
                        model.getProjectByID(projectID).getDisciplineByName(disciplineName), startDateTime, endDateTime);
                employeLogList.add(employeLog);
            }

            return employeLogList;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean post(EmployeLog log) {
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
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    

}
