package tlsys.view;

public class EmployeView extends TimeLogView {

    public String promptLoginEmployeUsername() {
        System.out.print("Entrer votre nom d'utilisateur");
        return scanner.nextLine();
    }

    public String promptLoginEmployeID() {
        System.out.print("Entrer votre ID");
        return scanner.nextLine();
    }

    public String promptEmployeMainMenu() {
        System.out.println(
                "[1] Commencer un tâches\n[2] Générer des rapport\n[3] Obtenir le nombre d'heures travaillées\n[4] Vous déconnecter");
        return scanner.nextLine();
    }

    public String promptEmployeRapportMenuSelection() {
        System.out.println("[1]\tGénérer un rapport d'état pour un projet");
        System.out.println("[2]\tGénérer un rapport d'état global");
        System.out.println("[3]\tGénérer un rapport d'état employé");
        System.out.println("[4]\tGénérer un talon de paie pour une période");
        return scanner.nextLine();
    }

    public String promptStartTimer(String task) {
        System.out.println("Vouler vous commencer l'activité" + task + "(y/n)");
        return scanner.nextLine();
    }

    public String promptEndTimer() {
        System.out.println("Vouler vous quitter cette tâche (y/n)");
        return scanner.nextLine();
    }

}
