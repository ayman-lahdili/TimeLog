package tlsys.controller;

import java.util.List;

import tlsys.model.Discipline;
import tlsys.model.Employe;
import tlsys.model.EmployeLog;
import tlsys.model.PayInfo;
import tlsys.model.PayRoll;
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
    private PayInfo payInfo;
    private PayRoll payroll;

    public EmployeController(TimeLogModel model, EmployeView view) {
        this.model = model;
        this.view = view;
        this.rapports = new Rapport(model);
        this.payInfo = new PayInfo(1, 40, 20, 34.50, 45, "2023-07-30", "2023-08-04");
        this.payroll = new PayRoll(model, payInfo);
    }

    public void login() {
        try {
            String ID = view.promptLoginEmployeID();
            String employeeUsername = view.promptLoginUsername();

            Employe user = model.authenticateEmploye(Integer.parseInt(ID), employeeUsername);

            if (user != null) {
                currentEmployee = user;
                view.displayLoginSuccessMessage();
                mainMenu();
            } else {
                view.displayLoginErrorMessage();
                login();
            }
        } catch (Exception e) {
            view.displayLoginErrorMessage();
            login();
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
        String employeeAction = view.promptMainMenu();

        switch (employeeAction) {
            case "1": // Commencer une tâche
                startTaskMenu();
                break;
            case "2": // Générer des rapports
                EmployeRapportMenu();
                break;
            case "3": // Obtenir le nombre d'heures travaillées
                EmployeWorkStatusReport();
                break;
            case "4": // Générer votre talon de paie avec le système Payroll
                //TODO
                payRollMenu();
                break;                
            case "5": // Logout
                logout();
            default:
                break;
        }

    }

    public void payRollMenu() {
        PayInfo employePayInfo = payroll.printPay(payInfo);
        System.out.println(employePayInfo.toString());
        mainMenu();
    }

    public void EmployeRapportMenu() {
        String rapportSelection = view.promptRapportMenu();

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
                employeRapportEtatMenu();
                break;
            case "4": // Générer un talon de paie employé
                EmployeTalonPaieMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                EmployeRapportMenu();
                break;
        }
    }

    public void EmployeWorkStatusReport() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), "default", "default"));
                mainMenu();
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), dateDebut, dateFin));
                mainMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                EmployeWorkStatusReport();
                break;
        }
    }

    public void EmployeTalonPaieMenu() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1":
                view.displayRapport(rapports.getTalonPaietoString(currentEmployee.getID(), "default", "default"));
                mainMenu();
                break;
            case "2":
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getTalonPaietoString(currentEmployee.getID(), dateDebut, dateFin));
                mainMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                EmployeTalonPaieMenu();
                break;
        }

    }

    public void employeRapportEtatMenu() {
        String timePeriodeSelection = view.promptStartDateType();

        switch (timePeriodeSelection) {
            case "1": // la dernière période de paie
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), "default", "default"));
                mainMenu();
                break;
            case "2": // à partir du période que vous choisissez
                String dateDebut = view.promptStartDateSelection();
                String dateFin = view.promptEndDateSelection();
                view.displayRapport(rapports.getRapportEtatEmploye(currentEmployee.getID(), dateDebut, dateFin));
                mainMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                employeRapportEtatMenu();
                break;
        }

    }
    
    public void startTaskMenu() {
        // 1. Selectionne un projet qu'il est assigné
        List<Project> projectList = currentEmployee.getProjectsAssignesList();

        String projectSelection = view.promptProjectSelection(projectList);
        int projectIndex = Integer.parseInt(projectSelection);

        Project project = projectList.get(projectIndex - 1);

        // 2. Selectionne une discipline du projet sélectionné
        List<Discipline> disciplineList = project.getDisciplinesList();
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
                mainMenu();
                break;
            default:
                view.displayInvalidInputWarning();
                startTaskMenu();
                break;
        }

    }

    public void displayEndTaskMenu() {
        String endTaskDecision = view.promptEndTimer();

        switch (endTaskDecision) {
            case "y":
                model.endTask(employeLog);

                mainMenu();
                break;
            case "n":
                displayEndTaskMenu();
                break;
            default:
                displayEndTaskMenu();
                break;
        }

    }

}
