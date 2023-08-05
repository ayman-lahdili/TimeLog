package tlsys.controller;


import tlsys.model.TimeLogModel;
import tlsys.view.AdministratorView;
import tlsys.view.EmployeView;
import tlsys.view.TimeLogView;

/*
 * le package controller agit comme un intermédiaire entre model et view
 * 
 * Il est responsable du traitement des interactions de l'utilisateur (i.e. changer les types, formater les inputs, etc.)
 */

public class TimeLogController {

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
            case "1": // Employee Login
                EmployeController employeController = new EmployeController(model, new EmployeView());
                employeController.login();

                view.displayLogoutMessage();
                break;
            case "2": // Admin Login
                AdministratorController administratorController = new AdministratorController(model, new AdministratorView());
                administratorController.login();

                view.displayLogoutMessage();
                break;
            default:
                break;
        }

    }

}
