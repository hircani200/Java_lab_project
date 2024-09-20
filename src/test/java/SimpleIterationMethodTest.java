import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.SimpleIterationMethod;
import functions.MathFunction;
import org.junit.jupiter.api.Test;

public class SimpleIterationMethodTest {

    @Test
    public void testSolveCubicEquation() {
        MathFunction phi = x -> x - (Math.pow(x, 3) - 8) / (3 * Math.pow(x, 2));
        SimpleIterationMethod solver = new SimpleIterationMethod(phi, 1e-6, 1000);

        double result = solver.solve(2.5);

        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSolveWithInitialCloseToSolution() {
        MathFunction phi = x -> x - (Math.pow(x, 3) - 8) / (3 * Math.pow(x, 2));
        SimpleIterationMethod solver = new SimpleIterationMethod(phi, 1e-6, 1000);

        double result = solver.solve(2.0001);

        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSolveWithLargeInitialGuess() {
        MathFunction phi = x -> x - (Math.pow(x, 3) - 8) / (3 * Math.pow(x, 2));
        SimpleIterationMethod solver = new SimpleIterationMethod(phi, 1e-6, 1000);

        double result = solver.solve(1000.0);

        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSolveWithSmallInitialGuess() {
        MathFunction phi = x -> x - (Math.pow(x, 3) - 8) / (3 * Math.pow(x, 2));
        SimpleIterationMethod solver = new SimpleIterationMethod(phi, 1e-6, 1000);

        double result = solver.solve(0.01);

        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void testSolveNoDivision() {
        MathFunction phi = x -> (x * x + 3) / 4;
        SimpleIterationMethod solver = new SimpleIterationMethod(phi, 1e-6, 1000);

        double result = solver.solve(1.5);

        assertEquals(1.0, result, 1e-6);
    }
}