package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mathapp.functions.MathFunction;
import mathapp.functions.SimpleIterationMethod;
import mathapp.functions.SqrFunction;
import org.junit.jupiter.api.Test;

public class AndThenTest {

    @Test
    void testSqrFunctionAndThen() {
        MathFunction sqrFunction = new SqrFunction();
        MathFunction doubleFunction = x -> 2 * x;

        MathFunction compositeFunction = sqrFunction.andThen(doubleFunction);

        assertEquals(2 * Math.pow(2, 2), compositeFunction.apply(2), 1e-9);
        assertEquals(2 * Math.pow(3, 2), compositeFunction.apply(3), 1e-9);
        assertEquals(2 * Math.pow(0, 2), compositeFunction.apply(0), 1e-9);
    }

    @Test
    void testCompositeWithSimpleIterationMethod() {
        MathFunction g = x -> (x + 4) / 2;
        MathFunction simpleIterationMethod = new SimpleIterationMethod(new MathFunction[]{g}, 1e-9, 1000);
        MathFunction sqrFunction = new SqrFunction();
        MathFunction compositeFunction = sqrFunction.andThen(simpleIterationMethod);

        double result = compositeFunction.apply(2);
        assertEquals(4, result, 1e-9);
    }

    @Test
    void testCompositeMultipleFunctions() {
        MathFunction doubleFunction = x -> 2 * x;
        MathFunction sqrFunction = new SqrFunction();
        MathFunction inverseFunction = x -> 1 / x;

        MathFunction compositeFunction = doubleFunction.andThen(sqrFunction).andThen(inverseFunction);

        assertEquals(1 / Math.pow(2 * 2, 2), compositeFunction.apply(2), 1e-9);
        assertEquals(1 / Math.pow(2 * 3, 2), compositeFunction.apply(3), 1e-9);
    }
}

