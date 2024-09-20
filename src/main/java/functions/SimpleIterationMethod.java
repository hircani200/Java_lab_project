package functions;

public class SimpleIterationMethod {
    private final MathFunction phi;  // функция ϕ(x)
    private final double tolerance;  // точность
    private final int maxIterations; // максимальное количество итераций

    public SimpleIterationMethod(MathFunction phi, double tolerance, int maxIterations) {
        this.phi = phi;
        this.tolerance = tolerance;
        this.maxIterations = maxIterations;
    }

    public double solve(double initialGuess) {
        double x = initialGuess;
        int iterations = 0;

        while (iterations < maxIterations) {
            double nextX = phi.apply(x);

            // Проверка на достижение требуемой точности
            if (Math.abs(nextX - x) < tolerance) {
                return nextX;
            }

            x = nextX;
            iterations++;
        }

        throw new RuntimeException("Failed to find solution in " + maxIterations + " iterations");
    }
}