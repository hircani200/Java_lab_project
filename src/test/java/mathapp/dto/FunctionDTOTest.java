package mathapp.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionDTOTest {

    FunctionDTO functionDTO;

    @BeforeEach
    void setUp() {
        functionDTO = new FunctionDTO();
    }

    @Test
    void setAndGetFunctionIdTest() {
        Long functionId = 1L;
        functionDTO.setFunctionId(functionId);
        assertEquals(functionId, functionDTO.getFunctionId());
    }

    @Test
    void setAndGetNameTest() {
        String name = "CosFunction";
        functionDTO.setType(name);
        assertEquals(name, FunctionDTO.getType());
    }

    @Test
    void setAndGetXFromTest() {
        Double xFrom = 0.0;
        functionDTO.setXFrom(xFrom);
        assertEquals(xFrom, functionDTO.getXFrom());
    }

    @Test
    void setAndGetXToTest() {
        Double xTo = 10.0;
        functionDTO.setXTo(xTo);
        assertEquals(xTo, functionDTO.getXTo());
    }

    @Test
    void setAndGetCountTest() {
        Integer count = 5;
        functionDTO.setCount(count);
        assertEquals(count, functionDTO.getCount());
    }
}