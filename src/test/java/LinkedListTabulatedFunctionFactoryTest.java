import static org.junit.jupiter.api.Assertions.*;

import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.LinkedListTabulatedFunction;
import functions.StrictTabulatedFunction;

public class LinkedListTabulatedFunctionFactoryTest {
    @Test
    void testEqualClasses(){
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        assertInstanceOf(LinkedListTabulatedFunction.class, factory.create(new double[]{0, 1, 2}, new double[]{5, 4, 3}));
        assertInstanceOf(LinkedListTabulatedFunction.class, factory.create(new double[]{-10000, 0, 10000}, new double[]{5, 4, 3}));
        assertInstanceOf(LinkedListTabulatedFunction.class, factory.create(new double[]{0.000001, 1.000001, 2.000001}, new double[]{5, 4, 3}));
    }

    @Test
    void testStrict(){
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction tabulatedFunction = factory.createStrict(new double[]{0, 1, 2}, new double[]{5, 4, 3});
        assertInstanceOf(StrictTabulatedFunction.class, tabulatedFunction);

        tabulatedFunction.apply(1);
        assertThrows(UnsupportedOperationException.class, () -> tabulatedFunction.apply(3));
    }
}