import static org.junit.jupiter.api.Assertions.*;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import functions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import functions.Point;

public class StrictTabulatedFunctionTests {

    @Test
    void testConstructorWithArrays(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(4, strictTabulatedFunction1.getCount());
        assertEquals(4, strictTabulatedFunction2.getCount());

        for (int i = 0; i < strictTabulatedFunction1.getCount(); i++) {
            assertEquals(xArray[i], strictTabulatedFunction1.getX(i), 1e-9);
            assertEquals(yArray[i], strictTabulatedFunction1.getY(i), 1e-9);
            assertEquals(xArray[i], strictTabulatedFunction2.getX(i), 1e-9);
            assertEquals(yArray[i], strictTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorArraysWithDuplicatesThrowsException(){

        double[] xArray = {1.0, 2.0, 1.0, 3.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorArraysWithUnsortedElementsThrowsException(){

        double[] xArray = {10.0, 2.0, 4.5, 1.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(ArrayIsNotSortedException.class, () -> new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testGetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        assertEquals(2.0, strictTabulatedFunction1.getX(-3));
        assertEquals(1.1, strictTabulatedFunction1.getY(-1));
    }

    @Test
    void testSetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        strictTabulatedFunction1.setY(-3, 1.0);

        assertEquals(1.0, strictTabulatedFunction1.getY(-3));
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(DifferentLengthOfArraysException.class, () -> new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(DifferentLengthOfArraysException.class, () -> new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorArraysWithOneElementThrowsException(){

        double[] xArray = {1.0};
        double[] yArray = {2.0};

        assertThrows(IllegalArgumentException.class, () -> new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        assertThrows(IllegalArgumentException.class, () -> new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));
    }

    @Test
    void testConstructorWithFunctionAndInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 4.0;
        int count = 7;

        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(function, xFrom, xTo, count));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(function, xFrom, xTo, count));

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        double[] rightY = {4.0, 6.25, 9.0, 12.25, 16, 20.25, 25.0};

        for (int i = 0; i < strictTabulatedFunction1.getCount(); i++) {
            assertEquals(rightX[i], strictTabulatedFunction1.getX(i), 1e-9);
            assertEquals(rightY[i], strictTabulatedFunction1.getY(i), 1e-9);
            assertEquals(rightX[i], strictTabulatedFunction2.getX(i), 1e-9);
            assertEquals(rightY[i], strictTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithZeroInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 1.0;
        int count = 6;

        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(function, xFrom, xTo, count));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(function, xFrom, xTo, count));

        double[] rightX = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] rightY = {4.0, 4.0, 4.0, 4.0, 4.0, 4.0};

        for (int i = 0; i < strictTabulatedFunction1.getCount(); i++) {
            assertEquals(rightX[i], strictTabulatedFunction1.getX(i), 1e-9);
            assertEquals(rightY[i], strictTabulatedFunction1.getY(i), 1e-9);
            assertEquals(rightX[i], strictTabulatedFunction2.getX(i), 1e-9);
            assertEquals(rightY[i], strictTabulatedFunction2.getY(i), 1e-9);
        }
    }

    @Test
    void testWithSetY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        strictTabulatedFunction1.setY(3, 2.0);
        strictTabulatedFunction2.setY(3, 2.0);

        assertEquals(2.0, strictTabulatedFunction1.getY(3));
        assertEquals(2.0, strictTabulatedFunction2.getY(3));
    }

    @Test
    void testWithIndexOfXY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(0, strictTabulatedFunction1.indexOfX(1.0));
        assertEquals(2, strictTabulatedFunction1.indexOfY(2.0));
        assertEquals(-1, strictTabulatedFunction1.indexOfX(0.0));

        assertEquals(0, strictTabulatedFunction2.indexOfX(1.0));
        assertEquals(2, strictTabulatedFunction2.indexOfY(2.0));
        assertEquals(-1, strictTabulatedFunction2.indexOfX(0.0));
    }

    @Test
    void testWithBounds(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertEquals(1.0, strictTabulatedFunction1.leftBound());
        assertEquals(10.0, strictTabulatedFunction1.rightBound());
        assertEquals(1.0, strictTabulatedFunction2.leftBound());
        assertEquals(10.0, strictTabulatedFunction2.rightBound());
    }

    @Test
    void testWithInterpolationThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction1.apply(9.0));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction2.apply(9.0));

    }

    @Test
    void testWithExtrapolationLeftThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));


        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction1.apply(0.1));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction2.apply(0.1));
    }

    @Test
    void testWithExtrapolationRightThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction1.apply(11.0));
        assertThrows(UnsupportedOperationException.class, () -> strictTabulatedFunction2.apply(11.0));
    }

    @Test
    void testIteratorWithWhile(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        Iterator<Point> iterator1 = strictTabulatedFunction1.iterator();
        Iterator<Point> iterator2 = strictTabulatedFunction2.iterator();
        int index = 0;

        while (iterator1.hasNext() && iterator2.hasNext()) {
            Point point1 = iterator1.next();
            Point point2 = iterator2.next();
            assertEquals(strictTabulatedFunction1.getX(index), point1.x, 1e-9);
            assertEquals(strictTabulatedFunction1.getY(index), point1.y, 1e-9);
            assertEquals(strictTabulatedFunction2.getX(index), point2.x, 1e-9);
            assertEquals(strictTabulatedFunction2.getY(index), point2.y, 1e-9);
            index++;
        }

        assertEquals(strictTabulatedFunction1.getCount(), index);
        assertEquals(strictTabulatedFunction2.getCount(), index);
    }

    @Test
    void testIteratorWithFor(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        StrictTabulatedFunction strictTabulatedFunction1 = new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));
        StrictTabulatedFunction strictTabulatedFunction2 = new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray));

        int index1 = 0;
        int index2 = 0;

        for(Point point1 : strictTabulatedFunction1){
            assertEquals(strictTabulatedFunction1.getX(index1), point1.x, 1e-9);
            assertEquals(strictTabulatedFunction1.getY(index1), point1.y, 1e-9);
            ++index1;
        }

        for(Point point2 : strictTabulatedFunction2){
            assertEquals(strictTabulatedFunction2.getX(index2), point2.x, 1e-9);
            assertEquals(strictTabulatedFunction2.getY(index2), point2.y, 1e-9);
            ++index2;
        }

        assertEquals(strictTabulatedFunction1.getCount(), index1);
        assertEquals(strictTabulatedFunction2.getCount(), index2);
    }

    @Test
    void testStrictWithUnmodifiable(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction1 = new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray)));
        UnmodifiableTabulatedFunction unmodifiableTabulatedFunction2 = new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(new ArrayTabulatedFunction(xArray, yArray)));


        assertThrows(UnsupportedOperationException.class, () -> unmodifiableTabulatedFunction1.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableTabulatedFunction2.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableTabulatedFunction1.setY(-3, 1.0));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableTabulatedFunction2.setY(-3, 1.0));
    }
}

