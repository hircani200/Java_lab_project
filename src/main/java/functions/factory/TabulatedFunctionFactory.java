package functions.factory;

import functions.TabulatedFunction;
import functions.UnmodifiableTabulatedFunction;
import functions.StrictTabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    default TabulatedFunction createUnmodifiable(double[] xValues, double[] yValues) {
        return new UnmodifiableTabulatedFunction(create(xValues, yValues));
    }

    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(create(xValues, yValues));
    }

    default TabulatedFunction createStrictUnmodifiable(double[] xValues, double[] yValues) {
        return new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(createStrict(xValues, yValues)));
    }
}
