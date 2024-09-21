package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected int count; // Количество точек в таблице

    protected abstract int floorIndexOfX(double x);

    // Линейная интерполяция в пределах таблицы
    protected abstract double interpolate(double x, int floorIndex);

    // Линейная интерполяция между двумя точками
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX);
    }

    // Экстраполяция слева
    protected abstract double extrapolateLeft(double x);

    // Экстраполяция справа
    protected abstract double extrapolateRight(double x);

    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index);
            } else {
                int floorIndex = floorIndexOfX(x);
                return interpolate(x, floorIndex);
            }
        }
    }
}