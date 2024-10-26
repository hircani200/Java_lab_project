package functions;

import static org.junit.jupiter.api.Assertions.*;


import mathapp.exceptions.ArrayIsNotSortedException;
import mathapp.exceptions.DifferentLengthOfArraysException;
import mathapp.functions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

public class UnmodifiableTabulatedFunctionTest {

    @Test
    void testConstructorWithArrays(){

        double[] xArray = {2.0, 2.5, 4.8, 10.0};
        double[] yArray = {0.5, 3.0, 7.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction1 = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction2 = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, unmodifiableTabulatedFunction1.getCount());
        assertEquals(4, unmodifiableTabulatedFunction2.getCount());

        for (int i = 0; i < unmodifiableTabulatedFunction1.getCount(); i++) {
            assertEquals(xArray[i], unmodifiableTabulatedFunction1.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableTabulatedFunction1.getY(i), 1e-9);
            assertEquals(xArray[i], unmodifiableTabulatedFunction2.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorArraysWithDuplicatesThrowsException(){

        double[] xArray = {2.0, 2.0, 1.0, 3.0};
        double[] yArray = {1.0, 3.0, 2.0, 3.7};

        assertThrows(ArrayIsNotSortedException.class, () -> new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorArraysWithUnsortedElementsThrowsException(){

        double[] xArray = {10.0, 7.0, 4.5, 1.0};
        double[] yArray = {4.0, 10.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testGetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction1 = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(2.0, unmodifiableTabulatedFunction1.getX(-3));
        assertEquals(1.1, unmodifiableTabulatedFunction1.getY(-1));
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 5.0, 6.5};
        double[] yArray = {1.0, 3.0, 7.0, 10.1};

        assertThrows(DifferentLengthOfArraysException.class, () -> new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(DifferentLengthOfArraysException.class, () -> new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    public void testSetYThrowsException() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction.setY(1, 5.0));
    }

    @Test
    public void testConstructorAndGetters() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, unmodifiableFunction.getCount());
        for (int i = 0; i < unmodifiableFunction.getCount(); i++) {
            assertEquals(xArray[i], unmodifiableFunction.getX(i), 1e-9);
            assertEquals(yArray[i], unmodifiableFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testApply() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(4.0, unmodifiableFunction.apply(2.0), 1e-9);
    }

    @Test
    public void testIndexOfXAndY() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(1, unmodifiableFunction.indexOfX(2.0));
        assertEquals(2, unmodifiableFunction.indexOfY(9.0));
        assertEquals(-1, unmodifiableFunction.indexOfX(5.0));
    }

    @Test
    public void testBounds() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(1.0, unmodifiableFunction.leftBound(), 1e-9);
        assertEquals(4.0, unmodifiableFunction.rightBound(), 1e-9);
    }

    @Test
    void testIteratorWithWhile(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableFunction1 = new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        UnmodifiableTabulatedFunction unmodifiableFunction2 = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        Iterator<Point> iterator1 = unmodifiableFunction1.iterator();
        Iterator<Point> iterator2 = unmodifiableFunction2.iterator();
        int index = 0;

        while (iterator1.hasNext() && iterator2.hasNext()) {
            Point point1 = iterator1.next();
            Point point2 = iterator2.next();
            assertEquals(unmodifiableFunction1.getX(index), point1.x, 1e-9);
            assertEquals(unmodifiableFunction1.getY(index), point1.y, 1e-9);
            assertEquals(unmodifiableFunction2.getX(index), point2.x, 1e-9);
            assertEquals(unmodifiableFunction2.getY(index), point2.y, 1e-9);
            index++;
        }

        assertEquals(unmodifiableFunction1.getCount(), index);
        assertEquals(unmodifiableFunction2.getCount(), index);
    }

    @Test
    public void testIteratorWithForEach() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        int index = 0;
        for (Point point : unmodifiableFunction) {
            assertEquals(xArray[index], point.x, 1e-9);
            assertEquals(yArray[index], point.y, 1e-9);
            index++;
        }
        assertEquals(xArray.length, index);
    }

    @Test
    void testUnmodifiableWithStrict(){
        double[] xArray = {1.0, 5.0, 6.5, 14.0};
        double[] yArray = {0.4, 3.0, 1.5, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new UnmodifiableTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        UnmodifiableTabulatedFunction strictTabulatedFunction2 = new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));


        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction1.apply(6));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction2.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction1.setY(-3, 1.0));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction2.setY(-6, 7.0));
    }
}
