package concurrent;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTaskTest {

    private IntegrationTaskExecutor executor;

    @BeforeEach
    public void setUp() {
        executor = new IntegrationTaskExecutor();
    }

    @AfterEach
    public void tearDown() {
        executor.shutdown();
    }

    @Test
    public void testIntegrationWithArrayTabulatedFunctionLinear() throws ExecutionException, InterruptedException {
        double[] xValues = {0, 1};
        double[] yValues = {0, 1};
        ArrayTabulatedFunction linearFunction = new ArrayTabulatedFunction(xValues, yValues);

        double result = executor.executeIntegration(linearFunction);
        double expected = 0.5;

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testIntegrationWithArrayTabulatedFunctionQuadratic() throws ExecutionException, InterruptedException {
        double[] xValues = {0.0, 0.5, 1.0};
        double[] yValues = {0.0, 0.25, 1.0};
        ArrayTabulatedFunction quadraticFunction = new ArrayTabulatedFunction(xValues, yValues);

        double result = executor.executeIntegration(quadraticFunction);
        double expected = 1.0 / 3.0;

        assertEquals(expected, result, 0.1);
    }

    @Test
    public void testIntegrationWithLinkedListTabulatedFunctionLinear() throws ExecutionException, InterruptedException {
        double[] xValues = {0.0, 1.0};
        double[] yValues = {0.0, 1.0};
        LinkedListTabulatedFunction linearFunction = new LinkedListTabulatedFunction(xValues, yValues);

        double result = executor.executeIntegration(linearFunction);
        double expected = 0.5;

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testIntegrationWithLinkedListTabulatedFunctionConstant() throws ExecutionException, InterruptedException {
        double[] xValues = {0.0, 1.0};
        double[] yValues = {5.0, 5.0};
        LinkedListTabulatedFunction constantFunction = new LinkedListTabulatedFunction(xValues, yValues);

        double result = executor.executeIntegration(constantFunction);
        double expected = 5.0;

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testIntegrationWithArrayTabulatedFunctionCustom() throws ExecutionException, InterruptedException {
        double[] xValues = {0.0, 2.0, 4.0};
        double[] yValues = {0.0, 2.0, 4.0};
        ArrayTabulatedFunction customFunction = new ArrayTabulatedFunction(xValues, yValues);

        double result = executor.executeIntegration(customFunction);
        double expected = 8.0;

        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testIntegrationWithInvalidBounds() {
        double[] xValues = {0.0, 1.0};
        double[] yValues = {0.0, 1.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertThrows(IllegalArgumentException.class, () -> new IntegrationTask(function, 2, 1).call());
    }

    @Test
    public void testIntegrationWithNegativeSegments() {
        double[] xValues = {0.0, 1.0};
        double[] yValues = {0.0, 1.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        IntegrationTask task = new IntegrationTask(function, 0, 1);
        assertThrows(IllegalArgumentException.class, () -> task.calculateTrapezoidArea(-1));
    }

    @Test
    public void testIntegrationWithZeroThreads() {
        assertThrows(IllegalArgumentException.class, () -> new IntegrationTaskExecutor(0));
    }

    @Test
    public void testIntegrationWithNullFunction() {
        assertThrows(NullPointerException.class, () -> executor.executeIntegration(null));
    }

    @Test
    public void testIntegrationWithSinglePoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            double[] xValues = {1};
            double[] yValues = {5};
            new ArrayTabulatedFunction(xValues, yValues);
        });
    }
}