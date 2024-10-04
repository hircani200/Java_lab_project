package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import java.io.Serializable;


public abstract class AbstractTabulatedFunction implements TabulatedFunction, Serializable {

    protected int count;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName() + " size = " + this.count + "\n");
        for (Point point : this) stringBuilder.append("[").append(point.x).append("; ").append(point.y).append("]\n");
        return stringBuilder.toString();
    }

    protected abstract int floorIndexOfX(double x);

    // Линейная интерполяция в пределах таблицы
    protected abstract double interpolate(double x, int floorIndex);

    // Линейная интерполяция между двумя точками
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX);
    }

    protected abstract double extrapolateLeft(double x);

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
    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) throws DifferentLengthOfArraysException {
        if (xValues.length != yValues.length) throw new DifferentLengthOfArraysException();

    }
    public static void checkSorted(double[] xValues)  throws ArrayIsNotSortedException {
        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i - 1] >= xValues[i]) throw new ArrayIsNotSortedException();
            }
    }
}