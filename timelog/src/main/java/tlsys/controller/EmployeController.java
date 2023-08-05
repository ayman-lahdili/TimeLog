package tlsys.controller;

import java.util.List;

import tlsys.model.Discipline;
import tlsys.model.Employe;
import tlsys.model.EmployeLog;
import tlsys.model.Project;
import tlsys.model.Rapport;
import tlsys.model.TimeLogModel;
import tlsys.view.EmployeView;

public class EmployeController {
    private TimeLogModel model;
    private EmployeView view;
    private Employe currentEmployee;
    private EmployeLog employeLog;
    private Rapport rapports;

    public EmployeController(TimeLogModel model, EmployeView view) {
        this.model = model;
        this.view = view;
        this.rapports = new Rapport(model);
    }

    public void login() {
        int ID = Integer.parseInt(view.promptLoginEmployeID());
        String employeeUsername = view.promptLoginEmployeUsername();

        Employe user = model.authenticateEmploye(ID, employeeUsername);

        if (user != null) {
            currentEmployee = user;
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
            case "4":
                logout();
            default:
                break;
        }

    }

    public void EmployeRapportMenu() {
        String rapportSelection = view.promptEmployeRapportMenuSelection();

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
                EmployeStatusReportMenu();
                break;
            case "4": // Générer un talon de paie employé
                EmployeTalonPaieMenu();
                break;
            default:
                break;
        }

        MainMenu();
    }

    public void EmployeWorkStatusReport() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), "default", "default"));
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), dateDebut, dateFin));
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

    public void EmployeStatusReportMenu() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1": // la dernière période de paie
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID()));
                break;
            case "2": // à partir du période que vous choisissez
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), dateDebut, dateFin));
                break;
            default:
                break;
        }

    }
    
    public void StartTaskMenu() {
        // 1. Selectionne un projet qu'il est assigné
        List<Project> projectList = currentEmployee.getProjectsAssignesList();

        String projectSelection = view.promptProjectSelection(projectList);
        int projectIndex = Integer.parseInt(projectSelection);

        Project project = projectList.get(projectIndex - 1);
        List<Discipline> disciplineList = project.getDisciplinesList();

        // 2. Selectionne une discipline du projet sélectionné
        String disciplineSelection = view.promptDisciplineSelection(disciplineList);
        int disciplineIndex = Integer.parseInt(disciplineSelection);

        Discipline discipline = disciplineList.get(disciplineIndex - 1);

        //3. Décide de commencer ou pas
        String startDecision = view.promptStartTimer(project.toString() + discipline.toString());

        switch (startDecision) {
            case "y":
                employeLog = model.startTask(currentEmployee, project, discipline);
                displayEndTaskMenu();
                break;
            case "n":
                MainMenu();
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

                MainMenu();
                break;
            case "n":
                displayEndTaskMenu();
                break;
            default:
                break;
        }

    }

}
