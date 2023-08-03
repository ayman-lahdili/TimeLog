package tlsys.model;

import java.time.Instant;

public class EmployeLog {

    private Employe employeeID;
    private Project projectName;
    private Discipline disciplineName;
    private Instant startDateTime;
    private Instant endDateTime;

    public EmployeLog(Employe employeeID, Project projectName, Discipline disciplineName, Instant startDateTime,
            Instant endDateTime) {
        this.employeeID = employeeID;
        this.projectName = projectName;
        this.disciplineName = disciplineName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "EmployeLog [employeeID=" + employeeID + ", projectName=" + projectName + ", disciplineName="
                + disciplineName + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
    }

    public Employe getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employe employeeID) {
        this.employeeID = employeeID;
    }

    public Project getProjectName() {
        return projectName;
    }

    public void setProjectName(Project projectName) {
        this.projectName = projectName;
    }

    public Discipline getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(Discipline disciplineName) {
        this.disciplineName = disciplineName;
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
