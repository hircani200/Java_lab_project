package mathapp.concurrent;

import mathapp.functions.TabulatedFunction;
import java.util.concurrent.Callable;

public class IntegrationTask implements Callable<Double> {

    private final TabulatedFunction function;
    private final double lowerLimit, upperLimit;

    public IntegrationTask(TabulatedFunction function, double lowerLimit, double upperLimit) {
        if (function == null) {
            throw new NullPointerException("The function cannot be null");
        }
        if (lowerLimit >= upperLimit) {
            throw new IllegalArgumentException("Lower limit must be less than upper limit");
        }
        this.function = function;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public double calculateTrapezoidArea(int segments) {
        if (segments <= 0) {
            throw new IllegalArgumentException("Number of segments must be greater than zero");
        }

        double totalArea = 0.0;
        double segmentWidth = (upperLimit - lowerLimit) / segments;

        for (int i = 0; i < segments; i++) {
            double x0 = lowerLimit + i * segmentWidth;
            double x1 = lowerLimit + (i + 1) * segmentWidth;
            double area = (function.apply(x0) + function.apply(x1)) * segmentWidth / 2;
            totalArea += area;
        }

        return totalArea;
    }

    @Override
    public Double call() {
        int segments = 10000;
        return calculateTrapezoidArea(segments);
    }
}