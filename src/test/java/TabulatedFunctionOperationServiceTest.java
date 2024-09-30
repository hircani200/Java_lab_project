import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.LinkedListTabulatedFunction;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    void testAsPointsWithArrayTabulatedFunction() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xArray, yArray);

        Point[] points = TabulatedFunctionOperationService.asPoints(arrayTabulatedFunction);

        assertEquals(xArray.length, points.length);

        for (int i = 0; i < xArray.length; i++) {
            assertEquals(xArray[i], points[i].x, 1e-9);
            assertEquals(yArray[i], points[i].y, 1e-9);
        }
    }
    @Test
    void testAsPointsWithLinkedListTabulatedFunction() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {0.5, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xArray, yArray);

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertNotNull(points);
        assertEquals(xArray.length, points.length);
        for (int i = 0; i < points.length; i++) {
            assertNotNull(points[i]);
            assertEquals(xArray[i], points[i].x);
            assertEquals(yArray[i], points[i].y);
        }
    }
}