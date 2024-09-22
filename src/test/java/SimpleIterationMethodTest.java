import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import functions.SimpleIterationMethod;
import functions.MathFunction;
import org.junit.jupiter.api.Test;

public class SimpleIterationMethodTest {

    @Test
    public void testFindRootConverges() {
        // g(x) = (x^2 + 2) / 3, ожидаемый корень примерно 1.0
        MathFunction g = x -> (x * x + 2) / 3;

        SimpleIterationMethod sim = new SimpleIterationMethod(g, 1e-5, 100);
        double root = sim.findRoot(1.0);
        assertEquals(1.0, root, 1e-5);
    }

    @Test
    public void testFindRootDiverges() {
        // g(x) = x + 1, ожидаемое исключение
        MathFunction g = x -> x + 1;

        SimpleIterationMethod sim = new SimpleIterationMethod(g, 1e-5, 5);
        Exception exception = assertThrows(RuntimeException.class, () -> sim.findRoot(0));
        assertEquals("Root not found within the maximum number of iterations", exception.getMessage());
    }

    @Test
    public void testInvalidResultHandling() {
        // g(x) = 1 / (x - 1), ожидаемое исключение
        MathFunction g = x -> 1 / (x - 1);

        SimpleIterationMethod sim = new SimpleIterationMethod(g, 1e-5, 100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sim.findRoot(1.0); // Этот вызов должен вызвать исключение
        });
        assertTrue(exception.getMessage().contains("Function g(x) returned an invalid result"));
    }

    @Test
    public void testEdgeCase() {
        // g(x) = x, ожидаемый корень 0
        MathFunction g = x -> x;

        SimpleIterationMethod sim = new SimpleIterationMethod(g, 1e-5, 100);
        double root = sim.findRoot(0.0);
        assertEquals(0.0, root, 1e-5);
    }

    @Test
    public void testMultipleIterations() {
        // g(x) = (x + 2) / 3, ожидаемый корень 1.0.
        MathFunction g = x -> (x + 2) / 3;

        SimpleIterationMethod sim = new SimpleIterationMethod(g, 1e-5, 100);
        double root = sim.findRoot(0.0);
        assertEquals(1.0, root, 1e-5);
    }
}