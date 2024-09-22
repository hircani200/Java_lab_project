import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.RungeKuttaMethod;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import functions.SqrFunction;

public class RungeKuttaMethodTest {
    @Test
    public void testRungeKuttaMethodTest(){

        SqrFunction sqr = new SqrFunction();
        BiFunction<Double, Double, Double> function1 = (x,y) -> x*y;
        BiFunction<Double, Double, Double> function2 = (x, y) -> x;
        BiFunction<Double, Double, Double> function3 = (x, y) -> sqr.apply(x)+ (y);

        RungeKuttaMethod differencialEquationY1 = new RungeKuttaMethod(function1, 0, 1);
        RungeKuttaMethod differencialEquationY2 = new RungeKuttaMethod(function2, 1, 0.5);
        RungeKuttaMethod differencialEquationY3 = new RungeKuttaMethod(function3, 1, 1);

        assertEquals(1.645833333, differencialEquationY1.apply(1.0), 1e-9);
        assertEquals(0.50001, differencialEquationY2.apply(0.00001), 1e-9);
        assertEquals(1.248047933, differencialEquationY3.apply(0.111), 1e-9);
    }
}
