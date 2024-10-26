package mathapp.operations;

import mathapp.functions.Point;
import mathapp.functions.TabulatedFunction;
import mathapp.functions.factory.ArrayTabulatedFunctionFactory;
import mathapp.functions.factory.TabulatedFunctionFactory;
import mathapp.concurrent.SynchronizedTabulatedFunction;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction>{

    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        double[] xValues = new double[function.getCount()];
        double[] yValues = new double[function.getCount()];

        for (int i = 0; i < points.length-1; i++) {
            xValues[i] = function.getX(i);
            yValues[i] = (function.getY(i+1) - function.getY(i))/(function.getX(i+1)-function.getX(i));
        }
        xValues[points.length-1] = function.getX(points.length-1);
        yValues[points.length-1] = (function.getY(points.length-2) - function.getY(points.length-1))/(function.getX(points.length-2)-function.getX(points.length-1));

        return factory.create(xValues, yValues);
    }

    public  synchronized TabulatedFunction deriveSynchronously(TabulatedFunction function) {
        if (!(function instanceof SynchronizedTabulatedFunction)) {
            function = new SynchronizedTabulatedFunction(function);
        }

        SynchronizedTabulatedFunction syncFunction = (SynchronizedTabulatedFunction) function;
        return syncFunction.doSynchronously(this::derive);
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }
}
