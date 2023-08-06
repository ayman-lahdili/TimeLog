package tlsys.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
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
        try (FileReader fileReader = new FileReader(ressourcePath + "systemproperties.json")) {
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
        try (FileReader fileReader = new FileReader(ressourcePath + "systemproperties.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            JSONObject systemProperties = (JSONObject) jsonObject.get("systemPropreties");
            JSONArray defaultDisciplineArray = (JSONArray) systemProperties.get("defaultDisciplines");

            List<Discipline> defaultDisciplineList = new ArrayList<>(); // TODO

            for (Object disciplineObj : defaultDisciplineArray) {
                JSONObject disciplineJson = (JSONObject) disciplineObj;
                String name = (String) disciplineJson.get("name");
                double heuresBudgetees = (long) disciplineJson.get("heuresBudgetees");
                double heuresTotalesConsacre = (long) disciplineJson.get("heuresTotalesConsacre");

                Discipline discipline = new Discipline(name, (double) heuresBudgetees, (double) heuresTotalesConsacre);
                defaultDisciplineList.add(discipline);
            }

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
                for (Object projectIDObject : projetsAssignes) {
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

                EmployeLog employeLog = new EmployeLog(model.getEmployeByID(employeeID),
                        model.getProjectByID(projectID),
                        model.getProjectByID(projectID).getDisciplineByName(disciplineName), startDateTime,
                        endDateTime);
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

    public boolean post(Employe employe) {
        try (FileReader fileReader = new FileReader(ressourcePath+"employees.json")) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
    
            // Create a JSON object for the new employe
            JSONObject newEmployeJson = new JSONObject();
            newEmployeJson.put("id", employe.getID());
            newEmployeJson.put("name", employe.getNom());
            newEmployeJson.put("dateEmbauche", employe.getDateEmbauche());
            newEmployeJson.put("dateDepart", employe.getDateDepart());
            newEmployeJson.put("nas", employe.getNAS());
            newEmployeJson.put("numeroPoste", employe.getNumeroPoste());
            newEmployeJson.put("tauxHoraireBase", employe.getTauxHoraireBase());
            newEmployeJson.put("tauxHoraireTempsSupplementaire", employe.getTauxHoraireTempsSupplementaire());
            List<Integer> projectListID = new ArrayList<Integer>();
            for (Project project: employe.getProjectsAssignesList()) {
                projectListID.add(project.getID());
            }
            newEmployeJson.put("projetsAssignes", projectListID);
    
            // Add the new employe JSON object to the JSON array
            jsonArray.add(newEmployeJson);
    
            // Write the updated JSON array back to the file
            try (FileWriter fileWriter = new FileWriter(ressourcePath+"employees.json")) {
                fileWriter.write(jsonArray.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean post(Project project) {
        try (FileReader fileReader = new FileReader(ressourcePath+"projects.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
    
            // Get the projects array from the JSON object
            JSONArray projectsArray = (JSONArray) jsonObject.get("projects");
    
            // Create a new JSON object to represent the new project
            JSONObject newProjectObject = new JSONObject();
            newProjectObject.put("name", project.getName());
            newProjectObject.put("ID", project.getID());
            newProjectObject.put("dateDebut", project.getDateDebut());
            newProjectObject.put("dateFin", project.getDateFin());
    
            // Create a new JSON array to represent the disciplines list
            JSONArray disciplinesArray = new JSONArray();
            List<Discipline> disciplinesList = project.getDisciplinesList();
            for (Discipline discipline : disciplinesList) {
                JSONObject disciplineObject = new JSONObject();
                disciplineObject.put("name", discipline.getName());
                disciplineObject.put("heuresBudgetees", discipline.getHeuresBudgetees());
                disciplineObject.put("heuresTotalesConsacre", discipline.getHeuresTotalesConsacre());
                disciplinesArray.add(disciplineObject);
            }
            newProjectObject.put("disciplines", disciplinesArray);
    
            // Add the new project object to the projects array
            projectsArray.add(newProjectObject);
    
            // Write the updated JSON object back to the file
            try (FileWriter fileWriter = new FileWriter(ressourcePath+"projects.json")) {
                fileWriter.write(jsonObject.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Employe employe) {
        try (FileReader fileReader = new FileReader(ressourcePath+"employees.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
    
            JSONArray employeArray = (JSONArray) jsonObject.get("employees");
    
            Iterator<JSONObject> iterator = employeArray.iterator();
            while (iterator.hasNext()) {
                JSONObject employeeObject = iterator.next();
                int employeeID = ((Long) employeeObject.get("id")).intValue();
    
                if (employeeID == employe.getID()) {
                    iterator.remove();
                    break;
                }
            }
    
            try (FileWriter fileWriter = new FileWriter(ressourcePath+"employees.json")) {
                fileWriter.write(jsonObject.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Project project) {
        try (FileReader fileReader = new FileReader(ressourcePath+"projects.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
    
            JSONArray projectsArray = (JSONArray) jsonObject.get("projects");
    
            Iterator<JSONObject> iterator = projectsArray.iterator();
            while (iterator.hasNext()) {
                JSONObject projectObject = iterator.next();
                int projectID = ((Long) projectObject.get("ID")).intValue();
    
                if (projectID == project.getID()) {
                    iterator.remove();
                    break;
                }
            }
    
            try (FileWriter fileWriter = new FileWriter(ressourcePath+"projects.json")) {
                fileWriter.write(jsonObject.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setNPE(int newNPE) {
        try (FileReader fileReader = new FileReader(ressourcePath+"systemproperties.json")) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
    
            // Get the systemProperties JSON object
            JSONObject systemProperties = (JSONObject) jsonObject.get("systemPropreties");
    
            // Update the value of "NPE"
            systemProperties.put("NPE", newNPE);
    
            // Write the updated JSON object back to the file
            try (FileWriter fileWriter = new FileWriter(ressourcePath+"systemproperties.json")) {
                fileWriter.write(jsonObject.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
