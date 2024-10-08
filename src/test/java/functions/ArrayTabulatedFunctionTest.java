package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import exceptions.ArrayIsNotSortedException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

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

        assertThrows(DifferentLengthOfArraysException.class, () -> new ArrayTabulatedFunction(xValues, yValues));
    }

    @Test
    void testConstructorWithArraysThrowsExceptionForUnsortedArray() {
        double[] xValues = {1.0, 3.0, 2.0};
        double[] yValues = {2.0, 6.0, 4.0};

        assertThrows(ArrayIsNotSortedException.class, () -> new ArrayTabulatedFunction(xValues, yValues));
    }

    @Test
    void testConstructorWithArraysThrowsExceptionForDuplicates() {
        double[] xValues = {1.0, 3.0, 1.0};
        double[] yValues = {2.0, 6.0, 4.0};

        assertThrows(ArrayIsNotSortedException.class, () -> new ArrayTabulatedFunction(xValues, yValues));
    }
    @Test
    void testConstructorWithArraysThrowsExceptionForSizeLessThanTwo() {
        double[] xValues = {1.0};
        double[] yValues = {2.0};

        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(xValues, yValues));
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
        MathFunction constantFunction = _ -> 5.0;
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
    void testInterpolationException() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};

        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        assertThrows(InterpolationException.class,
                () -> arrayFunction.interpolate(3.5, 1));

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

    @Test
    void testInsertWithXFromArray() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(3.0, 5.5);
        yValues[2] = 5.5;

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i), 1e-9);
            assertEquals(yValues[i], function.getY(i), 1e-9);
        }
    }

    @Test
    void testInsertWithXBelowZeroIndexElement(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(0.0, 5.5);

        double[] newXValues = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] newYValues = {5.5, 2.0, 4.0, 6.0, 8.0};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(newXValues[i], function.getX(i), 1e-9);
            assertEquals(newYValues[i], function.getY(i), 1e-9);
        }
    }

    @Test
    void testInsertWithXHigherLastIndexElement(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(5.0, 5.5);

        double[] newXValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] newYValues = {2.0, 4.0, 6.0, 8.0, 5.5};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(newXValues[i], function.getX(i), 1e-9);
            assertEquals(newYValues[i], function.getY(i), 1e-9);
        }
    }

    @Test
    void testInsertWithXInTheSegment() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(3.5, 5.5);

        double[] newXValues = {1.0, 2.0, 3.0, 3.5, 4.0};
        double[] newYValues = {2.0, 4.0, 6.0, 5.5, 8.0};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(newXValues[i], function.getX(i), 1e-9);
            assertEquals(newYValues[i], function.getY(i), 1e-9);
        }
    }

    @Test
    void testRemove() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0, 25.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(2);

        assertEquals(4, function.getCount());

        assertEquals(1.0, function.getX(0));
        assertEquals(2.0, function.getX(1));
        assertEquals(4.0, function.getX(2));
        assertEquals(5.0, function.getX(3));

        assertEquals(1.0, function.getY(0));
        assertEquals(4.0, function.getY(1));
        assertEquals(16.0, function.getY(2));
        assertEquals(25.0, function.getY(3));

        function.remove(0);

        assertEquals(3, function.getCount());

        assertEquals(2.0, function.getX(0));
        assertEquals(4.0, function.getX(1));
        assertEquals(5.0, function.getX(2));

        assertEquals(4.0, function.getY(0));
        assertEquals(16.0, function.getY(1));
        assertEquals(25.0, function.getY(2));
    }

    @Test
    void testRemoveInvalidIndex() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IndexOutOfBoundsException.class, () -> function.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> function.remove(4));
    }

    @Test
    void testRemoveWithMinimumPoints() {
        double[] xValues = {1.0, 2.0};
        double[] yValues = {1.0, 4.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IllegalStateException.class, () -> function.remove(0));
    }

    @Test
    public void testArrayIteratorWithWhile() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{10.0, 20.0, 30.0});
        Iterator<Point> iterator = function.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(function.getX(index), point.x, 1e-9);
            assertEquals(function.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(function.getCount(), index);
    }

    @Test
    public void testForEach() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        int index = 0;
        for (Point point : function) {
            assertEquals(xValues[index], point.x, 1e-9);
            assertEquals(yValues[index], point.y, 1e-9);
            index++;
        }
        assertEquals(xValues.length, index);
    }
}

