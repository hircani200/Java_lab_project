package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConstantFunctionTest {
    @Test
    public void testConstantFunction() {
        ConstantFunction constantFunction = new ConstantFunction(100);
        assertEquals(constantFunction.getConstant(), constantFunction.apply(10), 0.001);
        assertEquals(constantFunction.getConstant(), constantFunction.apply(-3), 0.001);
        assertEquals(constantFunction.getConstant(), constantFunction.apply(0), 0.001);
    }

}

