package mathapp.functions.factory;

import static org.junit.jupiter.api.Assertions.*;

import mathapp.functions.StrictTabulatedFunction;
import mathapp.functions.TabulatedFunction;
import mathapp.functions.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import mathapp.functions.ArrayTabulatedFunction;

public class ArrayTabulatedFunctionFactoryTest {
    @Test
    void testEqualClasses(){
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{0, 1, 2}, new double[]{5, 4, 3}));
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{-10000, 0, 10000}, new double[]{5, 4, 3}));
        assertInstanceOf(ArrayTabulatedFunction.class, factory.create(new double[]{0.000001, 1.000001, 2.000001}, new double[]{5, 4, 3}));
    }

    @Test
    void testStrict(){
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction tabulatedFunction = factory.createStrict(new double[]{0, 1, 2}, new double[]{5, 4, 3});
        assertInstanceOf(StrictTabulatedFunction.class, tabulatedFunction);

        tabulatedFunction.apply(1);
        assertThrows(UnsupportedOperationException.class, () -> tabulatedFunction.apply(3));
    }
}