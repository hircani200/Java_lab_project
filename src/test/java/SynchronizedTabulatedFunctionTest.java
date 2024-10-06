import concurrent.SynchronizedTabulatedFunction;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import functions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SynchronizedTabulatedFunctionTest {
    
    @Test
    void testConstructorWithArrays(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, synchronizedTabulatedFunction1.getCount());
        assertEquals(4, synchronizedTabulatedFunction2.getCount());

        for (int i = 0; i < synchronizedTabulatedFunction1.getCount(); i++) {
            assertEquals(xArray[i], synchronizedTabulatedFunction1.getX(i), 1e-9);
            assertEquals(yArray[i], synchronizedTabulatedFunction1.getY(i), 1e-9);
            assertEquals(xArray[i], synchronizedTabulatedFunction2.getX(i), 1e-9);
            assertEquals(yArray[i], synchronizedTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorArraysWithDuplicatesThrowsException(){

        double[] xArray = {1.0, 2.0, 1.0, 3.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorArraysWithUnsortedElementsThrowsException(){

        double[] xArray = {10.0, 2.0, 4.5, 1.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testGetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(2.0, synchronizedTabulatedFunction1.getX(-3));
        assertEquals(1.1, synchronizedTabulatedFunction1.getY(-1));
    }

    @Test
    void testSetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        synchronizedTabulatedFunction1.setY(-3, 1.0);

        assertEquals(1.0, synchronizedTabulatedFunction1.getY(-3));
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(DifferentLengthOfArraysException.class, () -> new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(DifferentLengthOfArraysException.class, () -> new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorArraysWithOneElementThrowsException(){

        double[] xArray = {1.0};
        double[] yArray = {2.0};

        assertThrows(IllegalArgumentException.class, () -> new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(IllegalArgumentException.class, () -> new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorWithFunctionAndInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 4.0;
        int count = 7;

        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(function, xFrom, xTo, count));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(function, xFrom, xTo, count));

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        double[] rightY = {4.0, 6.25, 9.0, 12.25, 16, 20.25, 25.0};

        for (int i = 0; i < synchronizedTabulatedFunction1.getCount(); i++) {
            assertEquals(rightX[i], synchronizedTabulatedFunction1.getX(i), 1e-9);
            assertEquals(rightY[i], synchronizedTabulatedFunction1.getY(i), 1e-9);
            assertEquals(rightX[i], synchronizedTabulatedFunction2.getX(i), 1e-9);
            assertEquals(rightY[i], synchronizedTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithZeroInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 1.0;
        int count = 6;

        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(function, xFrom, xTo, count));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(function, xFrom, xTo, count));

        double[] rightX = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] rightY = {4.0, 4.0, 4.0, 4.0, 4.0, 4.0};

        for (int i = 0; i < synchronizedTabulatedFunction1.getCount(); i++) {
            assertEquals(rightX[i], synchronizedTabulatedFunction1.getX(i), 1e-9);
            assertEquals(rightY[i], synchronizedTabulatedFunction1.getY(i), 1e-9);
            assertEquals(rightX[i], synchronizedTabulatedFunction2.getX(i), 1e-9);
            assertEquals(rightY[i], synchronizedTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testWithSetY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        synchronizedTabulatedFunction1.setY(3, 2.0);
        synchronizedTabulatedFunction2.setY(3, 2.0);

        assertEquals(2.0, synchronizedTabulatedFunction1.getY(3));
        assertEquals(2.0, synchronizedTabulatedFunction2.getY(3));
    }

    @Test
    void testWithIndexOfXY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(0, synchronizedTabulatedFunction1.indexOfX(1.0));
        assertEquals(2, synchronizedTabulatedFunction1.indexOfY(2.0));
        assertEquals(-1, synchronizedTabulatedFunction1.indexOfX(0.0));

        assertEquals(0, synchronizedTabulatedFunction2.indexOfX(1.0));
        assertEquals(2, synchronizedTabulatedFunction2.indexOfY(2.0));
        assertEquals(-1, synchronizedTabulatedFunction2.indexOfX(0.0));
    }

    @Test
    void testWithBounds(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(1.0, synchronizedTabulatedFunction1.leftBound());
        assertEquals(10.0, synchronizedTabulatedFunction1.rightBound());
        assertEquals(1.0, synchronizedTabulatedFunction2.leftBound());
        assertEquals(10.0, synchronizedTabulatedFunction2.rightBound());
    }

    @Test
    void testInterpolationWithinBounds(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(5.0, synchronizedTabulatedFunction1.apply(2.5), 1e-9);
        assertEquals(5.0, synchronizedTabulatedFunction2.apply(2.5), 1e-9);
    }

    @Test
    void testWithExtrapolationLeft(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(-1.5, synchronizedTabulatedFunction1.apply(0.5), 1e-9);
        assertEquals(-1.5, synchronizedTabulatedFunction2.apply(0.5), 1e-9);
    }

    @Test
    void testIteratorWithWhile(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        Iterator<Point> iterator1 = synchronizedTabulatedFunction1.iterator();
        Iterator<Point> iterator2 = synchronizedTabulatedFunction2.iterator();
        int index = 0;

        while (iterator1.hasNext() && iterator2.hasNext()) {
            Point point1 = iterator1.next();
            Point point2 = iterator2.next();
            assertEquals(synchronizedTabulatedFunction1.getX(index), point1.x, 1e-9);
            assertEquals(synchronizedTabulatedFunction1.getY(index), point1.y, 1e-9);
            assertEquals(synchronizedTabulatedFunction2.getX(index), point2.x, 1e-9);
            assertEquals(synchronizedTabulatedFunction2.getY(index), point2.y, 1e-9);
            index++;
        }

        assertEquals(synchronizedTabulatedFunction1.getCount(), index);
        assertEquals(synchronizedTabulatedFunction2.getCount(), index);
    }

    @Test
    void testIteratorWithFor(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction1 = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        SynchronizedTabulatedFunction synchronizedTabulatedFunction2 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        int index1 = 0;
        int index2 = 0;

        for(Point point1 : synchronizedTabulatedFunction1){
            assertEquals(synchronizedTabulatedFunction1.getX(index1), point1.x, 1e-9);
            assertEquals(synchronizedTabulatedFunction1.getY(index1), point1.y, 1e-9);
            ++index1;
        }

        for(Point point2 : synchronizedTabulatedFunction2){
            assertEquals(synchronizedTabulatedFunction2.getX(index2), point2.x, 1e-9);
            assertEquals(synchronizedTabulatedFunction2.getY(index2), point2.y, 1e-9);
            ++index2;
        }

        assertEquals(synchronizedTabulatedFunction1.getCount(), index1);
        assertEquals(synchronizedTabulatedFunction2.getCount(), index2);
    }

    @Test
    public void testGetCountSynchronously() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        Integer count = syncFunction.doSynchronously(SynchronizedTabulatedFunction::getCount);
        assertEquals(4, count);
    }

    @Test
    public void testSetYSynchronously() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        syncFunction.doSynchronously(f -> {
            f.setY(1, 10);
            return null;
        });

        assertEquals(10, syncFunction.getY(1));
    }

    @Test
    public void testGetXSynchronously() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        Double xValue = syncFunction.doSynchronously(f -> f.getX(2));
        assertEquals(3.0, xValue);
    }

    @Test
    public void testVoidOperationSynchronously() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        syncFunction.doSynchronously(f -> {
            f.setY(3, 12);
            return null;  // Используем Void-тип
        });

        assertEquals(12, syncFunction.getY(3));
    }

    @Test
    public void testMultipleOperationsSynchronously() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));

        Double result = syncFunction.doSynchronously(f -> {
            f.setY(0, 20);
            return f.getY(0);
        });

        assertEquals(20, result);
        assertEquals(20, syncFunction.getY(0));
    }
}

