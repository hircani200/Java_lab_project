package functions.factory;

import functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
    TabulatedFunction createStrict(double[] xValues, double[] yValues);
}
