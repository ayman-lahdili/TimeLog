package tlsys;

import tlsys.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

public class RapportTest {

    @Test
    public void testGetRapportEtatProjet() {
        // Create a mock TimeLogModel
        TimeLogModel mockModel = mock(TimeLogModel.class);

        // Create a Rapport instance with the mock model
        Rapport rapport = new Rapport(mockModel);

        // Create a mock Project and Discipline
        Project mockProject = mock(Project.class);
        Discipline mockDiscipline = mock(Discipline.class);
       
        // Stub methods on the mock Project and Discipline
        when(mockModel.getProjectByID(anyInt())).thenReturn(mockProject);
        when(mockProject.getDisciplinesList()).thenReturn(Collections.singletonList(mockDiscipline));
        when(mockDiscipline.getName()).thenReturn("Test Discipline");
        when(mockDiscipline.getHeuresBudgetees()).thenReturn(10.0);
        when(mockDiscipline.getHeuresTotalesConsacre()).thenReturn(5.0);

        // Call the method under test
        String result = rapport.getRapportEtatProjet(1);

        // Verify that the result is not null
        assertNotNull(result);
    }
}