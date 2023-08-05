package tlsys.model;

import java.util.List;

public class Rapport {
    
    TimeLogModel model;

    public Rapport(TimeLogModel model) {
        this.model = model;
    }

    public String getRapportEtatGlobale() {
        String rapport = "";
        List<Project> projectList = model.getProjectList();

        if (projectList.size() == 0) {
            return null;
        } else {
            for (Project project: projectList) {
                rapport += getRapportEtatProjet(project.getID()) + "\n";
            }
        }

        return rapport;
    }

    public String getRapportEtatProjet(int ID) {
        Project project = model.getProjectByID(ID);
        List<Discipline> disciplines = project.getDisciplinesList();

        int nbreHeuresDesign1 = 0; //TODO double
        int pctDesign1 = 0;

        int nbreHeuresDesign2 = 0;
        int pctDesign2 = 0;

        int nbreHeuresImplementation = 0;
        int pctImplementation = 0;

        int nbreHeuresTest = 0;
        int pctTest = 0;

        int nbreHeuresDeploiement = 0;
        int pctDeploiement = 0;

        int nbreHeuresTotales = 0;
        int nbreHeuresBudgeteesTotales = 0;
        int pctProjet = 0;

        for (int i = 0; i < disciplines.size(); i++) {
            int heuresBudgetees = disciplines.get(i).getHeuresBudgetees();
            nbreHeuresBudgeteesTotales += heuresBudgetees;

            if (disciplines.get(i).getName() == "Design1") {
                nbreHeuresDesign1 = disciplines.get(i).getHeuresTotalesConsacre();
                pctDesign1 = (nbreHeuresDesign1 / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Design2") {
                nbreHeuresDesign2 = disciplines.get(i).getHeuresTotalesConsacre();
                pctDesign2 = (nbreHeuresDesign2 / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Implémentation") {
                nbreHeuresImplementation = disciplines.get(i).getHeuresTotalesConsacre();
                pctImplementation = (nbreHeuresImplementation / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Test") {
                nbreHeuresTest = disciplines.get(i).getHeuresTotalesConsacre();
                pctTest = (nbreHeuresTest / heuresBudgetees) * 100;
            }

            if (disciplines.get(i).getName() == "Déploiement") {
                nbreHeuresDeploiement = disciplines.get(i).getHeuresTotalesConsacre();
                pctDeploiement = (nbreHeuresDesign1 / heuresBudgetees) * 100;
            }
        }

        nbreHeuresTotales = nbreHeuresDesign1 + nbreHeuresDesign2 + nbreHeuresImplementation + nbreHeuresTest
                + nbreHeuresDeploiement;

        pctProjet = (nbreHeuresTotales / nbreHeuresBudgeteesTotales) * 100;

        return "Project" + ID + "{" +
                "Nombre d’heures travaillées pour Design1 = " + nbreHeuresDesign1 + '\'' +
                ", Pourcentage d’avancement pour Design1 = " + pctDesign1 + '\'' +
                ", Nombre d’heures travaillées pour Design2 = " + nbreHeuresDesign2 + '\'' +
                ", Pourcentage d’avancement pour Design2 = " + pctDesign2 + '\'' +
                ", Nombre d’heures travaillées pour Implémentation = " + nbreHeuresImplementation + '\'' +
                ", Pourcentage d’avancement pour Implémentation = " + pctImplementation + '\'' +
                ", Nombre d’heures travaillées pour Test = " + nbreHeuresTest + '\'' +
                ", Pourcentage d’avancement pour Test = " + pctTest + '\'' +
                ", Nombre d’heures travaillées pour Déploiement = " + nbreHeuresDeploiement + '\'' +
                ", Pourcentage d’avancement pour Déploiement = " + pctDeploiement + '\'' +
                ", Nombre d’heures travaillées pour Projet1 = " + nbreHeuresTotales + '\'' +
                ", Pourcentage d’avancement pour Projet1 = " + pctProjet +
                "}";
    }

    
    public String getRapportEtatEmploye(int ID) {
        String rapport = "";

        return rapport;
    }

    public String getRapportEtatEmploye(int ID, String debut, String fin) {
        String rapport = "";
        List<Employe> employeList = model.getEmployeList();

        for(int i = 0; i<employeList.size(); i++){
            rapport += model.getTalonPaieEmploye(i, debut, fin) + "\n"; // TODO
        }

        return rapport;
    }



}
