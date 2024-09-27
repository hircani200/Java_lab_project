package functions;

import java.util.Arrays;

public class SimpleIterationMethod implements MathFunction {

    private final MathFunction[] equations;  // Система уравнений
    private final double epsilon;            // Точность
    private final int maxIterations;         // Максимальное количество итераций

    public SimpleIterationMethod(MathFunction[] equations, double epsilon, int maxIterations) {
        this.equations = equations;
        this.epsilon = epsilon;
        this.maxIterations = maxIterations;
    }

    @Override
    public double apply(double initialGuess) { // initialGuess начальное приближение для системы уравнений
        double[] currentGuess = new double[equations.length];  // Текущие значения переменных
        double[] nextGuess = new double[equations.length];     // Следующие значения переменных

        // Инициализируем начальные приближения
        Arrays.fill(currentGuess, initialGuess);

        // Итерационный процесс
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            boolean converged = true;

            // Применяем функцию g для каждой переменной
            for (int i = 0; i < equations.length; i++) {
                nextGuess[i] = equations[i].apply(currentGuess[i]);  // Вычисляем следующее значение

                // Проверяем, достигнута ли сходимость для каждой переменной
                if (Math.abs(nextGuess[i] - currentGuess[i]) >= epsilon) {
                    converged = false;
                }
            }

            // Если все переменные удовлетворяют точности, то решение найдено
            if (converged) {
                return nextGuess[0];
            }

            // Обновляем текущее приближение для следующей итерации
            System.arraycopy(nextGuess, 0, currentGuess, 0, currentGuess.length);
        }

        throw new IllegalStateException("Root not found within maximum iterations: " + maxIterations);
    }
}