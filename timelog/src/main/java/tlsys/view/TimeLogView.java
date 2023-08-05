package tlsys.view;

import java.util.Scanner;

import java.util.List;

// Le package view gère l'interface de commande et la logique de présentation
public class TimeLogView {

    public final String dateFormat = "AAAA-MM-JJ";
    public static Scanner scanner = new Scanner(System.in);

    public String promptForMethodOfLogin() {
        System.out.print("Se connecter en tant qu'employe [1] ou se connecter en tant qu'administrateur [2]");
        return scanner.nextLine();
    }

    public String promptLogout() {
        System.out.println("Veux-tu te déconnecté? (y/n)");
        return scanner.nextLine();
    }

    public String promptProjectSelection(List<?> list) {
        System.out.println("Selectionne un project");
        return promptObjectSelection(list);
    }
    
    public String promptContinueDecision() {
        System.out.println("Veux-tu ajouter? (y/n) ");
        return scanner.nextLine(); 
    }

    public String promptDisciplineSelection(List<?> list) {
        System.out.println("Selectionne une discipline");
        return promptObjectSelection(list);
    }

    public String promptObjectSelection(List<?> objectList) {
        int itemId = 1;
        for (Object obj : objectList) {
            System.out.println("[" + itemId++ + "]\t" + obj);
        }
        return scanner.nextLine();
    }

    public void displayLoginSuccessMessage() {
        System.out.println("Login successful!");
    }

    public void displayLogoutMessage() {
        System.out.println("Logout successful!");
    }

    public void displayModifySuccessMessage(boolean action) {
        if (action) {
            System.out.println("Paramètre modifié avec succès");
            return;    
        }
        System.out.println("Incapable de modifié ce paramètre");
    }

    public String promptModificationDecision(String parameterName, Object currentValue) {
        System.out.println(parameterName+" :\t"+currentValue);
        System.out.println("Souhaitez-vous modifier se paramètre");
        return scanner.nextLine();
    }

    public String promptNewParameterDecision(String parameterName) {
        System.out.println(parameterName+":\t");
        return scanner.nextLine();
    }

    public String promptConfirmationObjectAddition(String objectName) {
        System.out.println("Êtes-vous sur d'ajouter le suivant:\t"+ objectName);
        return scanner.nextLine();
    }

    public String promptConfirmationObjectRemoval(String objectName) {
        System.out.println("Êtes-vous sur de retirer le suivant:\t"+ objectName);
        return scanner.nextLine();
    }    

    public void displayLoginErrorMessage() {
        System.out.println("Invalid username or password. Please try again.");
    }

    public String promptStartDateType() {
        System.out.println(
                "Voulez-vous la générer à partir de [1] la dernière période de paie ou [2] à partir du période que vous choisissez");
        return scanner.nextLine();
    }

    public String promptStartDateSelection() {
        System.out.println("Entrer une date de début (" + dateFormat + ")");
        return scanner.nextLine();
    }

    public String promptEndDateSelection() {
        System.out.println("Entrer une date de fin (" + dateFormat + ")");
        return scanner.nextLine();
    }

    public String promptEnterToContinue() {
        System.out.println("Presser la touche \"ENTRER\" pour retourner au menu");
        return scanner.nextLine();
    }

    public void displayRapport(String rapport) {
        System.out.println(rapport);
    }

}
