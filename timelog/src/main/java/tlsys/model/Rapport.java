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
                rapport += getRapportEtatProjet(project.getID()) + "\n" + "\n";
            }
        }

        return rapport;
    }

    public String getRapportEtatProjet(int ID) {
        Project project = model.getProjectByID(ID);
        List<Discipline> disciplines = project.getDisciplinesList();
    
        int nbreHeuresTotales = 0;
        int nbreHeuresBudgeteesTotales = 0;
        String rapport = "";
    
        for (Discipline discipline : disciplines) {
            double heuresBudgetees = discipline.getHeuresBudgetees();
            nbreHeuresBudgeteesTotales += heuresBudgetees;
    
            double nbreHeuresConsacrees = discipline.getHeuresTotalesConsacre();
            double pctAvancement = (nbreHeuresConsacrees * 100) / heuresBudgetees;
    
            nbreHeuresTotales += nbreHeuresConsacrees;
    
            rapport += "" +discipline.getName() +": \t heures consacrées = "  +nbreHeuresConsacrees +"\t (%) d'avancement = " +pctAvancement +"%\n";
        }

        int pctProjet = (nbreHeuresTotales * 100) / nbreHeuresBudgeteesTotales;
    
        rapport += "TOTAL: Projet" +ID +"\t heures consacrées=" +nbreHeuresTotales +"\t (%) d'avancement = " +pctProjet +"%\n";
    
        return "Rapport d'état Project [ID=" + ID + "] :\n" + rapport + "\n";
    }

    public String getRapportEtatEmploye(int ID, String debut, String fin) {
        List<Project> projects = model.getProjectList();
        //List<Employe> employes = model.getEmployeList();
        Employe employe = model.getEmployeByID(ID);
        Map<Integer, double[]> heures = new HashMap<Integer, double[]>();
        String rapport = "";

        heures = getHeures(ID, debut, fin);

        for(Project project : projects){
            int projectID = project.getID();

            if(heures.get(projectID) != null){
            double [] projectHours = heures.get(projectID);

            double salaireBase = employe.getTauxHoraireBase();
            double salaireTempsSupplementaire = employe.getTauxHoraireTempsSupplementaire();
            double salaireBrut = salaireBase * projectHours[0] + salaireTempsSupplementaire * projectHours[3];
            double salaireNet = salaireBrut * 0.6;
           
            rapport += "Projet" +projectID +"{Heures faites = " +projectHours[0] +", Minutes faites = " +projectHours[1] +", Secondes faites = " +projectHours[2] +", Heures supplémentaire faites = " +projectHours[3] +", Minutes supplémentaire faites = " +projectHours[4] +", Secondes supplémentaire faites = " +projectHours[5] +", Salaire brut généré = " +salaireBrut +", Salaire net généré = " +salaireNet +"}" +"\n" +"\n";
            }
        }

        double [] hoursWorked = heures.get(-1);
        double [] salaire = getTalonPaieEmploye(ID, debut, fin);

        return rapport += "Heures en total faites = " +hoursWorked[0] +", Minutes en total faites = " +hoursWorked[1] +", Secondes en total faites = " +hoursWorked[2] +", Heures supplémentaires en total faites = " +hoursWorked[3] +", Minutes supplémentaires en total faites = " +hoursWorked[4] +", Secondes supplémentaires en total faites = " +hoursWorked[5] +"\n" +"\n" +"Salaire brute pour la période sélectionné = " +salaire[0] +", Salaire net pour la période sélectionné = " +salaire[1];
     }

    public String getTalonPaieGlobal(String debut, String fin){
        double [] salaire = null;
        double salaireBrut = 0;
        double salaireNet = 0;
        String rapport = "";
        List<Employe> employeList = model.getEmployeList();

        if (employeList.size() == 0) {
            return null;
        } else {
            for (Employe employe: employeList) {
                salaire = getTalonPaieEmploye(employe.getID(), debut, fin);
                salaireBrut += salaire[0];
                salaireNet += salaire[1];

                rapport += getTalonPaietoString(employe.getID(), debut, fin) + "\n";
            }

            rapport += "Salaire brut total = " +salaireBrut +" Salaire net total = " +salaireNet;
        }

        return rapport;
    }

    private double [] getTalonPaieEmploye(int ID, String debut, String fin) {
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

        return "Salaire brut = " +salaire[0] +" Salaire Net = " +salaire[1];
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

                    if (totalMinutes >= 60) {
                        totalHours += totalMinutes / 60;
                        totalMinutes %= 60;
                    }

                    if (totalSeconds >= 60) {
                        totalMinutes += totalSeconds / 60;
                        totalSeconds %= 60;
                    }
    
                    if (totalHours > 40) {
                        overtimeHours = totalHours - 40;
                        overtimeMinutes += minutesWorked;
                        overtimeSeconds += secondsWorked;
    
                        if (overtimeMinutes >= 60) {
                            overtimeHours += overtimeMinutes / 60;
                            overtimeMinutes %= 60;
                        }

                        if (overtimeSeconds >= 60) {
                            overtimeMinutes += overtimeSeconds / 60;
                            overtimeSeconds %= 60;
                        }
                    }
    
                    Project project = log.getProject();
                    int projectID = project.getID();
                    double[] projectHours = projectHoursMap.getOrDefault(projectID, new double[6]);
    
                    projectHours[0] += hoursWorked;
                    projectHours[1] += minutesWorked;
                    projectHours[2] += secondsWorked;

                    if (projectHours[1] >= 60) {
                        projectHours[0] += (long) (projectHours[1] / 60);
                        projectHours[1] %= 60;
                    }

                    if (projectHours[2] >= 60) {
                        projectHours[1] += (long) (projectHours[2] / 60);
                        projectHours[2] %= 60;
                    }
    
                    if (projectHours[0] > 40) {
                        projectHours[3] += projectHours[0] - 40;
                        projectHours[4] += minutesWorked;
                        projectHours[5] += secondsWorked;
    
                        if (projectHours[4] >= 60) {
                            projectHours[3] += (long) (projectHours[4] / 60);
                            projectHours[4] %= 60;
                        }

                        if (projectHours[5] >= 60) {
                            projectHours[4] += (long) (projectHours[5] / 60);
                            projectHours[5] %= 60;
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
