package tlsys.controller;

import tlsys.model.Employe;
import tlsys.model.TimeLogModel;
import tlsys.view.TimeLogView;

public class TimeLogController {

    private Employe employe;
    private TimeLogModel model;
    private TimeLogView view;

    public TimeLogController(TimeLogModel model, TimeLogView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {

        login();

    }

    public void login() {
        String loginMethod = view.promptForMethodOfLogin();

        switch (loginMethod) {
            case "1":
                int ID = Integer.parseInt(view.promptLoginEmployeID());
                String username = view.promptLoginEmployeUsername();

                Employe user = model.authenticateEmploye(ID, username);

                if (user != null) {
                    employe = user;
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                break;
            case "2":
                System.out.println("de");
                break;

            default:
                break;
        }

    }

}
