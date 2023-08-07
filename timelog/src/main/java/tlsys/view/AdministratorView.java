package tlsys.view;

public class AdministratorView extends TimeLogView {

    public String promptLoginAdministratorPassword() {
        System.out.print("Veuillez entrer votre mot de passe:\t");
        return scanner.nextLine();
    }

    public String promptMainMenu() {
        System.out.println("[1]\tFaire des modifications aux paramètres du système");
        System.out.println("[2]\tGénérer des rapports");
        System.out.println("[3]\tVous déconnecter");
        return scanner.nextLine();
    }

    public String promptModificationMenu() {
        System.out.println("[1]\tModifier les paramètres des employés") ;
        System.out.println("[2]\tModifier les paramètres de projets");
        return scanner.nextLine();
    }

    public String promptSetDisciplineListMenu() {
        System.out.println("[1]\tUtiliser la liste de discipline par défault");
        System.out.println("[2]\tCréer ma propre liste");
        return scanner.nextLine();
    }

    public String promptModificationEmployeMenu() {
        System.out.println("[1]\tModifier le NPE");
        System.out.println("[2]\tModifier les paramètres d'un employé");
        System.out.println("[3]\tModifier la liste des employés");
        return scanner.nextLine();
    }

    public String promptModificationProjectMenu() {
        System.out.println("[1]\tModifier un projet");
        System.out.println("[2]\tModifier la liste des projets");
        return scanner.nextLine();
    }

    public String promptAddOrRemoveMenu(String listToModifiy) {
        System.out.println("[1]\tAjouter un "+listToModifiy);
        System.out.println("[2]\tRetirer un "+listToModifiy);
        return scanner.nextLine();
    }

    public String promptModificationInputSelection() {
        System.out.print("Nouvelle valeur:\t");
        return scanner.nextLine();
    }

    public String promptRapportMenuSelection() {
        System.out.println("[1]\tGénérer un rapport d'état pour un projet");
        System.out.println("[2]\tGénérer un rapport d'état global");
        System.out.println("[3]\tGénérer le rapport d'état d'un employé");
        System.out.println("[4]\tGénérer le talon de paie d'un employé");
        System.out.println("[5]\tGénérer le talon de paie global");
        return scanner.nextLine();
    }

    public String promptGetEmployeID() {
        System.out.print("Entrer le ID de l'employé:\t");
        return scanner.nextLine();
    }

    public String promptAdministratorGetProjectID() {
        System.out.print("Entrer le ID du projet:\t");
        return scanner.nextLine();
    }

    public String promptProjectParamsModificationMenu() {
        System.out.println("[1]\tModifier le nom du projet");
        System.out.println("[2]\tModifier la date de fin d'un projet");
        System.out.println("[3]\tModifier la date de début du projet");
        System.out.println("[4]\tModifier le nombre d'heures budgétées pour une disciplines");
        return scanner.nextLine();
    }

    public String promptModificationParametreEmployeMenu() {
        System.out.println("[1]\tModifier la liste des projets assignée à l'employé");
        System.out.println("[2]\tModifier le noms d'usager de l'employé");
        System.out.println("[3]\tModifier le ID de l'employé");
        return scanner.nextLine();
    }

}
