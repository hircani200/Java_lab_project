package mathapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskResultEntityTest {

    private TaskResultEntity taskResultEntity;

    @BeforeEach
    void setUp() {
        taskResultEntity = new TaskResultEntity();
    }

    @Test
    void setAndGetResultIdTest() {
        taskResultEntity.setResultId(1L);
        assertEquals(1L, taskResultEntity.getResultId());
    }

    @Test
    void setAndGetTaskTest() {
        TaskEntity taskEntity = new TaskEntity(); // Создаем экземпляр TaskEntity
        taskResultEntity.setTask(taskEntity);
        assertEquals(taskEntity, taskResultEntity.getTask());
    }

    @Test
    void setAndGetResultTest() {
        taskResultEntity.setResult(95.5);
        assertEquals(95.5, taskResultEntity.getResult());
    }

    @Test
    void setAndGetNullResultTest() {
        taskResultEntity.setResult(null);
        assertEquals(null, taskResultEntity.getResult());
    }

    @Test
    void setAndGetNegativeResultTest() {
        taskResultEntity.setResult(-10.0);
        assertEquals(-10.0, taskResultEntity.getResult());
    }
}
