package tlsys.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        int nbreHeuresDesign1 = 0;
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
        List<Project> projects = model.getProjectList();
        List<Employe> employes = model.getEmployeList();
        Map<Integer, double[]> heures = new HashMap<Integer, double[]>();
        String rapport = "";

        heures = getHeures(ID, debut, fin);

        /*for(Project project : projects){
            int projectID = project.getID();
            double [] projectHours = heures.get(projectID);
           
            rapport += "Projet" +projectID +"{Temps fait = " +projectHours[0] +":" +projectHours[1] +":" +projectHours[2] +", Temps supplémentaire fait = " +projectHours[3] +":" +projectHours[4] +":" +projectHours[5] +"}" +"\n";
        }*/

        double [] hoursWorked = heures.get(-1);
        double [] salaire = getTalonPaieEmploye(ID, debut, fin);

        return rapport += "Heures en total faites = " +hoursWorked[0] +", Minutes en total faites = " +hoursWorked[1] +", Secondes en total faites = " +hoursWorked[2] +", Heures supplémentaires en total faites = " +hoursWorked[3] +", Minutes supplémentaires en total faites = " +hoursWorked[4] +", Secondes supplémentaires en total faites = " +hoursWorked[5] +"\n" +"Salaire brute pour la période sélectionné = " +salaire[0] +", Salaire net pour la période sélectionné = " +salaire[1];
     }

    public double [] getTalonPaieEmploye(int ID, String debut, String fin) {
        Employe employe = model.getEmployeByID(ID);
        Map<Integer, double[]> heures = new HashMap<Integer, double[]>();

        heures = getHeures(ID, debut, fin);

        double [] hoursWorked = heures.get(-1);

        double salaireBase = employe.getTauxHoraireBase();
        double salaireTempsSupplementaire = employe.getTauxHoraireTempsSupplementaire();
        double salaireBrut = salaireBase * hoursWorked[0] + salaireTempsSupplementaire * hoursWorked[3];
        double salaireNet = salaireBrut * 0.6;

        double [] salaire = {salaireBrut, salaireNet};

        return salaire;
    }

    public String getTalonPaietoString(int ID, String debut, String fin){
        double [] salaire = getTalonPaieEmploye(ID, debut, fin);

        return "Salaire brute = " +salaire[0] +" Salaire Net = " +salaire[1];
    }

    public String getTalonPaieGlobal(String debut, String fin){
        String rapport = "";
        List<Employe> employeList = model.getEmployeList();

        if (employeList.size() == 0) {
            return null;
        } else {
            for (Employe employe: employeList) {
                rapport += getTalonPaietoString(employe.getID(), debut, fin) + "\n";
            }
        }

        return rapport;
    }

    public Map<Integer, double[]> getHeures(int ID, String debut, String fin) {
        LocalDate start = null;
        LocalDate end = null;
    
        if (debut.equals("default") && fin.equals("default")) {
            LocalDate currentDate = LocalDate.now();
            LocalDate startOfLastWeek = currentDate.minusWeeks(1);

            // Find the start of the last odd week
            int daysUntilThursday = (DayOfWeek.THURSDAY.getValue() + 7 - startOfLastWeek.getDayOfWeek().getValue()) % 7;             
            start = startOfLastWeek.plusDays(daysUntilThursday);             
            end = start.plusWeeks(2);

            System.out.println("Start Date: " + start);
            System.out.println("End Date: " + end);
        } else {
            start = LocalDate.parse(debut);
            end = LocalDate.parse(fin);
        }
    
        Employe employe = model.getEmployeByID(ID);
        List<EmployeLog> employeLogs = model.getEmployeLogsList();
        Map<Integer, double[]> projectHoursMap = new HashMap<Integer, double[]>();
    
        long totalHours = 0;
        long totalMinutes = 0;
        long totalSeconds = 0;
    
        long overtimeHours = 0;
        long overtimeMinutes = 0;
        long overtimeSeconds = 0;
    
        for (EmployeLog log : employeLogs) {
            if (log.getEmployeeID() == employe) {
                Instant startTime = log.getStartDateTime();
                Instant endTime = log.getEndDateTime();
    
                LocalDateTime startDateTime = startTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endDateTime = endTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
    
                if (!startDateTime.toLocalDate().isBefore(start) && !endDateTime.toLocalDate().isAfter(end)) {
                    Duration duration = Duration.between(startDateTime, endDateTime);
    
                    long hoursWorked = duration.toHours();
                    long minutesWorked = duration.toMinutesPart();
                    long secondsWorked = duration.toSecondsPart();
    
                    totalHours += hoursWorked;
                    totalMinutes += minutesWorked;
                    totalSeconds += secondsWorked;
    
                    if (totalHours > 40) {
                        overtimeHours = totalHours - 40;
                        overtimeMinutes += minutesWorked;
                        overtimeSeconds += secondsWorked;
    
                        if (overtimeMinutes >= 60) {
                            overtimeHours += overtimeMinutes / 60;
                            overtimeMinutes %= 60;
                        }
                    }
    
                    Project project = log.getProjectName();
                    int projectID = project.getID();
                    double[] projectHours = projectHoursMap.getOrDefault(projectID, new double[6]);
    
                    projectHours[0] += hoursWorked;
                    projectHours[1] += minutesWorked;
                    projectHours[2] += secondsWorked;
    
                    if (projectHours[0] > 40) {
                        projectHours[3] += projectHours[0] - 40;
                        projectHours[4] += minutesWorked;
                        projectHours[5] += secondsWorked;
    
                        if (projectHours[4] >= 60) {
                            projectHours[3] += (long) (projectHours[3] / 60);
                            projectHours[4] %= 60;
                        }
                    }
    
                    projectHoursMap.put(projectID, projectHours);
                }
            }
        }
    
        double[] total = {totalHours, totalMinutes, totalSeconds, overtimeHours, overtimeMinutes, overtimeSeconds};
        projectHoursMap.put(-1, total); // Use -1 as a key to represent overall totals
    
        return projectHoursMap;
    }

}