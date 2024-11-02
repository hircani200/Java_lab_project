package mathapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskEntityTest {

    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        taskEntity = new TaskEntity();
    }

    @Test
    void setAndGetTaskIdTest() {
        taskEntity.setTaskId(1L);
        assertEquals(1L, taskEntity.getTaskId());
    }

    @Test
    void setAndGetFunctionTest() {
        FunctionEntity functionEntity = new FunctionEntity(); // Создаем экземпляр FunctionEntity
        taskEntity.setFunction(functionEntity);
        assertEquals(functionEntity, taskEntity.getFunction());
    }

    @Test
    void setAndGetTaskTypeTest() {
        taskEntity.setTaskType("Type");
        assertEquals("Type", taskEntity.getTaskType());
    }

    @Test
    void setAndGetStatusTest() {
        taskEntity.setStatus("In Progress");
        assertEquals("In Progress", taskEntity.getStatus());
    }

    @Test
    void setAndGetStartTimeTest() {
        LocalDateTime startTime = LocalDateTime.now();
        taskEntity.setStartTime(startTime);
        assertEquals(startTime, taskEntity.getStartTime());
    }

    @Test
    void setAndGetEndTimeTest() {
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        taskEntity.setEndTime(endTime);
        assertEquals(endTime, taskEntity.getEndTime());
    }

    @Test
    void setAndGetNullValues() {
        taskEntity.setStartTime(null);
        taskEntity.setEndTime(null);
        assertEquals(null, taskEntity.getStartTime());
        assertEquals(null, taskEntity.getEndTime());
    }
}
