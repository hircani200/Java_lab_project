package operations;

import static org.junit.jupiter.api.Assertions.*;

import mathapp.concurrent.SynchronizedTabulatedFunction;
import mathapp.functions.TabulatedFunction;
import mathapp.functions.factory.ArrayTabulatedFunctionFactory;
import mathapp.functions.factory.LinkedListTabulatedFunctionFactory;
import mathapp.operations.TabulatedDifferentialOperator;
import org.junit.jupiter.api.Test;

import mathapp.functions.ArrayTabulatedFunction;
import mathapp.functions.LinkedListTabulatedFunction;

public class TabulatedDifferentialOperatorTest {

    @Test
    void testConstructorWithoutParameters(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        assertInstanceOf(ArrayTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testConstructor(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        assertInstanceOf(LinkedListTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testSetFactory(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        operator.setFactory(new LinkedListTabulatedFunctionFactory());
        assertInstanceOf(LinkedListTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testApplyThrowsException(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        assertThrows(UnsupportedOperationException.class, () -> operator.apply(4));

    }

    @Test
    void testDeriveWithCreateLinkedListArrays(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(operator.getFactory().create(new double[] {-3, -1.5, 0, 1.5, 3}, new double[] {4, 0.25, 1, 6.25, 16}));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }
    }

    @Test
    void testDeriveWithNewLinkedListFunction(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(new LinkedListTabulatedFunction(x -> x*x + 2*x + 1, -3, 3, 5));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }
    }

    @Test
    void testDeriveWithCreateArrayFunctionWithArrays(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(operator.getFactory().create(new double[] {-3, -1.5, 0, 1.5, 3}, new double[] {4, 0.25, 1, 6.25, 16}));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }
    }

    @Test
    void testDeriveWithNewArrayFunction(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(new ArrayTabulatedFunction(x -> x*x + 2*x + 1, -3, 3, 5));

        double[] xValues = new double[] {-3, -1.5, 0, 1.5, 3};
        double[] yValues = new double[] {-4, -1, 2, 5, 8};
        double maxEpsilon = function.getY(1) - function.getY(0);

        for (int i = 0; i < function.getCount()-1; i++) {
            if((function.getY(i+1) - function.getY(i))>maxEpsilon){
                maxEpsilon = function.getY(i+1) - function.getY(i);
            }
        }

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i), maxEpsilon);
        }
    }
    @Test
    public void testDeriveSynchronouslyOnSimpleFunction() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        TabulatedFunction function = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());

        TabulatedFunction derivedFunction = operator.deriveSynchronously(function);

        for (int i = 0; i < derivedFunction.getCount(); i++) {
            assertEquals(2.0, derivedFunction.getY(i));
        }
    }

    @Test
    public void testDeriveSynchronouslyOnNonLinearFunction() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};

        TabulatedFunction function = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());

        TabulatedFunction derivedFunction = operator.deriveSynchronously(function);

        assertEquals(3.0, derivedFunction.getY(0), 1e-5);
        assertEquals(5.0, derivedFunction.getY(1), 1e-5);
        assertEquals(7.0, derivedFunction.getY(2), 1e-5);
    }

    @Test
    public void testDeriveSynchronouslyWithAlreadySynchronizedFunction() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0};

        TabulatedFunction function = new SynchronizedTabulatedFunction(new ArrayTabulatedFunctionFactory().create(xValues, yValues));
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());

        TabulatedFunction derivedFunction = operator.deriveSynchronously(function);

        for (int i = 0; i < derivedFunction.getCount(); i++) {
            assertEquals(2.0, derivedFunction.getY(i));
        }
    }

    @Test
    public void testDeriveSynchronouslyOnSmallDataSet() {
        double[] xValues = {1.0, 2.0};
        double[] yValues = {3.0, 7.0};

        TabulatedFunction function = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());

        TabulatedFunction derivedFunction = operator.deriveSynchronously(function);

        assertEquals(4.0, derivedFunction.getY(0), 1e-5);
    }
}
