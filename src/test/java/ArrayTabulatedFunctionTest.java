import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import functions.ArrayTabulatedFunction;
import functions.MathFunction;

class ArrayTabulatedFunctionTest {

    @Test
    void testConstructorWithArrays() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(4, function.getCount());

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i), 1e-9);
            assertEquals(yValues[i], function.getY(i), 1e-9);
        }

        assertEquals(1.0, function.leftBound(), 1e-9);
        assertEquals(4.0, function.rightBound(), 1e-9);
    }

    @Test
    void testConstructorWithArraysThrowsExceptionForDifferentLengths() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 4.0};

        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xValues, yValues);
        });
    }

    @Test
    void testConstructorWithArraysThrowsExceptionForUnsortedArray() {
        double[] xValues = {1.0, 3.0, 2.0};
        double[] yValues = {2.0, 6.0, 4.0};

        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xValues, yValues);
        });
    }
    @Test
    void testConstructorWithFunctionAndInterval() {
        MathFunction linearFunction = x -> 2 * x;
        double xFrom = 0.0;
        double xTo = 4.0;
        int count = 5;

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(linearFunction, xFrom, xTo, count);

        double[] expectedX = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] expectedY = {0.0, 2.0, 4.0, 6.0, 8.0};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(expectedX[i], function.getX(i), 1e-9);
            assertEquals(expectedY[i], function.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithEqualXFromAndXTo() {
        MathFunction constantFunction = x -> 5.0;
        double xFrom = 2.0;
        double xTo = 2.0;
        int count = 5;

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(constantFunction, xFrom, xTo, count);

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(2.0, function.getX(i), 1e-9);
            assertEquals(5.0, function.getY(i), 1e-9);
        }
    }
    @Test
    void testInterpolateWithinBounds() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(5.0, function.apply(2.5), 1e-9);
    }

    @Test
    void testExtrapolateLeft() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(0.0, function.apply(0.0), 1e-9);
    }

    @Test
    void testExtrapolateRight() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(10.0, function.apply(5.0), 1e-9);
    }
    @Test
    void testGetSetY() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(4.0, function.getY(1), 1e-9);

        function.setY(1, 10.0);
        assertEquals(10.0, function.getY(1), 1e-9);
    }
}