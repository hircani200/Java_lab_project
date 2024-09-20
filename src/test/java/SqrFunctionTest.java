import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.SqrFunction;
import org.junit.jupiter.api.Test;

public class SqrFunctionTest {

    @Test
    public void testApply() {
        SqrFunction sqrFunction = new SqrFunction();

        // Проверяем квадрат положительного числа
        assertEquals(9, sqrFunction.apply(3), 0.001);

        // Проверяем квадрат отрицательного числа
        assertEquals(9, sqrFunction.apply(-3), 0.001);

        // Проверяем квадрат нуля
        assertEquals(0, sqrFunction.apply(0), 0.001);

        // Проверяем квадрат дробного числа
        assertEquals(2.25, sqrFunction.apply(1.5), 0.001);
    }
}