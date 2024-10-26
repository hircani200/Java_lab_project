package mathapp.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import mathapp.concurrent.IntegrationTask;
import mathapp.functions.MathFunction;
import mathapp.functions.TabulatedFunction;

public class TabulatedIntegrationOperator implements MathFunction {
    private final ExecutorService executor;
    private final int numberOfThreads;

    public TabulatedIntegrationOperator() {
        this.numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public TabulatedIntegrationOperator(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public double integrate(TabulatedFunction function) throws ExecutionException, InterruptedException {
        double deltaX = (function.rightBound() - function.leftBound()) / numberOfThreads;
        double sumOfTrapezoids = 0;
        List<Future<Double>> futureList = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            double lower = function.leftBound() + i * deltaX;
            double upper = lower + deltaX;
            IntegrationTask task = new IntegrationTask(function, lower, upper);
            futureList.add(executor.submit(task));
        }

        for (Future<Double> point : futureList) {
            sumOfTrapezoids += point.get();
        }

        return sumOfTrapezoids;
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }

    public void shutdown() {
        executor.shutdown();
    }
}