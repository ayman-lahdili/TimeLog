package tlsys.controller;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.TimeLogModel;
import tlsys.view.AdministratorView;
import tlsys.view.TimeLogView;

public class AdministratorController {
    private TimeLogModel model;
    private AdministratorView view;
    private Administrator currentAdmin;

    public AdministratorController(TimeLogModel model, AdministratorView view) {
        this.model = model;
        this.view = view;
    }

    public void login() {
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
    }

    public void AdministratorMenu() {
        String adminAction = view.promptAdministratorMainMenu();

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

}
