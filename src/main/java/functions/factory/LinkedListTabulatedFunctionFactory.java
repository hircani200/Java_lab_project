package functions.factory;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.StrictTabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Override
    public TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues));
    }
}
