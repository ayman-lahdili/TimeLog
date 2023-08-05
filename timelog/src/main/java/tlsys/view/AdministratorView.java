package tlsys.view;

public class AdministratorView extends TimeLogView {

    public String promptLoginAdministratorUsername() {
        System.out.print("Entrer votre nom d'utilisateur");
        return scanner.nextLine();
    }

    public String promptLoginAdministratorPassword() {
        System.out.print("Entrer votre mot de pass");
        return scanner.nextLine();
    }

    public String promptAdministratorMainMenu() {
        System.out.println("[1] Faire des modifications aux paramètres du système\n[2] Générer des rapports");
        return scanner.nextLine();
    }

    public String promptAdministratorModificationMenu() {
        System.out.println("[1] Modifier les paramètres employés [2] Modifier les paramètres de projets");
        return scanner.nextLine();
    }

    public String promptAdministratorEmplpoyeeModificationMenu() {
        System.out.println("[1] Modifier le NPE [2] Modifier les paramètres d'un employé");
        return scanner.nextLine();
    }

    public String promptAdministratorProjectModificationMenu() {
        System.out.println(
                "[1] Assignation des employés à des projets\n[2] Modifier la liste des employés\n[3] Modifier la liste des projets");
        return scanner.nextLine();
    }

    public String promptAdministratorModificationInputSelection() {
        System.out.println("Entrer le nouveau champ que");
        return scanner.nextLine();
    }

    public String promptAdministratorGetEmployeeID() {
        System.out.println("Entrer le ID de l'employé");
        return scanner.nextLine();
    }

    public String promptAdministratorEmployeeParamsModificationMenu() {
        System.out.println(
                "[1] Modifier l'assignation des employés à des projets\n[2] Modifier le noms d'usager de l'employé\n[3] Modifier le ID de l'employé");
        return scanner.nextLine();
    }

    public String promptAdministratorMenuSelection() {
        System.out.println("[1]\tGénérer un rapport d'état pour un projet");
        System.out.println("[2]\tGénérer un rapport d'état global");
        System.out.println("[3]\tGénérer un rapport d'état un employé");
        System.out.println("[4]\tGénérer les talons de paie pour tous les employés");
        return scanner.nextLine();
    }

}
