package concurrent;

import functions.TabulatedFunction;

import java.util.concurrent.Callable;

public class IntegrationTask implements Callable<Double> {

    private final TabulatedFunction function;
    private final double lowerLimit, upperLimit;

    public IntegrationTask(TabulatedFunction function, double lowerLimit, double upperLimit) {
        this.function = function;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    private double getArrayOfTrapezoid() {
        return Math.abs((function.apply(lowerLimit) + function.apply(upperLimit)) * (lowerLimit-upperLimit)/2);
    }

    @Override
    public Double call() throws Exception {
        return getArrayOfTrapezoid();
    }
}
