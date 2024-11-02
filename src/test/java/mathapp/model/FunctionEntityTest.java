package mathapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionEntityTest {

    FunctionEntity functionEntity;

    @BeforeEach
    void setUp() {
        functionEntity = new FunctionEntity();
    }

    @Test
    public void setAndGetFunctionIdTest() {
        functionEntity.setFunctionId(1L);
        assertEquals(1L, functionEntity.getFunctionId());
    }

    @Test
    public void setAndGetNameTest() {
        functionEntity.setName("CosFunction");
        assertEquals("CosFunction", functionEntity.getName());
    }

    @Test
    public void setAndGetXFromTest() {
        functionEntity.setXFrom(0.0);
        assertEquals(0.0, functionEntity.getXFrom());
    }

    @Test
    public void setAndGetXToTest() {
        functionEntity.setXTo(10.0);
        assertEquals(10.0, functionEntity.getXTo());
    }

    @Test
    public void setAndGetCountTest() {
        functionEntity.setCount(5);
        assertEquals(5, functionEntity.getCount());
    }

    @Test
    public void setAndGetPointsTest() {
        List<FunctionPointEntity> points = new ArrayList<>();
        FunctionPointEntity point = new FunctionPointEntity(); // Предположим, что у вас есть этот класс
        points.add(point);
        functionEntity.setPoints(points);
        assertEquals(points, functionEntity.getPoints());
    }
}
