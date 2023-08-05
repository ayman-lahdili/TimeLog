package tlsys.view;

public class EmployeView extends TimeLogView {

    public String promptLoginEmployeID() {
        System.out.print("Veuillez entrer votre numéro d'identification:\t");
        return scanner.nextLine();
    }

    public String promptMainMenu() {
        System.out.println("[1]\tCommencer un tâches");
        System.out.println("[2]\tGénérer des rapports");
        System.out.println("[3]\tObtenir le nombre d'heures travaillées");
        System.out.println("[4]\tGénérer votre talon de paie avec le système Payroll");
        System.out.println("[5]\tVous déconnecter");
        return scanner.nextLine();
    }

    public String promptRapportMenu() {
        System.out.println("[1]\tGénérer un rapport d'état pour un projet");
        System.out.println("[2]\tGénérer un rapport d'état global");
        System.out.println("[3]\tGénérer votre rapport d'état employé");
        System.out.println("[4]\tGénérer votre talon de paie");
        return scanner.nextLine();
    }

    public String promptStartTimer(String task) {
        System.out.println("Vouler vous commencer l'activité suivante?\n\t" + task + "\n(y/n)");
        return scanner.nextLine();
    }

    public String promptEndTimer() {
        System.out.println("Entrer le caractère \"y\" lorsque vous terminer votre travail");
        return scanner.nextLine();
    }

}
