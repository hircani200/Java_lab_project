import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.MathFunction;
import functions.SqrFunction;
import functions.SimpleIterationMethod;

public class AndThenTest {

    @Test
    void testSqrFunctionAndThen() {
        MathFunction sqrFunction = new SqrFunction();
        MathFunction doubleFunction = x -> 2 * x;

        MathFunction compositeFunction = sqrFunction.andThen(doubleFunction);

        assertEquals(2 * Math.pow(2, 2), compositeFunction.apply(2), 1e-5); // 2 * 4 = 8
        assertEquals(2 * Math.pow(3, 2), compositeFunction.apply(3), 1e-5); // 2 * 9 = 18
        assertEquals(2 * Math.pow(0, 2), compositeFunction.apply(0), 1e-5); // 2 * 0 = 0
    }

    @Test
    void testCompositeWithSimpleIterationMethod() {
        MathFunction g = x -> x / 2;

        MathFunction simpleIterationMethod = new SimpleIterationMethod(g, 1e-5, 1000);

        MathFunction sqrFunction = new SqrFunction();
        MathFunction compositeFunction = sqrFunction.andThen(simpleIterationMethod);

        double result = compositeFunction.apply(4);
        assertEquals(8, result, 1e-5);
    }

    @Test
    void testCompositeMultipleFunctions() {
        MathFunction doubleFunction = x -> 2 * x;
        MathFunction sqrFunction = new SqrFunction();
        MathFunction inverseFunction = x -> 1 / x;

        MathFunction compositeFunction = doubleFunction.andThen(sqrFunction).andThen(inverseFunction);

        assertEquals(1 / Math.pow(2 * 2, 2), compositeFunction.apply(2), 1e-5);
        assertEquals(1 / Math.pow(2 * 3, 2), compositeFunction.apply(3), 1e-5);
    }
}

