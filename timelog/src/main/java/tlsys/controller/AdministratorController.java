package tlsys.controller;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.TimeLogModel;
import tlsys.view.AdministratorView;

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
            MainMenu();
        } else {
            view.displayLoginErrorMessage();
        }
    }

    public void logout() {
        String logoutDecision = view.promptLogout();
        
        switch (logoutDecision) {
            case "y":
                break;
            default:
                MainMenu();
                break;
        }
    }

    public void MainMenu() {
        String adminAction = view.promptAdministratorMainMenu();

        switch (adminAction) {
            case "1": // Faire des modifications au paramètre du système
                ModificationMenu();
                break;
            case "2": // Générer des rapports
                
                break;
            case "3": // Logout
                logout();
                break;
            default:
                break;
        }
    }

    public void ModificationMenu() {
        String adminModificationChoice = view.promptModificationMenu();

        switch (adminModificationChoice) {
            case "1": // Modifier les paramètres employés
                EmployeeModificationMenu();
                break;
            case "2": // Modifier les paramètres de projets
                
                break;
            default:
                break;
        }

    }

    public void EmployeeModificationMenu() {
        String adminAction = view.promptAdministratorEmplpoyeeModificationMenu();

        switch (adminAction) {
            case "1": // Modifier le NPE                
                String modificationDecision =  view.promptModificationDecision("NPE", model.getNPE());
                
                switch (modificationDecision) {
                    case "y":
                        int newNPE = Integer.parseInt(view.promptAdministratorModificationInputSelection());
                        view.displayModifySuccessMessage(model.setNPE(newNPE));
                        break;
                    case "n":
                        break;
                    default:
                        break;
                }
                MainMenu();
                break;
            case "2": // Modifier les paramètres d'un employé
                EmployeeParamsModificationMenu();
                break;
            default:
                break;
        }
    }

    public void EmployeeParamsModificationMenu() {
        String adminAction = view.promptEmployeeParamsModificationMenu();

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
