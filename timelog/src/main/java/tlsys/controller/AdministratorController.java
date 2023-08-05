package tlsys.controller;

import java.util.ArrayList;
import java.util.List;

import tlsys.model.Administrator;
import tlsys.model.Discipline;
import tlsys.model.Employe;
import tlsys.model.Project;
import tlsys.model.Rapport;
import tlsys.model.TimeLogModel;
import tlsys.view.AdministratorView;

public class AdministratorController {
    private TimeLogModel model;
    private AdministratorView view;
    private Administrator currentAdmin;
    private Rapport rapports;

    public AdministratorController(TimeLogModel model, AdministratorView view) {
        this.model = model;
        this.view = view;
        this.rapports = new Rapport(model);
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
                RapportMenu();
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
                ProjectModificationMenu();
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
        String modificationDecision = view.promptModificationDecision("NPE", model.getNPE());

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
        double tauxHoraireTempsSupplementaire = Double
                .parseDouble(view.promptNewParameterDecision("tauxHoraireTempsSupplementaire"));

        String modificationDecision = view.promptConfirmationObjectAddition("Employee");

        switch (modificationDecision) {
            case "y":
                ;
                view.displayModifySuccessMessage(model.addEmployee());// TODO
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
            case "y":
                ;
                view.displayModifySuccessMessage(model.removeEmployee(employe_to_remove));// TODO
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

                while (continueDecision != "y") {
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
            case "3": // Modifier le ID de l'employé
                // TODO
                int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
                view.displayModifySuccessMessage(employe_to_modify.setID(ID));

                break;
            default:
                break;
        }
    }

    public void ProjectModificationMenu() {
        String adminAction = view.promptProjectModificationMenu();

        switch (adminAction) {
            case "1": // Modifier un projet
                ProjectParamsModificationMenu();
                break;
            case "2": // Modifier la liste des projets
                ProjectListModificationMenu();
                break;
            default:
                break;
        }
    }

    public void ProjectParamsModificationMenu() {
        String prompt = view.promptAdministratorGetProjectID();

        int projectID = Integer.parseInt(view.promptAdministratorGetProjectID());
        Project projectToModify = model.getProjectByID(projectID);

        String adminAction = view.promptProjectParamsModificationMenu();

        switch (adminAction) {
            case "1": // Modifier le nom du projet
                projectNameModificationMenu(projectToModify);
                break;
            case "2": // Modifier la date de fin d'un projet
                projectEndDateModificationMenu(projectToModify);
                break;
            case "3": // Modifier la date de début du projet
                projectStartDateModificationMenu(projectToModify);
                break;
            case "4": // Modifier le nombre d'heures budgétées pour une disciplines
                projectHeuresBudgeteeModificationMenu(projectToModify);
                break;
            default:
                break;
        }
    }

    public void projectNameModificationMenu(Project projectToModify) {
        String modificationDecision = view.promptModificationDecision("Nom", projectToModify.getName());

        switch (modificationDecision) {
            case "y":
                String newName = view.promptAdministratorModificationInputSelection();
                view.displayModifySuccessMessage(projectToModify.setName(newName));
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void projectStartDateModificationMenu(Project projectToModify) {
        String modificationDecision = view.promptModificationDecision("Date de début", projectToModify.getDateDebut());

        switch (modificationDecision) {
            case "y":
                String newDateDebut = view.promptAdministratorModificationInputSelection();
                view.displayModifySuccessMessage(projectToModify.setDateDebut(newDateDebut));
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void projectEndDateModificationMenu(Project projectToModify) {
        String modificationDecision = view.promptModificationDecision("Date de fin", projectToModify.getDateFin());

        switch (modificationDecision) {
            case "y":
                String newDateFin = view.promptAdministratorModificationInputSelection();
                view.displayModifySuccessMessage(projectToModify.setDateFin(newDateFin));
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void projectHeuresBudgeteeModificationMenu(Project projectToModify) {
        List<Discipline> disciplineList = projectToModify.getDisciplinesList();

        // 2. Selectionne une discipline du projet sélectionné
        String disciplineSelection = view.promptDisciplineSelection(disciplineList);
        int disciplineIndex = Integer.parseInt(disciplineSelection);

        Discipline discipline = disciplineList.get(disciplineIndex - 1);

        String modificationDecision = view.promptModificationDecision("Heures budgétées "+ discipline, discipline.getHeuresBudgetees());

        switch (modificationDecision) {
            case "y":
                int newNbrHeuresBudgetee = Integer.parseInt(view.promptAdministratorModificationInputSelection());
                view.displayModifySuccessMessage(discipline.setHeuresBudgetees(projectToModify, newNbrHeuresBudgetee));
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void ProjectListModificationMenu() {
        String modificationType = view.promptAddOrRemoveMenu("Projets");

        switch (modificationType) {
            case "1": // ajouter
                addProjectMenu();
                break;
            case "2": // retirer
                removeProjectMenu();
                break;
            default:
                break;
        }
    }

    public void addProjectMenu() {
        int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
        String name = view.promptNewParameterDecision("nom");
        String dateDebut = view.promptNewParameterDecision("date de début");
        String dateFin = view.promptNewParameterDecision("date de fin");

        List<Discipline> disciplinesList = createDisciplineListMenu();

        String modificationDecision = view.promptConfirmationObjectAddition("Project");

        switch (modificationDecision) {
            case "y":
                ;
                view.displayModifySuccessMessage(model.addProject());// TODO
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public List<Discipline> createDisciplineListMenu() {
        String adminAction = view.promptSetDisciplineListMenu();
        List<Discipline> disciplinesList = new ArrayList<Discipline>();

        switch (adminAction) {
            case "1":
                disciplinesList = model.getdefaultDisciplineList();
                break;
            case "2":
                String continueDecision = view.promptContinueDecision();

                while (continueDecision != "y") {
                    String name = view.promptNewParameterDecision("Nom");
                    int heuresBudgetees = Integer.parseInt(view.promptNewParameterDecision("Heures Budgetees"));
                    Discipline discipline = model.createNewDiscipline(name, heuresBudgetees);
                    disciplinesList.add(discipline);
                    continueDecision = view.promptContinueDecision();
                }
            default:
                break;
        }

        return disciplinesList;
    }

    public void removeProjectMenu() {
        String adminAction = view.promptConfirmationObjectRemoval("Projet");

        int projectID = Integer.parseInt(view.promptAdministratorGetProjectID());
        Project project_to_remove = model.getProjectByID(projectID);

        switch (adminAction) {
            case "y":
                view.displayModifySuccessMessage(model.removeProject(project_to_remove));// TODO
                break;
            case "n":
                break;
            default:
                break;
        }
        MainMenu();
    }

    public void RapportMenu() {
        String rapportSelection = view.promptAdministratorRapportMenuSelection();

        switch (rapportSelection) {
            case "1": // Générer un rapport d'état pour un projet
                List<Project> projectList = model.getProjectList();
                String projectSelection = view.promptProjectSelection(projectList);
                int projectIndex = Integer.parseInt(projectSelection);
                Project project = projectList.get(projectIndex - 1);

                view.displayRapport(rapports.getRapportEtatProjet(project.getID()));
                break;
            case "2": // Générer un rapport d'état global
                view.displayRapport(rapports.getRapportEtatGlobale());
                break;
            case "3": // Générer un rapport d'état employé
                AdministratorStatusReportMenu();
                break;
            case "4": // Générer un talon de paie employé
                EmployeTalonPaieMenu();
                break;
            default:
                break;
        }

        MainMenu();
    }

    public void AdministratorStatusReportMenu() {
        int employeID = Integer.parseInt(view.promptAdministratorGetEmployeeID());
        Employe employe = model.getEmployeByID(employeID);

        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1": // la dernière période de paie
                view.displayRapport(rapports.getRapportEtatEmploye(employe.getID()));
                break;
            case "2": // à partir du période que vous choisissez
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getRapportEtatEmploye(employe.getID(), dateDebut, dateFin));
                break;
            default:
                break;
        }

    }

    public void EmployeTalonPaieMenu() {
        int employeID = Integer.parseInt(view.promptAdministratorGetEmployeeID());
        Employe employe = model.getEmployeByID(employeID);

        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(model.getTalonPaieEmploye(employe.getID()));
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(model.getTalonPaieEmploye(employe.getID(), dateDebut, dateFin));
                break;
            default:
                break;
        }

    }

}
