package mathapp.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskResultDTOTest {

    TaskResultDTO taskResultDTO;

    @BeforeEach
    void setUp() {
        taskResultDTO = new TaskResultDTO();
    }

    @Test
    public void setAndGetResultIdTest() {
        taskResultDTO.setResultId(1L);
        assertEquals(1L, taskResultDTO.getResultId());
    }

    @Test
    public void setAndGetTaskIdTest() {
        taskResultDTO.setTaskId(2L);
        assertEquals(2L, taskResultDTO.getTaskId());
    }

    @Test
    public void setAndGetResultTest() {
        taskResultDTO.setResult(95.5);
        assertEquals(95.5, taskResultDTO.getResult());
    }

    @Test
    public void setAndGetResultWithNegativeValueTest() {
        taskResultDTO.setResult(-10.0);
        assertEquals(-10.0, taskResultDTO.getResult());
    }

    @Test
    public void setAndGetResultWithNullTest() {
        taskResultDTO.setResult(null);
        assertEquals(null, taskResultDTO.getResult());
    }
}
