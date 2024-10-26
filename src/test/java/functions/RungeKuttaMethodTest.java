package functions;

import static org.junit.jupiter.api.Assertions.*;

import mathapp.functions.RungeKuttaMethod;
import mathapp.functions.SqrFunction;
import org.junit.jupiter.api.Test;

public class RungeKuttaMethodTest {
    @Test
    void testRungeKuttaMethodTest(){

        SqrFunction sqr = new SqrFunction();

        RungeKuttaMethod function1 = new RungeKuttaMethod((x, y) -> x*y, 0, 1);
        RungeKuttaMethod function2 = new RungeKuttaMethod((x, _) -> x , 1, 0.5);
        RungeKuttaMethod function3 = new RungeKuttaMethod((x, y) -> sqr.apply(x)+ (y), 1, 1);


        assertEquals(1.645833333, function1.apply(1.0), 1e-9);
        assertEquals(0.50001, function2.apply(0.00001), 1e-9);
        assertEquals(1.248047933, function3.apply(0.111), 1e-9);
    }
    @Test
    void testConstructorThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new RungeKuttaMethod((x, _) -> 1/x, 0, 2));
    }
    @Test
    void testApplyThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new RungeKuttaMethod((x, _) -> 1/x, -1, 2).apply(1));
    }
}
