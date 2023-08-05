package tlsys.controller;

import java.util.ArrayList;
import java.util.List;

import tlsys.model.Administrator;
import tlsys.model.Employe;
import tlsys.model.Project;
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
        String adminAction = view.promptEmployeeModificationMenu();

        switch (adminAction) {
            case "1": // Modifier le NPE                
                NPEModificationMenu();
                break;
            case "2": // Modifier les paramètres d'un employé
                EmployeeParamsModificationMenu();
                break;
            case "3": // Modifier la liste des employés
                EmployeeListModificationMenu();
                break;
            default:
                break;
        }
    }

    public void NPEModificationMenu() {
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
    }

    public void EmployeeListModificationMenu() {
        String modificationType = view.promptAddOrRemoveMenu("Employé");

        switch (modificationType) {
            case "1": // ajouter
                addEmployeeMenu();
                break;
            case "2": // retirer
                removeEmployeeMenu();
                break;
            default:
                break;
        }
    }

    public void addEmployeeMenu() {
        int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
        String nom = view.promptNewParameterDecision("nom");
        String dateEmbauche = view.promptNewParameterDecision("dateEmbauche");
        String dateDepart = view.promptNewParameterDecision("dateDepart");
        int NAS = Integer.parseInt(view.promptNewParameterDecision("NAS"));
        int numeroPoste = Integer.parseInt(view.promptNewParameterDecision("numeroPoste"));
        double tauxHoraireBase = Double.parseDouble(view.promptNewParameterDecision("tauxHoraireBase"));
        double tauxHoraireTempsSupplementaire = Double.parseDouble(view.promptNewParameterDecision("tauxHoraireTempsSupplementaire"));

        String modificationDecision =  view.promptConfirmationObjectAddition("Employee");
        
        switch (modificationDecision) {
            case "y":;
                view.displayModifySuccessMessage(model.addEmployee());//TODO
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void removeEmployeeMenu() {
        String adminAction = view.promptConfirmationObjectRemoval("Employé");

        int employeID = Integer.parseInt(view.promptAdministratorGetEmployeeID());
        Employe employe_to_remove = model.getEmployeByID(employeID);

        switch (adminAction) {
            case "y":;
                view.displayModifySuccessMessage(model.removeEmployee(employe_to_remove));//TODO
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void EmployeeParamsModificationMenu() {
        String adminAction = view.promptEmployeeParamsModificationMenu();
        
        int employeID = Integer.parseInt(view.promptAdministratorGetEmployeeID());

        Employe employe_to_modify = model.getEmployeByID(employeID);

        switch (adminAction) {
            case "1": // Modifier l'assignation des employés à des projets
                // TODO
                List<Project> newEmployeProjectList = new ArrayList<Project>();
                String continueDecision = view.promptContinueDecision();
                
                while (continueDecision!="y") {
                    List<Project> projectList = model.getProjectList();
                    String projectSelection = view.promptProjectSelection(projectList);
                    int projectIndex = Integer.parseInt(projectSelection);
                    Project project = projectList.get(projectIndex - 1);
                    newEmployeProjectList.add(project);
                    continueDecision = view.promptContinueDecision();
                }
                
                view.displayModifySuccessMessage(employe_to_modify.setProjectsAssignesList(newEmployeProjectList));

                break;
            case "2": // Modifier le noms d'usager de l'employé
                // TODO
                String nom = view.promptNewParameterDecision("nom");
                view.displayModifySuccessMessage(employe_to_modify.setNom(nom));

                break;
            case "3": //  Modifier le ID de l'employé
                // TODO
                int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
                view.displayModifySuccessMessage(employe_to_modify.setID(ID));

                break;
            default:
                break;
        }
    }

}
