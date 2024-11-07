package mathapp.functions;

import static org.junit.jupiter.api.Assertions.*;

import mathapp.exceptions.ArrayIsNotSortedException;
import mathapp.exceptions.InterpolationException;
import mathapp.exceptions.DifferentLengthOfArraysException;
import mathapp.functions.LinkedListTabulatedFunction;
import mathapp.functions.MathFunction;
import mathapp.functions.Point;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


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

        assertThrows(ArrayIsNotSortedException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorArraysWithUnsortedElementsThrowsException(){

        double[] xArray = {10.0, 2.0, 4.5, 1.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(ArrayIsNotSortedException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testGetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(2.0, linkedList.getX(-3));
        assertEquals(1.1, linkedList.getY(-1));
    }

    @Test
    void testSetterWithIndexLesserThanZero(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        linkedList.setY(-3, 1.0);

        assertEquals(1.0, linkedList.getY(-3));
    }

    @Test
    void testConstructorArraysWithDifferentSizeThrowsException(){

        double[] xArray = {1.0, 2.0, 4.5};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(DifferentLengthOfArraysException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorArraysWithOneElementThrowsException(){

        double[] xArray = {1.0};
        double[] yArray = {2.0};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testConstructorWithFunctionAndInterval(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 1.0;
        double xTo = 4.0;
        int count = 7;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        double[] rightY = {4.0, 6.25, 9.0, 12.25, 16, 20.25, 25.0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithXFromBiggerThanXTo(){
        MathFunction function = x -> x*x + 2*x + 1;
        double xFrom = 4.0;
        double xTo = 1.0;
        int count = 7;

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
        assertEquals(-1, linkedList.indexOfY(5.0));
        assertEquals(-1, linkedList.indexOfX(0.0));

        assertThrows(IllegalArgumentException.class,
                () -> linkedList.interpolate(3.5, linkedList.floorNodeOfX(0)));
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
        assertEquals(2.6, linkedList.apply(3.0), 1e-9);
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
    @Test
    void testInterpolationException() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};

        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        assertThrows(InterpolationException.class,
                () -> linkedListFunction.interpolate(3.5, linkedListFunction.floorNodeOfX(1)));
    }

    @Test
    void testInterpolationException2() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};

        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        assertThrows(InterpolationException.class,
                () -> linkedListFunction.interpolate(3.5, 1));
    }

    @Test
    void testInsertIntoBeginning() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{1.0, 3.0, 5.0}, new double[]{2.0, 6.0, 10.0});
        function.insert(0.5, 1.0);
        assertEquals(4, function.getCount());
        assertEquals(0.5, function.getX(0));
        assertEquals(1.0, function.getY(0));
    }

    @Test
    void testInsertIntoMiddle() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{1.0, 3.0, 5.0}, new double[]{2.0, 6.0, 10.0});
        function.insert(2.0, 3.0);
        assertEquals(4, function.getCount());
        assertEquals(2.0, function.getX(1));
        assertEquals(3.0, function.getY(1));
    }

    @Test
    void testInsertIntoEnd() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{1.0, 3.0, 5.0}, new double[]{2.0, 6.0, 10.0});
        function.insert(6.0, 12.0);
        assertEquals(4, function.getCount());
        assertEquals(6.0, function.getX(3));
        assertEquals(12.0, function.getY(3));
    }

    @Test
    void testUpdateYIfXExists() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[]{1.0, 3.0, 5.0}, new double[]{2.0, 6.0, 10.0});
        function.insert(3.0, 7.0);  // Обновляем y для x = 3.0
        assertEquals(3, function.getCount());
        assertEquals(3.0, function.getX(1));
        assertEquals(7.0, function.getY(1));
    }

    @Test
    void testRemove(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        linkedList.remove(0);

        double[] xNewArray = {2.0, 4.5, 10.0};
        double[] yNewArray = {3.0, 2.0, 1.1};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(xNewArray[i], linkedList.getX(i), 1e-9);
            assertEquals(yNewArray[i], linkedList.getY(i), 1e-9);
        }

        linkedList.remove(2);

        double[] xNewArray1 = {2.0, 4.5};
        double[] yNewArray1 = {3.0, 2.0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(xNewArray1[i], linkedList.getX(i), 1e-9);
            assertEquals(yNewArray1[i], linkedList.getY(i), 1e-9);
        }

        linkedList.remove(-4);
        double[] xNewArray2 = {4.5};
        double[] yNewArray2 = {2.0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(xNewArray2[i], linkedList.getX(i), 1e-9);
            assertEquals(yNewArray2[i], linkedList.getY(i), 1e-9);
        }

        linkedList.remove(0);
        assertThrows(IllegalArgumentException.class,
                () -> linkedList.remove(0));
    }

    @Test
    void testIteratorWithWhile(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xArray, yArray);

        Iterator<Point> iterator = linkedListFunction.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(linkedListFunction.getX(index), point.x, 1e-9);
            assertEquals(linkedListFunction.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(linkedListFunction.getCount(), index);
    }

    @Test
    void testIteratorWithFor(){
        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xArray, yArray);

        int index = 0;

        for(Point point : linkedListFunction){
            assertEquals(linkedListFunction.getX(index), point.x, 1e-9);
            assertEquals(linkedListFunction.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(linkedListFunction.getCount(), index);

    }
}
