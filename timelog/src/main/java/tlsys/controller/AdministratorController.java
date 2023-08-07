package tlsys.controller;

import java.util.ArrayList;
import java.util.List;

import tlsys.model.Administrator;
import tlsys.model.Discipline;
import tlsys.model.Employe;
import tlsys.model.PayInfo;
import tlsys.model.PayRoll;
import tlsys.model.Project;
import tlsys.model.Rapport;
import tlsys.model.TimeLogModel;
import tlsys.view.AdministratorView;

public class AdministratorController {
    private TimeLogModel model;
    private AdministratorView view;
    private Administrator currentAdmin;
    private Rapport rapports;
    private PayInfo payInfo;
    private PayRoll payroll;

    public AdministratorController(TimeLogModel model, AdministratorView view) {
        this.model = model;
        this.view = view;
        this.rapports = new Rapport(model);
        this.payInfo = new PayInfo(1, 40, 20, 34.50, 45, "2023-07-30", "2023-08-04");
        this.payroll = new PayRoll(model, payInfo);
    }

    public void login() {
        String adminUsername = view.promptLoginUsername();
        String password = view.promptLoginAdministratorPassword();

        Administrator admin = model.authenticateAdministrator(adminUsername, password);

        if (admin != null) {
            currentAdmin = admin;
            view.displayLoginSuccessMessage();
            mainMenu();
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
                mainMenu();
                break;
        }
    }

    public void mainMenu() {
        String adminAction = view.promptMainMenu();

        switch (adminAction) {
            case "1": // Faire des modifications au paramètre du système
                modificationMenu();
                break;
            case "2": // Générer des rapports
                rapportMenu();
                break;
            case "3": // Logout
                logout();
                break;
            default:
                break;
        }
    }

    public void modificationMenu() {
        String adminModificationChoice = view.promptModificationMenu();

        switch (adminModificationChoice) {
            case "1": // Modifier les paramètres employés
                employeeModificationMenu();
                break;
            case "2": // Modifier les paramètres de projets
                ProjectModificationMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                modificationMenu();
                break;
        }

    }

    public void employeeModificationMenu() {
        String adminAction = view.promptModificationEmployeMenu();

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
                view.displayInvalidInputWarning();
                employeeModificationMenu();
                break;
        }
    }

    public void NPEModificationMenu() {
        try {
            String modificationDecision = view.promptModificationDecision("NPE", model.getNPE());

            switch (modificationDecision) {
                case "y":
                    int newNPE = Integer.parseInt(view.promptModificationInputSelection());
                    view.displayModifySuccessMessage(model.setNPE(newNPE));
                    mainMenu();
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    NPEModificationMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            NPEModificationMenu();
        }
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
                view.displayInvalidInputWarning();
                EmployeeListModificationMenu();
                break;
        }
    }

    public void addEmployeeMenu() {
        try {
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
                    Employe newEmploye = model.createEmploye(ID, nom, dateEmbauche, dateDepart, NAS, numeroPoste, tauxHoraireBase, tauxHoraireTempsSupplementaire);
                    view.displayModifySuccessMessage(model.post(newEmploye));
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    addEmployeeMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            addEmployeeMenu();
        }

    }

    public void removeEmployeeMenu() {

        try {
            int employeID = Integer.parseInt(view.promptGetEmployeID());
            Employe employe_to_remove = model.getEmployeByID(employeID);
            String adminAction = view.promptConfirmationObjectRemoval("Employé");

            if (employe_to_remove != null) {
                switch (adminAction) {
                    case "y":
                        view.displayModifySuccessMessage(model.delete(employe_to_remove));// TODO
                        mainMenu();
                        break;
                    case "n":
                        mainMenu();
                        break;
                    default:
                        mainMenu();
                        break;
                }                
            } else {
                view.displayInexistantDataWarning();
                removeEmployeeMenu();
            }           

        } catch (Exception e) {
            view.displayInvalidInputWarning();
            removeEmployeeMenu();
        }

    }

    public void EmployeeParamsModificationMenu() {
        try {
            String adminAction = view.promptModificationParametreEmployeMenu();

            int employeID = Integer.parseInt(view.promptGetEmployeID());
            Employe employe_to_modify = model.getEmployeByID(employeID);

            switch (adminAction) {
                case "1": // Modifier l'assignation des employés à des projets
                    // TODO
                    List<Project> newEmployeProjectList = new ArrayList<Project>();

                    for (int i = 0; i < model.getNPE(); i++) {
                        List<Project> projectList = model.getProjectList();
                        int projectIndex = Integer.parseInt(view.promptProjectSelection(projectList));
                        Project project = projectList.get(projectIndex - 1);
                        newEmployeProjectList.add(project);
                    }

                    view.displayModifySuccessMessage(employe_to_modify.setProjectsAssignesList(newEmployeProjectList));
                    mainMenu();
                    break;
                case "2": // Modifier le noms d'usager de l'employé
                    String nom = view.promptNewParameterDecision("nom");
                    view.displayModifySuccessMessage(employe_to_modify.setNom(nom));
                    mainMenu();
                    break;
                case "3": // Modifier le ID de l'employé
                    int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
                    view.displayModifySuccessMessage(employe_to_modify.setID(ID));
                    mainMenu();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            EmployeeParamsModificationMenu();
        }

    }

    public void ProjectModificationMenu() {
        String adminAction = view.promptModificationProjectMenu();

        switch (adminAction) {
            case "1": // Modifier un projet
                ProjectParamsModificationMenu();
                break;
            case "2": // Modifier la liste des projets
                ProjectListModificationMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                ProjectModificationMenu();
                break;
        }
    }

    public void ProjectParamsModificationMenu() {
        try {
            List<Project> projectList = model.getProjectList();

            String projectSelection = view.promptProjectSelection(projectList);
            int projectIndex = Integer.parseInt(projectSelection);
    

            Project projectToModify = projectList.get(projectIndex - 1);

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
                    view.displayInvalidInputWarning();
                    ProjectParamsModificationMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            ProjectParamsModificationMenu();
        }

    }

    public void projectNameModificationMenu(Project projectToModify) {
        try {
            String modificationDecision = view.promptModificationDecision("Nom", projectToModify.getName());

            switch (modificationDecision) {
                case "y":
                    String newName = view.promptModificationInputSelection();
                    view.displayModifySuccessMessage(projectToModify.setName(newName));
                    mainMenu();
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    projectNameModificationMenu(projectToModify);
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            projectNameModificationMenu(projectToModify);
        }
    }

    public void projectStartDateModificationMenu(Project projectToModify) {
        try {
            String modificationDecision = view.promptModificationDecision("Date de début",
                    projectToModify.getDateDebut());

            switch (modificationDecision) {
                case "y":
                    String newDateDebut = view.promptModificationInputSelection();
                    if (projectToModify.setDateDebut(newDateDebut)) {
                        view.displayModifySuccessMessage(true);
                        mainMenu();
                    } else {
                        projectStartDateModificationMenu(projectToModify);
                    }
                    break;
                case "n":
                    mainMenu();
                    projectNameModificationMenu(projectToModify);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            projectNameModificationMenu(projectToModify);
        }
    }

    public void projectEndDateModificationMenu(Project projectToModify) {
        try {
            String modificationDecision = view.promptModificationDecision("Date de Fin", projectToModify.getDateFin());

            switch (modificationDecision) {
                case "y":
                    String newDateDebut = view.promptModificationInputSelection();
                    if (projectToModify.setDateFin(newDateDebut)) {
                        view.displayModifySuccessMessage(true);
                        mainMenu();
                    } else {
                        projectStartDateModificationMenu(projectToModify);
                    }
                    break;
                case "n":
                    mainMenu();
                    projectNameModificationMenu(projectToModify);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            projectNameModificationMenu(projectToModify);
        }
    }

    public void projectHeuresBudgeteeModificationMenu(Project projectToModify) {
        try {
            List<Discipline> disciplineList = projectToModify.getDisciplinesList();

            // 2. Selectionne une discipline du projet sélectionné
            String disciplineSelection = view.promptDisciplineSelection(disciplineList);
            int disciplineIndex = Integer.parseInt(disciplineSelection);

            Discipline discipline = disciplineList.get(disciplineIndex - 1);

            String modificationDecision = view.promptModificationDecision("Heures budgétées " + discipline,
                    discipline.getHeuresBudgetees());

            switch (modificationDecision) {
                case "y":
                    int newNbrHeuresBudgetee = Integer.parseInt(view.promptModificationInputSelection());
                    view.displayModifySuccessMessage(
                            discipline.setHeuresBudgetees(projectToModify, newNbrHeuresBudgetee));
                    mainMenu();
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    projectHeuresBudgeteeModificationMenu(projectToModify);
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            projectHeuresBudgeteeModificationMenu(projectToModify);
        }

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
                view.displayInvalidInputWarning();
                ProjectListModificationMenu();
                break;
        }
    }

    public void addProjectMenu() {
        try {
            int ID = Integer.parseInt(view.promptNewParameterDecision("ID"));
            String name = view.promptNewParameterDecision("nom");
            String dateDebut = view.promptNewParameterDecision("date de début");
            String dateFin = view.promptNewParameterDecision("date de fin");

            List<Discipline> disciplinesList = createDisciplineListMenu();

            String modificationDecision = view.promptConfirmationObjectAddition("Project");

            switch (modificationDecision) {
                case "y":
                    Project newProject = model.createProject(ID, name, dateDebut, dateFin, disciplinesList);
                    view.displayModifySuccessMessage(model.post(newProject));
                    mainMenu();
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    addProjectMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            addProjectMenu();
        }
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
                    continueDecision = "n";
                    String name = view.promptNewParameterDecision("Nom");
                    int heuresBudgetees = Integer.parseInt(view.promptNewParameterDecision("Heures Budgetees"));
                    Discipline discipline = model.createNewDiscipline(name, heuresBudgetees);
                    disciplinesList.add(discipline);
                    continueDecision = view.promptContinueDecision();
                    if (continueDecision != "y")
                        break;
                }
                break;
            default:
                view.displayInvalidInputWarning();
                addProjectMenu();
                break;
        }

        return disciplinesList;
    }

    public void removeProjectMenu() {
        try {
            String adminAction = view.promptConfirmationObjectRemoval("Projet");

            int projectID = Integer.parseInt(view.promptAdministratorGetProjectID());
            Project project_to_remove = model.getProjectByID(projectID);

            switch (adminAction) {
                case "y":
                    view.displayModifySuccessMessage(model.delete(project_to_remove));
                    mainMenu();
                    break;
                case "n":
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    removeProjectMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            removeProjectMenu();
        }
    }

    public void rapportMenu() {
        try {
            String rapportSelection = view.promptRapportMenuSelection();

            switch (rapportSelection) {
                case "1": // Générer un rapport d'état pour un projet
                    List<Project> projectList = model.getProjectList();
                    String projectSelection = view.promptProjectSelection(projectList);
                    int projectIndex = Integer.parseInt(projectSelection);
                    Project project = projectList.get(projectIndex - 1);

                    view.displayRapport(rapports.getRapportEtatProjet(project.getID()));
                    mainMenu();
                    break;
                case "2": // Générer un rapport d'état global
                    view.displayRapport(rapports.getRapportEtatGlobale());
                    mainMenu();
                    break;
                case "3": // Générer un rapport d'état employé
                    rapportEtatMenu();
                    break;
                case "4": // Générer un talon de paie employé
                    EmployeTalonPaieMenu();
                    break;
                case "5":
                    EmployeTalonPaieGlobalMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    rapportMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            rapportMenu();
        }

    }

    public void rapportEtatMenu() {
        try {
            int employeID = Integer.parseInt(view.promptGetEmployeID());
            Employe employe = model.getEmployeByID(employeID);

            String timePeriodeSelection = view.promptStartDateType();

            switch (timePeriodeSelection) {
                case "1": // la dernière période de paie
                    view.displayRapport(rapports.getRapportEtatEmploye(employe.getID(), "default", "default"));
                    mainMenu();
                    break;
                case "2": // à partir du période que vous choisissez
                    String dateDebut = view.promptStartDateSelection();
                    String dateFin = view.promptEndDateSelection();
                    view.displayRapport(rapports.getRapportEtatEmploye(employe.getID(), dateDebut, dateFin));
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    rapportEtatMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            rapportEtatMenu();
        }

    }

    public void EmployeTalonPaieGlobalMenu() {
        try {
            String timePeriodeSelection = view.promptStartDateType();

            switch (timePeriodeSelection) {
                case "1":
                    view.displayRapport(rapports.getTalonPaieGlobal("default", "default"));
                    mainMenu();
                    break;
                case "2":
                    String dateDebut = view.promptStartDateSelection();
                    String dateFin = view.promptEndDateSelection();
                    view.displayRapport(rapports.getTalonPaieGlobal(dateDebut, dateFin));
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    EmployeTalonPaieMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            EmployeTalonPaieGlobalMenu();
        }
    } 

    public void EmployeTalonPaieMenu() {
        try {
            int employeID = Integer.parseInt(view.promptGetEmployeID());
            Employe employe = model.getEmployeByID(employeID);

            String timePeriodeSelection = view.promptStartDateType();

            switch (timePeriodeSelection) {
                case "1":
                    view.displayRapport(rapports.getTalonPaietoString(employe.getID(), "default", "default"));
                    mainMenu();
                    break;
                case "2":
                    String dateDebut = view.promptStartDateSelection();
                    String dateFin = view.promptEndDateSelection();
                    view.displayRapport(rapports.getTalonPaietoString(employe.getID(), dateDebut, dateFin));
                    mainMenu();
                    break;
                default:
                    view.displayInvalidInputWarning();
                    EmployeTalonPaieMenu();
                    break;
            }
        } catch (Exception e) {
            view.displayInvalidInputWarning();
            EmployeTalonPaieMenu();
        }

    }

}
