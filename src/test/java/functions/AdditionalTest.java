package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mathapp.functions.*;
import org.junit.jupiter.api.Test;


public class AdditionalTest {

    @Test
    public void test(){

        SqrFunction sqr = new SqrFunction();
        MathFunction function1 = (x) -> x + 2;
        MathFunction function2 = (x) -> sqr.apply(x+1);
        CompositeFunctions compositeFunction = new CompositeFunctions(function1, function2);

        LinkedListTabulatedFunction tabulatedFunction1 = new LinkedListTabulatedFunction(function1, 0, 3, 4);
        ArrayTabulatedFunction tabulatedFunction2 = new ArrayTabulatedFunction(function2, 0, 3, 4);
        LinkedListTabulatedFunction tabulatedFunction3 = new LinkedListTabulatedFunction(compositeFunction, 0, 3, 4);

        ArrayTabulatedFunction tabulatedFunction4 = new ArrayTabulatedFunction(tabulatedFunction3, 0, 3, 4);
        LinkedListTabulatedFunction tabulatedFunction5 = new LinkedListTabulatedFunction(tabulatedFunction2, 0, 3, 4);

        double[] yArray1 = {2.0, 3.0, 4.0, 5.0};
        double[] yArray2 = {1.0, 4.0, 9.0, 16.0};
        double[] yArray3 = {9.0, 16.0, 25.0, 36.0};
        double[] yArray4 = {9.0, 16.0, 25.0, 36.0};
        double[] yArray5 = {1.0, 4.0, 9.0, 16.0};


        for (int i = 0; i < tabulatedFunction1.getCount(); i++) {
            assertEquals(yArray1[i], tabulatedFunction1.getY(i), 1e-9);
            assertEquals(yArray2[i], tabulatedFunction2.getY(i), 1e-9);
            assertEquals(yArray3[i], tabulatedFunction3.getY(i), 1e-9);
            assertEquals(yArray4[i], tabulatedFunction4.getY(i), 1e-9);
            assertEquals(yArray5[i], tabulatedFunction5.getY(i), 1e-9);
        }
    }

    @Test
    public void test2(){

        MathFunction function1 = (x) -> 2 * x + 3;
        MathFunction function2 = (x) -> Math.log(x + 1);

        CompositeFunctions compositeFunction = new CompositeFunctions(function2, function1);

        LinkedListTabulatedFunction tabulatedFunction1 = new LinkedListTabulatedFunction(function1, 0, 2, 3);
        ArrayTabulatedFunction tabulatedFunction2 = new ArrayTabulatedFunction(function2, 0, 2, 3);
        LinkedListTabulatedFunction tabulatedFunction3 = new LinkedListTabulatedFunction(compositeFunction, 0, 2, 3);

        double[] yArray1 = {3.0, 5.0, 7.0};
        double[] yArray2 = {0.0, Math.log(2), Math.log(3)};
        double[] yArray3 = {3.0, 2 * Math.log(2) + 3, 2 * Math.log(3) + 3};

        for (int i = 0; i < tabulatedFunction1.getCount(); i++) {
            assertEquals(yArray1[i], tabulatedFunction1.getY(i), 1e-9);
            assertEquals(yArray2[i], tabulatedFunction2.getY(i), 1e-9);
            assertEquals(yArray3[i], tabulatedFunction3.getY(i), 1e-9);
        }

    }
}
