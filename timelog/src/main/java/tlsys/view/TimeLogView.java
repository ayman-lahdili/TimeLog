package tlsys.view;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

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

    public String promptLoginAdministratorUsername() {
        System.out.print("Entrer votre nom d'utilisateur");
        return scanner.nextLine();
    }

    public String promptLoginAdministratorPassword() {
        System.out.print("Entrer votre mot de pass");
        return scanner.nextLine();
    }

    public String promptEmployeMainMenu() {
        System.out.println("[1] Commencer un tâches [2] Générer des rapport");
        return scanner.nextLine();
    }

    public String promptProjectSelection(List<?> list) {
        System.out.println("Selectionne un project");
        return promptObjectSelection(list);
    }

    public String promptDisciplineSelection(List<?> list) {
        System.out.println("Selectionne une discipline");
        return promptObjectSelection(list);
    }

    public String promptObjectSelection(List<?> objectList) {
        int itemId=1;
        for (Object obj: objectList) {
            System.out.println("["+ itemId++ +"]\t"+obj);
        }
        return scanner.nextLine();
    }

    public String promptStartTimer(String task) {
        System.out.println("Vouler vous commencer l'activité"+task+"(y/n)");
        return scanner.nextLine();
    }

    public String promptEndTimer() {
        System.out.println("Vouler vous quitter cette tâche (y/n)");
        return scanner.nextLine();
    }

    public void displayLoginSuccessMessage() {
        System.out.println("Login successful!");
    }

    public void displayLoginErrorMessage() {
        System.out.println("Invalid username or password. Please try again.");
    }

}
