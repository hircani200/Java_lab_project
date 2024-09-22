package functions;

public class SimpleIterationMethod implements MathFunction {

    private final MathFunction g;  // Функция g(x) в уравнении x = g(x)
    private final double epsilon;   // Точность
    private final int maxIterations; // Максимальное количество итераций

    public SimpleIterationMethod(MathFunction g, double epsilon, int maxIterations) {
        this.g = g;
        this.epsilon = epsilon;
        this.maxIterations = maxIterations;
    }

    public double findRoot(double initialGuess) {
        double x0 = initialGuess;
        double x1;
        for (int i = 0; i < maxIterations; i++) {
            x1 = apply(x0);
            if (Math.abs(x1 - x0) < epsilon) {
                return x1; // нашли корень
            }
            x0 = x1;
        }
        throw new RuntimeException("Root not found within the maximum number of iterations");
    }

    @Override
    public double apply(double x) {
        double result = g.apply(x);
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new IllegalArgumentException("Function g(x) returned an invalid result at x = " + x);
        }
        return result;
    }
}