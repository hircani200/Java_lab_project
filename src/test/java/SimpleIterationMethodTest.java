import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import functions.SimpleIterationMethod;
import functions.MathFunction;
import org.junit.jupiter.api.Test;

public class SimpleIterationMethodTest {

    @Test
    public void testSingleEquation() {
        // Уравнение x = (x + 2) / 3, точное решение: x = 1
        MathFunction equation = x -> (x + 2) / 3;

        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 0;
        double root = iterationMethod.apply(initialGuess);

        assertEquals(1.0, root, 1e-6);
    }

    @Test
    public void testSystemOfEquations() {
        // Система уравнений:
        // x1 = (x2 + 2) / 3
        // x2 = (x1 + 1) / 2
        MathFunction equation1 = x -> (x + 2) / 3;
        MathFunction equation2 = x -> (x + 1) / 2;

        MathFunction[] equations = new MathFunction[]{equation1, equation2};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 0;
        double root = iterationMethod.apply(initialGuess);

        assertTrue(Math.abs(root - 1.0) < 1e-9 || Math.abs(root - 1.5) < 1e-9);
    }

    @Test
    public void testNoConvergence() {
        // Уравнение, которое не сходится
        MathFunction equation = x -> 2 * x;  // x = 2x не имеет фиксированной точки

        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        assertThrows(RuntimeException.class, () -> iterationMethod.apply(1.0));
    }

    @Test
    public void testEdgeCase() {
        // Уравнение, у которого решение уже известно: x = 1
        MathFunction equation = x -> x;

        MathFunction[] equations = new MathFunction[]{equation};
        SimpleIterationMethod iterationMethod = new SimpleIterationMethod(equations, 1e-9, 100);

        double initialGuess = 1.0;
        double root = iterationMethod.apply(initialGuess);

        assertEquals(1.0, root, 1e-9);
    }
}
