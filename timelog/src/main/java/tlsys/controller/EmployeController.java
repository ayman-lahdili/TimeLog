package tlsys.controller;

import java.util.List;

import tlsys.model.Discipline;
import tlsys.model.Employe;
import tlsys.model.EmployeLog;
import tlsys.model.Project;
import tlsys.model.TimeLogModel;
import tlsys.view.TimeLogView;

public class EmployeController {
    private TimeLogModel model;
    private TimeLogView view;
    private Employe currentEmployee;
    private EmployeLog employeLog;

    public EmployeController(TimeLogModel model, TimeLogView view) {
        this.model = model;
        this.view = view;
    }

    public void login() {
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
    }

    public void EmployeWorkStatusReport() {
        String startDate = view.promptStartDateSelection();
        String endDate = view.promptEndDateSelection();

        view.displayRapport(model.getEmployeWorkStatusReport(currentEmployee.getID()));

        view.promptEnterToContinue();
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
