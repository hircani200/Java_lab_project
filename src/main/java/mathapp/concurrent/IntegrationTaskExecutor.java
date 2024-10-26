package mathapp.concurrent;

import mathapp.functions.TabulatedFunction;
import mathapp.operations.TabulatedIntegrationOperator;
import java.util.concurrent.ExecutionException;

public class IntegrationTaskExecutor {

    private final TabulatedIntegrationOperator operator;

    public IntegrationTaskExecutor() {
        this.operator = new TabulatedIntegrationOperator();
    }

    public IntegrationTaskExecutor(int numberOfThreads) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than zero");
        }
        this.operator = new TabulatedIntegrationOperator(numberOfThreads);
    }

    public double executeIntegration(TabulatedFunction function) throws ExecutionException, InterruptedException {
        if (function == null) {
            throw new NullPointerException("The function cannot be null");
        }
        return operator.integrate(function);
    }

    public void shutdown() {
        operator.shutdown();
    }
}