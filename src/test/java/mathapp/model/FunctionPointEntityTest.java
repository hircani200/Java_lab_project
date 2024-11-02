package mathapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionPointEntityTest {

    FunctionPointEntity functionPointEntity;

    @BeforeEach
    void setUp() {
        functionPointEntity = new FunctionPointEntity();
    }

    @Test
    public void setAndGetPointIdTest() {
        functionPointEntity.setPointId(1L);
        assertEquals(1L, functionPointEntity.getPointId());
    }

    @Test
    public void setAndGetFunctionTest() {
        FunctionEntity functionEntity = new FunctionEntity();
        functionPointEntity.setFunction(functionEntity);
        assertEquals(functionEntity, functionPointEntity.getFunction());
    }

    @Test
    public void setAndGetXValueTest() {
        functionPointEntity.setXValue(2.5);
        assertEquals(2.5, functionPointEntity.getXValue());
    }

    @Test
    public void setAndGetYValueTest() {
        functionPointEntity.setYValue(4.0);
        assertEquals(4.0, functionPointEntity.getYValue());
    }

    @Test
    public void setAndGetXValueWithNegativeTest() {
        functionPointEntity.setXValue(-3.0);
        assertEquals(-3.0, functionPointEntity.getXValue());
    }

    @Test
    public void setAndGetYValueWithNullTest() {
        functionPointEntity.setYValue(null);
        assertEquals(null, functionPointEntity.getYValue());
    }
}
