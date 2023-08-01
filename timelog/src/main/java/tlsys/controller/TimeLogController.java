package tlsys.controller;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.TimeLogModel;
import tlsys.view.TimeLogView;

/*
 * le package controller agit comme un intermédiaire entre model et view
 * 
 * Il est responsable du traitement des interactions de l'utilisateur (i.e. changer les types, formater les inputs, etc.)
 */

public class TimeLogController {

    private Employe currentEmployee;
    private Administrator currentAdmin;
    private TimeLogModel model;
    private TimeLogView view;

    public TimeLogController(TimeLogModel model, TimeLogView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        while (currentEmployee == null && currentAdmin == null) {
            login();
        }
    }

    public void login() {
        String loginMethod = view.promptForMethodOfLogin();

        switch (loginMethod) {
            case "1": //Employee Login
                int ID = Integer.parseInt(view.promptLoginEmployeID());
                String employeeUsername = view.promptLoginEmployeUsername();

                Employe user = model.authenticateEmploye(ID, employeeUsername);

                if (user != null) {
                    currentEmployee = user;
                    System.out.println("Login successful!");
                    displayEmployeMenu();
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                
                break;
            case "2": //
                String adminUsername = view.promptLoginAdministratorUsername();
                String password = view.promptLoginAdministratorPassword();

                Administrator admin = model.authenticateAdministrator(adminUsername, password);

                if (admin != null) {
                    currentAdmin = admin;
                    System.out.println("Login successful!");
                    displayAdministratorMenu();
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                
                break;

            default:
                break;
        }

    }

    public void displayEmployeMenu() {
        // TODO
        System.out.println("displayEmployeMenu");
    }

    public void displayAdministratorMenu() {
        // TODO
        System.out.println("displayAdministratorMenu");
    }

}
