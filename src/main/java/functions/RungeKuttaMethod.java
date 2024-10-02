package functions;

import java.util.function.BiFunction;

public class RungeKuttaMethod implements MathFunction{

    // Поля, отвечающие за задачу Коши
    private final BiFunction<Double, Double, Double> differentialEquation; // Обыкновенное Дифференциальное уравнение(ОДУ) первого порядка
    private final double x0, y0; // Начальные условия(НУ)


    // Конструктор иницализации ОДУ + НУ
    public RungeKuttaMethod(BiFunction<Double, Double, Double> differentialEquation, double x0, double y0) {
        if(differentialEquation.apply(x0, y0) == Double.POSITIVE_INFINITY || differentialEquation.apply(x0, y0) == Double.NaN || differentialEquation.apply(x0, y0) == Double.NEGATIVE_INFINITY){
            throw new IllegalArgumentException("There is no scope of definition for current numbers");
        }
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

        double y1 = this.y0 + (stepSize/6) * (k1+2*k2+2*k3+k4);

        if(this.differentialEquation.apply(x0+stepSize, y1) == Double.POSITIVE_INFINITY || this.differentialEquation.apply(x0+stepSize, y1) == Double.NaN || this.differentialEquation.apply(x0+stepSize, y1) == Double.NEGATIVE_INFINITY){
            throw new IllegalArgumentException("There is no scope of definition for current numbers");
        }

        // На выходе получаем y1
        return y1;
    }
}
