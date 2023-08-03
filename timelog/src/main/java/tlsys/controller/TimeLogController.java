package tlsys.controller;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.Project;
import tlsys.model.Discipline;
import tlsys.model.TimeLogModel;
import tlsys.model.Timer;
import tlsys.model.EmployeLog;
import tlsys.view.TimeLogView;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

/*
 * le package controller agit comme un intermédiaire entre model et view
 * 
 * Il est responsable du traitement des interactions de l'utilisateur (i.e. changer les types, formater les inputs, etc.)
 */

public class TimeLogController {

    private Employe currentEmployee;
    private Administrator currentAdmin;
    private EmployeLog employeLog;
    private TimeLogModel model;
    private TimeLogView view;
    private Timer timer;

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
            case "1": // Employee Login
                int ID = Integer.parseInt(view.promptLoginEmployeID());
                String employeeUsername = view.promptLoginEmployeUsername();

                Employe user = model.authenticateEmploye(ID, employeeUsername);

                if (user != null) {
                    currentEmployee = user;
                    System.out.println("Login successful!");
                    displayEmployeMainMenu();
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

    public void displayEmployeMainMenu() {
        String employeeAction = view.promptEmployeMainMenu();

        switch (employeeAction) {
            case "1":
                displayStartTaskMenu();
                break;
            case "2":

                break;
            default:
                break;
        }

    }

    public void displayAdministratorMenu() {
        // TODO
        System.out.println("displayAdministratorMenu");
    }

    public void displayEmployeRapportMenu() {
        // TODO
    };

    public void displayStartTaskMenu() {
        List<Project> projectList = model.getProjectList();

        String projectSelection = view.promptProjectSelection(projectList);
        int projectIndex = Integer.parseInt(projectSelection);

        Project project = projectList.get(projectIndex-1);
        List<Discipline> disciplineList = project.getDisciplinesList();

        String disciplineSelection = view.promptDisciplineSelection(disciplineList);
        int disciplineIndex = Integer.parseInt(disciplineSelection);

        Discipline discipline = disciplineList.get(disciplineIndex-1);

        String startDecision = view.promptStartTimer(project.toString() + discipline.toString());

        switch (startDecision) {
            case "y":
                
                employeLog = model.startTask(currentEmployee, project, discipline);

                displayEndTaskMenu();

                break;
            case "n":
                displayEmployeMainMenu();
                break;
                
            default:
                break;
        }

        
    }

    public void displayEndTaskMenu() {
        String endTaskDecision = view.promptEndTimer();

        switch (endTaskDecision) {
            case "y":
                model.endTask(employeLog);
                
                break;
            case "n":
                displayEndTaskMenu();
                break;
            default:
                break;
        }

    }

}
