package mathapp.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionPointDTOTest {

    FunctionPointDTO functionPointDTO;

    @BeforeEach
    public void setUp() {
        functionPointDTO = new FunctionPointDTO();
    }

    @Test
    public void setAndGetPointIdTest() {
        Long pointId = 1L;
        functionPointDTO.setPointId(pointId);
        assertEquals(pointId, functionPointDTO.getPointId());
    }

    @Test
    public void setAndGetFunctionIdTest() {
        Long functionId = 2L;
        functionPointDTO.setFunctionId(functionId);
        assertEquals(functionId, functionPointDTO.getFunctionId());
    }

    @Test
    public void setAndGetXValueTest() {
        Double xValue = 3.14;
        functionPointDTO.setXValue(xValue);
        assertEquals(xValue, functionPointDTO.getXValue());
    }

    @Test
    public void setAndGetYValueTest() {
        Double yValue = 2.71;
        functionPointDTO.setYValue(yValue);
        assertEquals(yValue, functionPointDTO.getYValue());
    }
}
