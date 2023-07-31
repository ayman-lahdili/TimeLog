package tlsys.controller;

import tlsys.model.Employe;
import tlsys.model.TimeLogModel;
import tlsys.view.TimeLogView;

/*
 * le package controller agit comme un intermédiaire entre model et view
 * 
 * Il est responsable du traitement des interactions de l'utilisateur (i.e. changer les types, formater les inputs, etc.)
 */

public class TimeLogController {

    private Employe currentUser;
    private TimeLogModel model;
    private TimeLogView view;

    public TimeLogController(TimeLogModel model, TimeLogView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        while (currentUser == null) {
            login();
        }
        
    }

    public void login() {
        String loginMethod = view.promptForMethodOfLogin();

        switch (loginMethod) {
            case "1":
                int ID = Integer.parseInt(view.promptLoginEmployeID());
                String username = view.promptLoginEmployeUsername();

                Employe user = model.authenticateEmploye(ID, username);

                if (user != null) {
                    currentUser = user;
                    System.out.println("Login successful!");
                    displayEmployeMenu();
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                
                break;
            case "2":
                System.out.println("de");
                break;

            default:
                break;
        }

    }

    public void displayEmployeMenu() {
        String action = view.promptEmployeMenu();

        switch (action) {
            case "1":
                String ProjectID = view.promptProjectSelection();

                break;
            case "2":
                
                break;
        
            default:
                break;
        }
    }

}
