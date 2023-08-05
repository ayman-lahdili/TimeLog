package tlsys.view;

import java.util.Scanner;

import java.util.List;

// Le package view gère l'interface de commande et la logique de présentation
public class TimeLogView {

    public final String dateFormat = "AAAA-MM-JJ";
    public static Scanner scanner = new Scanner(System.in);

    public String promptForMethodOfLogin() {
        System.out.println("[1]\tSe connecter en tant qu'employé");
        System.out.println("[2]\tSe connecter en tant qu'administrateur");
        return scanner.nextLine();
    }

    public String promptLoginUsername() {
        System.out.print("Veuillez entrer votre nom d'utilisateur:\t");
        return scanner.nextLine();
    }

    public String promptLogout() {
        System.out.println("Souhaitez-vous vous déconnecter? (y/n)");
        return scanner.nextLine();
    }

    public String promptProjectSelection(List<?> list) {
        System.out.println("Sélectionnez un project");
        return promptObjectSelection(list);
    }
    
    public String promptContinueDecision() {
        System.out.println("Souhaitez-vous continuer d'en ajouter? (y/n)");
        return scanner.nextLine(); 
    }

    public String promptDisciplineSelection(List<?> list) {
        System.out.println("Veuillez sélectionner une discipline");
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
        System.out.println("Vous êtes maintenant connecté");
    }

    public void displayLogoutMessage() {
        System.out.println("Vous êtes maintenant déconnecté");
    }

    public void displayModifySuccessMessage(boolean action) {
        if (action) {
            System.out.println("Se paramètre a été modifié avec succès");
            return;
        }
        System.out.println("Incapable de modifier ce paramètre");
    }

    public String promptModificationDecision(String parameterName, Object currentValue) {
        System.out.println(parameterName+" :\t"+currentValue);
        System.out.println("Souhaitez-vous modifier se paramètre?");
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
        System.out.println("Nom d'utilisateur ou mot de passe invalide. Veuillez réessayer");
    }

    public String promptStartDateType() {
        System.out.println("[1]\tGénérer à partir de la dernière période de paie");
        System.out.println("[2]\tGénérer à partir de la période de votre choix");
        return scanner.nextLine();
    }

    public String promptStartDateSelection() {
        System.out.print("Entrer une date de début (" + dateFormat + "):\t");
        return scanner.nextLine();
    }

    public String promptEndDateSelection() {
        System.out.print("Entrer une date de fin (" + dateFormat + "):\t");
        return scanner.nextLine();
    }

    public String promptEnterToContinue() {
        System.out.println("Presser la touche \"ENTRER\" pour retourner au menu");
        return scanner.nextLine();
    }

    public String displayInvalidInputWarning() {
        System.out.println("Entré invalide. Presser la touche \"ENTRER\" pour réessayer");
        return scanner.nextLine();
    }

    public void displayRapport(String rapport) {
        if (rapport==null) {
            System.out.println("Aucun rapport disponible");
            return;
        }
        System.out.println(rapport);
    }

}
