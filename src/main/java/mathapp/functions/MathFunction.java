package mathapp.functions;

// Интерфейс мат. функции
public interface MathFunction {
    double apply(double x);

    default MathFunction andThen(MathFunction afterFunction) {
        return new CompositeFunctions(this, afterFunction);
    }
}
