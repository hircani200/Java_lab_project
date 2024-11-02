package mathapp.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOTest {

    TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO();
    }

    @Test
    public void setAndGetPointIdTest() {
        taskDTO.setTaskId(1L);
        assertEquals(1L, taskDTO.getTaskId());
    }

    @Test
    public void setAndGetFunctionIdTest() {
        taskDTO.setFunctionId(2L);
        assertEquals(2L, taskDTO.getFunctionId());
    }

    @Test
    public void setAndGetTaskTypeTest() {
        taskDTO.setTaskType("Example Type");
        assertEquals("Example Type", taskDTO.getTaskType());
    }

    @Test
    public void setAndGetStatusTest() {
        taskDTO.setStatus("Pending");
        assertEquals("Pending", taskDTO.getStatus());
    }

    @Test
    public void setAndGetStartTimeTest() {
        LocalDateTime startTime = LocalDateTime.now();
        taskDTO.setStartTime(startTime);
        assertEquals(startTime, taskDTO.getStartTime());
    }

    @Test
    public void setAndGetEndTimeTest() {
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        taskDTO.setEndTime(endTime);
        assertEquals(endTime, taskDTO.getEndTime());
    }
}