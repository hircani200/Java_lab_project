package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ZeroFunctionTest {
    @Test
    public void testZeroFunction() {
        ZeroFunction zeroFunction = new ZeroFunction();

        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(10), 1e-9);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(-3), 1e-9);
        assertEquals(zeroFunction.getConstant(), zeroFunction.apply(0), 1e-9);
    }

}