import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.LinkedListTabulatedFunction;
import operations.TabulatedFunctionOperationService;
import exceptions.InconsistentFunctionsException;
import functions.factory.*;
import functions.TabulatedFunction;
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
    @Test
    public void testAdditionWithArrayTabulatedFunction() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {1.0, 2.0, 3.0};
        double[] yValues2 = {4.0, 5.0, 6.0};

        TabulatedFunction f1 = factory.create(xValues, yValues1);
        TabulatedFunction f2 = factory.create(xValues, yValues2);

        TabulatedFunction result = service.add(f1, f2);
        double[] expectedY = {5.0, 7.0, 9.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void testSubtractionWithDifferentFactories() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {5.0, 6.0, 7.0};
        double[] yValues2 = {1.0, 2.0, 3.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues1);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues2);

        TabulatedFunction result = service.subtract(f1, f2);
        double[] expectedY = {4.0, 4.0, 4.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void testInconsistentFunctionsExceptionWhenDifferentLengths() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues1 = {1.0, 2.0, 3.0};
        double[] xValues2 = {1.0, 2.0};
        double[] yValues1 = {1.0, 2.0, 3.0};
        double[] yValues2 = {4.0, 5.0};

        TabulatedFunction f1 = factory.create(xValues1, yValues1);
        TabulatedFunction f2 = factory.create(xValues2, yValues2);

        assertThrows(InconsistentFunctionsException.class, () -> service.add(f1, f2));
    }

    @Test
    public void testInconsistentFunctionsExceptionWhenDifferentXValues() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues1 = {1.0, 2.0, 3.0};
        double[] xValues2 = {1.0, 3.0, 4.0};
        double[] yValues1 = {1.0, 2.0, 3.0};
        double[] yValues2 = {4.0, 5.0, 6.0};

        TabulatedFunction f1 = factory.create(xValues1, yValues1);
        TabulatedFunction f2 = factory.create(xValues2, yValues2);

        assertThrows(InconsistentFunctionsException.class, () -> service.add(f1, f2));
    }

    @Test
    public void testSetterAndGetterFactory() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();

        assertInstanceOf(ArrayTabulatedFunctionFactory.class, service.getFactory());

        TabulatedFunctionFactory newFactory = new LinkedListTabulatedFunctionFactory();
        service.setFactory(newFactory);

        assertSame(newFactory, service.getFactory());
    }
}