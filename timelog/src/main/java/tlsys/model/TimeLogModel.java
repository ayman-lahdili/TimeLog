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

public class TimeLogModel {

    private List<Employe> employeList;

    public TimeLogModel() {
        // Load the employee data from the JSON file when the TimeLogModel is created
        loadEmployeData();
    }

    private void loadEmployeData() {
        employeList = new ArrayList<>();

        try (FileReader fileReader = new FileReader("timelog\\src\\main\\ressources\\employees.json")) {
            JSONParser parser = new JSONParser();
            // Parse the JSON array
            JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
            for (Object obj : jsonArray) {
                JSONObject employeeObject = (JSONObject) ((JSONObject) obj).get("employee");
                Integer ID = ((Long) employeeObject.get("ID")).intValue();
                String username = (String) employeeObject.get("username");
                Employe employe = new Employe(ID, username);
                employeList.add(employe);
                System.out.println(employe.getID() + employe.getNom());
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
}
