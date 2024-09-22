package functions;

import java.util.function.BiFunction;

public class RungeKuttaMethod implements MathFunction{

    // Поля, отвечающие за задачу Коши
    private final BiFunction<Double, Double, Double> differentialEquation; // Обыкновенное Дифференциальное уравнение(ОДУ) первого порядка
    private final double x0, y0; // Начальные условия(НУ)

    // Конструктор иницализации ОДУ + НУ
    public RungeKuttaMethod(BiFunction<Double, Double, Double> differentialEquation, double x0, double y0) {
        this.differentialEquation = differentialEquation;
        this.x0 = x0;
        this.y0 = y0;
    }

    @Override
    public double apply(double stepSize) {
        // Вместо x в apply берём шаг по оси OX относительно x0
        // Тогда x1 = x0 + stepSize
        double k1, k2, k3, k4;

        k1 = this.differentialEquation.apply(this.x0, this.y0);
        k2 = this.differentialEquation.apply(this.x0+stepSize/2, this.y0+stepSize*k1/2);
        k3 = this.differentialEquation.apply(this.x0+stepSize/2, this.y0+stepSize*k2/2);
        k4 = this.differentialEquation.apply(this.x0+stepSize, this.y0+stepSize*k3);

        // На выходе получаем y1
        return this.y0 + (stepSize/6) * (k1+2*k2+2*k3+k4);
    }
}
