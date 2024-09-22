import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import functions.LinkedListTabulatedFunction;
import functions.MathFunction;

public class LinkedListTabulatedFunctionTest {

    @Test
    void testConstructorWithArrays(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(4, linkedList.getCount());

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(xArray[i], linkedList.getX(i), 1e-9);
            assertEquals(yArray[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorArraysWithDuplicatesThrowsException(){

        double[] xArray = {1.0, 2.0, 1.0, 3.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorArraysWithUnsortedElementsThrowsException(){

        double[] xArray = {10.0, 2.0, 4.5, 1.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorArraysWithOneElementThrowsException(){

        double[] xArray = {1.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorWithFunctionAndInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 4.0;
        int count = 6;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        double[] rightY = {4.0, 6.25, 9.0, 12.25, 16, 20.25, 25.0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithZeroInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 1.0;
        int count = 6;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] rightY = {4.0, 4.0, 4.0, 4.0, 4.0, 4.0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testWithSetY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        linkedList.setY(3, 2.0);

        assertEquals(2.0, linkedList.getY(3));
    }

    @Test
    void testWithIndexOfXY(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);


        assertEquals(0, linkedList.indexOfX(1.0));
        assertEquals(2, linkedList.indexOfY(2.0));
        assertEquals(-1, linkedList.indexOfX(0.0));
    }

    @Test
    void testWithBounds(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(1.0, linkedList.leftBound());
        assertEquals(10.0, linkedList.rightBound());
    }

    @Test
    void testWithInterpolation(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(1.263636363, linkedList.apply(9.0), 1e-9);
    }

    @Test
    void testWithExtrapolationLeft(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(-2.7, linkedList.apply(0.1), 1e-9);
    }

    @Test
    void testWithExtrapolationRight(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(0.936363636, linkedList.apply(11.0), 1e-9);
    }
}