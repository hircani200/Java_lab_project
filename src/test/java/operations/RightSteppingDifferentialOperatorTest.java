package operations;

import mathapp.functions.SqrFunction;
import mathapp.functions.MathFunction;
import mathapp.operations.LeftSteppingDifferentialOperator;
import mathapp.operations.RightSteppingDifferentialOperator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RightSteppingDifferentialOperatorTest {

    @Test
    public void testRightDerivativeForSqrFunction() {
        double step = 1e-5;
        RightSteppingDifferentialOperator function = new RightSteppingDifferentialOperator(step);
        MathFunction function1 = function.derive(new SqrFunction());
        assertEquals(4.0, function1.apply(2.0), 1e-3);
        assertEquals(-4.0, function1.apply(-2.0), 1e-3);
        assertEquals(0.0, function1.apply(0.0), 1e-3);
        assertEquals(10.0, function1.apply(5.0), 1e-3);
        assertEquals(913.528, function1.apply(456.764), 1e-3);
    }

    @Test
    public void testInvalidStepThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(0));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(-1));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new RightSteppingDifferentialOperator(Double.NaN));
    }

    @Test
    public void testApplyThrowsException() {
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(0.1);
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(1.0));
    }
}