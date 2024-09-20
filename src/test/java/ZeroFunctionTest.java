import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.ZeroFunction;
import org.junit.jupiter.api.Test;

public class ZeroFunctionTest {
    @Test
    public void testZeroFunction() {
        ZeroFunction zeroFunction = new ZeroFunction();
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(10), 0.001);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(-3), 0.001);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(0), 0.001);
    }

}