package tlsys.view;

import java.util.Scanner;

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

}
