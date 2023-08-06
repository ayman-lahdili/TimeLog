package tlsys.model;

import java.time.Instant;

public class EmployeLog {

    private Employe employeeID;
    private Project project;
    private Discipline discipline;
    private Instant startDateTime;
    private Instant endDateTime;

    public EmployeLog(Employe employeeID, Project projectName, Discipline disciplineName, Instant startDateTime,
            Instant endDateTime) {
        this.employeeID = employeeID;
        this.project = projectName;
        this.discipline = disciplineName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "EmployeLog [employeeID=" + employeeID + ", projectName=" + project + ", disciplineName="
                + discipline + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
    }

    public Employe getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employe employeeID) {
        this.employeeID = employeeID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project projectName) {
        this.project = projectName;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline disciplineName) {
        this.discipline = disciplineName;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

}
