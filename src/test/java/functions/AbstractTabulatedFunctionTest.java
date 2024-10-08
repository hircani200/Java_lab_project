package functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public class AbstractTabulatedFunctionTest {

    double[] xValues;
    double[] yValues;

    @Test
    void testLengthIsTheSame() {
        xValues = new double[]{1.0, 2.0, 3.0};
        yValues = new double[]{4.0, 5.0, 6.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
        xValues = new double[]{1.0, 2.0};
        assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
        xValues = new double[]{1.4, 2.5};
        assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    void testSorted() {
        xValues = new double[]{1.0, 2.0, 3.0, 5.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
        xValues = new double[]{1.0, 1.0, 2.0, 3.5};
        assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(xValues));
        xValues = new double[]{1.0, 1.5, 3.0, 2.0};
        assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(xValues));
    }

    @Test
    void testToString(){
        xValues = new double[]{1.0, 2.0, 3.0};
        yValues = new double[]{4.0, 5.0, 6.0};
        LinkedListTabulatedFunction function1 = new LinkedListTabulatedFunction(xValues, yValues);
        ArrayTabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0, 4.25}, new double[]{6.0, 4.0, 0.0, 5.55});

        String stringLinkedListFunction = "LinkedListTabulatedFunction size = 3\n[1.0; 4.0]\n[2.0; 5.0]\n[3.0; 6.0]\n";
        String stringArrayFunction = "ArrayTabulatedFunction size = 4\n[1.0; 6.0]\n[2.0; 4.0]\n[3.0; 0.0]\n[4.25; 5.55]\n";

        assertEquals(stringLinkedListFunction, function1.toString());
        assertEquals(stringArrayFunction, function2.toString());
    }
}