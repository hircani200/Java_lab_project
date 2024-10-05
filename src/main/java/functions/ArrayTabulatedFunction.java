package functions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InterpolationException;

import java.io.Serial;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.Iterator;
import java.lang.Iterable;
import java.io.Serializable;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Removable, Insertable, Iterable<Point>, Serializable {

    @Serial
    private static final long serialVersionUID = 0x99ab18f6b0c44682L;

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    protected double[] xValues;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    protected double[] yValues;

    @JsonCreator
    public ArrayTabulatedFunction(@JsonProperty(value = "xvalues") double[] xValues, @JsonProperty(value = "yvalues") double[] yValues) {
        AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues);
        AbstractTabulatedFunction.checkSorted(xValues);

        if (xValues.length < 2) {
            throw new IllegalArgumentException("The size must be at least 2");
        }


        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("The number of points must be at least 2");
        }

        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        if (xFrom == xTo) {
            double yValue = source.apply(xFrom);
            for (int i = 0; i < count; ++i) {
                xValues[i] = xFrom;
                yValues[i] = yValue;
            }
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; ++i) {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xValues[i]);
            }
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        yValues[index] = value;
    }

    public double[] getXValues() {
        return this.xValues;
    }

    public double[] getYValues() {
        return this.yValues;
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < xValues[0]) {
            throw new IllegalArgumentException("Lesser than left left bound");
        }
        for (int i = 1; i < count; i++) {
            if (x < xValues[i]) {
                return i - 1;
            }
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    // Метод экстраполяции справа
    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("x is out of interpolation bounds");
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public void insert(double x, double y) {

        for (int i = 0; i < count; i++) {
            if(this.xValues[i] == x){
                this.yValues[i] = y;
                return;
            }
            if((this.xValues[i] < x && x < this.xValues[i + 1]) || (x<this.xValues[0]) || (x>this.xValues[count-1])){

                double[] newXValues = new double[count+1];
                double[] newYValues = new double[count+1];

                if(x < this.xValues[0]) {
                    newXValues[0] = x;
                    newYValues[0] = y;

                    System.arraycopy(this.xValues, 0, newXValues, 1, count);
                    System.arraycopy(this.yValues, 0, newYValues, 1, count);
                } else if(x>this.xValues[count-1]){

                    System.arraycopy(this.xValues, 0, newXValues, 0, count);
                    System.arraycopy(this.yValues, 0, newYValues, 0, count);

                    newXValues[count] = x;
                    newYValues[count] = y;
                } else {
                    System.arraycopy(this.xValues, 0, newXValues, 0, i + 1);
                    System.arraycopy(this.yValues, 0, newYValues, 0, i + 1);

                    newXValues[i + 1] = x;
                    newYValues[i + 1] = y;

                    System.arraycopy(this.xValues, i + 1, newXValues, i + 2, count - i - 1);
                    System.arraycopy(this.yValues, i + 1, newYValues, i + 2, count - i - 1);
                }

                this.xValues = newXValues;
                this.yValues = newYValues;
                this.count++;

                return;
            }
        }
    }

    @Override
    public void remove(int index) {
        if (count <= 2) {
            throw new IllegalStateException("Cannot remove a point. The number of points must be at least 2");
        }
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        double[] newXValues = new double[count - 1];
        double[] newYValues = new double[count - 1];

        System.arraycopy(xValues, 0, newXValues, 0, index);
        System.arraycopy(yValues, 0, newYValues, 0, index);

        System.arraycopy(xValues, index + 1, newXValues, index, count - index - 1);
        System.arraycopy(yValues, index + 1, newYValues, index, count - index - 1);

        this.xValues = newXValues;
        this.yValues = newYValues;
        count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < getCount();
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }

}