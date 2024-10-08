package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SqrFunctionTest {

    @Test
    public void testApply() {
        SqrFunction sqrFunction = new SqrFunction();

        assertEquals(9, sqrFunction.apply(3), 1e-9);
        assertEquals(9, sqrFunction.apply(-3), 1e-9);
        assertEquals(0, sqrFunction.apply(0), 1e-9);
        assertEquals(2.25, sqrFunction.apply(1.5), 1e-9);
    }
}