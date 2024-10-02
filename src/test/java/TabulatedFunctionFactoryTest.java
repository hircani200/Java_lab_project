import functions.factory.*;
import functions.UnmodifiableTabulatedFunction;
import exceptions.ArrayIsNotSortedException;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TabulatedFunctionFactoryTest {
    @Test
    public void testCreateUnmodifiable() {
        TabulatedFunctionFactory factory1 = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory2 = new LinkedListTabulatedFunctionFactory();
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};

        TabulatedFunction unmodifiableFunction1 = factory1.createUnmodifiable(xValues, yValues);
        TabulatedFunction unmodifiableFunction2 = factory2.createUnmodifiable(xValues, yValues);

        assertInstanceOf(UnmodifiableTabulatedFunction.class, unmodifiableFunction1);
        assertInstanceOf(UnmodifiableTabulatedFunction.class, unmodifiableFunction2);

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction1.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableFunction2.setY(0, 10));
    }
    @Test
    public void testCreateStrictUnmodifiableArrayTabulatedFunction() {
        TabulatedFunctionFactory factory1 = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory2 = new LinkedListTabulatedFunctionFactory();
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};

        TabulatedFunction strictUnmodifiableFunction1 = factory1.createStrictUnmodifiable(xValues, yValues);
        TabulatedFunction strictUnmodifiableFunction2 = factory2.createStrictUnmodifiable(xValues, yValues);

        assertInstanceOf(UnmodifiableTabulatedFunction.class, strictUnmodifiableFunction1);
        assertInstanceOf(UnmodifiableTabulatedFunction.class, strictUnmodifiableFunction2);

        assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiableFunction1.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiableFunction2.setY(0, 10));

        double[] wrongXValues = {1, 3, 2};
        assertThrows(ArrayIsNotSortedException.class, () -> factory1.createStrictUnmodifiable(wrongXValues, yValues));
        assertThrows(ArrayIsNotSortedException.class, () -> factory2.createStrictUnmodifiable(wrongXValues, yValues));
    }
}