package tlsys.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private List<Employe> employeList;
    private List<Administrator> administratorList;

    public TimeLogModel() {
        // Load the employee data from the JSON file when the TimeLogModel is created
        loadRessourcePath();
        loadEmployeData();
        loadAdministratorData();
    }

    public void loadRessourcePath() {
        switch (System.getProperty("os.name").split(" ", 2)[0]) {
            case "Linux":  ressourcePath = "timelog/src/main/ressources/";
                     break;
            case "Windows":  ressourcePath = "timelog\\src\\main\\ressources\\";
                     break;
        }
    }

    private void loadEmployeData() {
        employeList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(ressourcePath+"employees.json")) {
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
                int nas = ((Long)employeeObject.get("nas")).intValue();
                int numeroPoste = ((Long)employeeObject.get("numeroPoste")).intValue();
                double tauxHoraireBase = (double) employeeObject.get("tauxHoraireBase");
                double tauxHoraireTempsSupplementaire = (double) employeeObject.get("tauxHoraireTempsSupplementaire");
    
                // Create the Employe object and add it to the employeList
                Employe employe = new Employe(id, name, dateEmbauche, dateDepart, nas, numeroPoste, tauxHoraireBase, tauxHoraireTempsSupplementaire);
                employeList.add(employe);
                System.out.println(employe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAdministratorData() {
        administratorList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(ressourcePath+"administrators.json")) {
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
                System.out.println(admin);
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
        return null; // Return null if credentials are invalid
    }

    public Administrator authenticateAdministrator(String name, String password) {
        for (Administrator admin : administratorList) {
            System.out.println(admin);
            if (admin.getName().equals(name) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null; // Return null if credentials are invalid
    }
}
