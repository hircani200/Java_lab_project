import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import functions.AbstractTabulatedFunction;

public class AbstractTabulatedFunctionTest {

    double[] xValues;
    double[] yValues;

    @Test
    void testLengthIsTheSame() {
        xValues = new double[]{1.0, 2.0, 3.0};
        yValues = new double[]{4.0, 5.0, 6.0};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
        xValues = new double[]{1.0, 2.0};
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
        xValues = new double[]{1.4, 2.5};
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    void testSorted() {
        xValues = new double[]{1.0, 2.0, 3.0, 5.0};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
        xValues = new double[]{1.0, 1.0, 2.0, 3.5};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
        xValues = new double[]{1.0, 1.5, 3.0, 2.0};
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(xValues));
    }
}