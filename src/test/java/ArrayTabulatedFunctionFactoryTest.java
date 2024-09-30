import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.ArrayTabulatedFunction;

public class ArrayTabulatedFunctionFactoryTest {
    @Test
    void testEqualClasses(){
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{0, 1, 2}, new double[]{5, 4, 3}));
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{-10000, 0, 10000}, new double[]{5, 4, 3}));
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{0.000001, 1.000001, 2.000001}, new double[]{5, 4, 3}));
    }
}