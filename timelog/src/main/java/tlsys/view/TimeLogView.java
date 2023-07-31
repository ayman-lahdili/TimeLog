package tlsys.view;

import java.util.Scanner;

// Le package view gère l'interface de commande et la logique de présentation
public class TimeLogView {

    public static Scanner scanner = new Scanner(System.in);

    public String promptForMethodOfLogin() {
        System.out.print("Se connecter en tant qu'employe [1] ou se connecter en tant qu'administrateur [2]");
        return scanner.nextLine();
    }

    public String promptLoginEmployeUsername() {
        System.out.print("Entrer votre nom d'utilisateur");
        return scanner.nextLine();
    }

    public String promptLoginEmployeID() {
        System.out.print("Entrer votre ID");
        return scanner.nextLine();
    }

    public String promptEmployeMenu() {
        System.out.println("[1] Commencer un tâches [2] Générer des rapport");
        return scanner.nextLine();
    }

    public String promptProjectSelection() {
        System.out.println("Select a project");
        return scanner.nextLine();
    }

}
