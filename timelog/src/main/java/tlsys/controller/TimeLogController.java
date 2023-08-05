package tlsys.controller;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.Project;
import tlsys.model.Discipline;
import tlsys.model.TimeLogModel;
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

    private TimeLogModel model;
    private TimeLogView view;
    private Employe currentEmployee;
    private Administrator currentAdmin;
    private EmployeLog employeLog;

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
                    view.displayLoginSuccessMessage();
                    EmployeeMainMenu();
                } else {
                    view.displayLoginErrorMessage();
                }

                break;
            case "2": // Admin Login
                String adminUsername = view.promptLoginAdministratorUsername();
                String password = view.promptLoginAdministratorPassword();

                Administrator admin = model.authenticateAdministrator(adminUsername, password);

                if (admin != null) {
                    currentAdmin = admin;
                    view.displayLoginSuccessMessage();
                    AdministratorMenu();
                } else {
                    view.displayLoginErrorMessage();
                }

                break;

            default:
                break;
        }

    }

    public void EmployeeMainMenu() {
        String employeeAction = view.promptEmployeMainMenu();

        switch (employeeAction) {
            case "1": // Commencer une tâche
                StartTaskMenu();
                break;
            case "2": // Générer des rapports
                EmployeRapportMenu();
                break;
            case "3": // Obtenir le nombre d'heures travaillées
                EmployeWorkStatusReport();
                break;
            default:
                break;
        }

    }

    public void AdministratorMenu() {
        String adminAction = view.promptEmployeMainMenu();

        switch (adminAction) {
            case "1": // Faire des modifications au paramètre du système
                AdministratorModificationMenu();
                break;
            case "2": // Générer des rapports
                
                break;
            default:
                break;
        }
    }

    public void AdministratorModificationMenu() {
        String adminModificationChoice = view.promptAdministratorModificationMenu();

        switch (adminModificationChoice) {
            case "1": // Modifier les paramètres employés
                AdministratorEmployeeModificationMenu();
                break;
            case "2": // Modifier les paramètres de projets
                
                break;
            default:
                break;
        }

    }

    public void AdministratorEmployeeModificationMenu() {
        String adminAction = view.promptAdministratorEmplpoyeeModificationMenu();

        switch (adminAction) {
            case "1": // Modifier le NPE
                int newNPE = Integer.parseInt(view.promptAdministratorModificationInputSelection());

                //TODO model
                break;
            case "2": // Modifier les paramètres d'un employé
                //TODO
                break;
            default:
                break;
        }
    }

    public void AdministratorProjectModificationMenu() {
        String adminAction = view.promptAdministratorProjectModificationMenu();

        switch (adminAction) {
            case "1": // Assignation des employés à des projets
                // TODO
                break;
            case "2": // Modifier la liste des employés

                break;
            case "3": // Modifier la liste des projets

                break;        
            default:
                break;
        }
    }


    public void AdministratorEmployeeParamsModificationMenu() {
        String adminAction = view.promptAdministratorEmployeeParamsModificationMenu();

        int employeID = Integer.parseInt(view.promptAdministratorGetEmployeeID());

        Employe employe_to_modify = model.getEmployeByID(employeID);

        switch (adminAction) {
            case "1": // Modifier l'assignation des employés à des projets
                // TODO
                break;
            case "2": // Modifier le noms d'usager de l'employé
                // TODO
                break;
            case "3": //  Modifier le ID de l'employé
                // TODO
                break;
            default:
                break;
        }
    }

    public void EmployeWorkStatusReport() {
        String startDate = view.promptStartDateSelection();
        String endDate = view.promptEndDateSelection();

        view.displayRapport(model.getEmployeWorkStatusReport(currentEmployee.getID()));

        view.promptEnterToContinue();
    }

    public void EmployeRapportMenu() {
        String rapportSelection = view.promptEmployeRapportMenuSelection();

        switch (rapportSelection) {
            case "1": // Générer un rapport d'état pour un projet
                List<Project> projectList = model.getProjectList();

                String projectSelection = view.promptProjectSelection(projectList);
                int projectIndex = Integer.parseInt(projectSelection);

                Project project = projectList.get(projectIndex - 1);

                view.displayRapport(model.getRapportEtatProjet(project.getID()));

                break;
            case "2": // Générer un rapport d'état global
                view.displayRapport(model.getRapportEtatGlobale());

                break;
            case "3": // Générer un rapport d'état employé
                EmployeStatusReportMenu();

                break;
            case "4": // Générer un talon de paie employé
                EmployeTalonPaieMenu();

                break;
            default:
                break;
        }

        EmployeeMainMenu();
    };

    public void EmployeStatusReportMenu() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(model.getRapportEtatEmploye(currentEmployee.getID()));
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();

                view.displayRapport(model.getRapportEtatEmploye(currentEmployee.getID(), dateDebut, dateFin));
                break;
            default:
                break;
        }

    }

    public void EmployeTalonPaieMenu() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(model.getTalonPaieEmploye(currentEmployee.getID()));
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();

                view.displayRapport(model.getTalonPaieEmploye(currentEmployee.getID(), dateDebut, dateFin));
                break;
            default:
                break;
        }

    }

    public void StartTaskMenu() {
        // Selectionne un projet
        List<Project> projectList = currentEmployee.getProjectsAssignesList();

        String projectSelection = view.promptProjectSelection(projectList);
        int projectIndex = Integer.parseInt(projectSelection);

        Project project = projectList.get(projectIndex - 1);
        List<Discipline> disciplineList = project.getDisciplinesList();

        // Selectionne une discipline
        String disciplineSelection = view.promptDisciplineSelection(disciplineList);
        int disciplineIndex = Integer.parseInt(disciplineSelection);

        Discipline discipline = disciplineList.get(disciplineIndex - 1);

        String startDecision = view.promptStartTimer(project.toString() + discipline.toString());

        switch (startDecision) {
            case "y":

                employeLog = model.startTask(currentEmployee, project, discipline);

                displayEndTaskMenu();

                break;
            case "n":
                EmployeeMainMenu();
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

                EmployeeMainMenu();
                break;
            case "n":
                displayEndTaskMenu();
                break;
            default:
                break;
        }

    }

}
