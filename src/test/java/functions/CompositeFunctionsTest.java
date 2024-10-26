package functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mathapp.functions.CompositeFunctions;
import mathapp.functions.ConstantFunction;
import mathapp.functions.SqrFunction;
import org.junit.jupiter.api.Test;

public class CompositeFunctionsTest {
    @Test
    public void testCompositeFunctions(){

        ConstantFunction constantFunction = new ConstantFunction(2);
        SqrFunction sqrFunction = new SqrFunction();

        CompositeFunctions compositeFunction1 = new CompositeFunctions(constantFunction, sqrFunction);
        CompositeFunctions compositeFunction2 = new CompositeFunctions(sqrFunction, sqrFunction);
        CompositeFunctions compositeFunction3 = new CompositeFunctions(compositeFunction1, compositeFunction2);

        assertEquals(4, compositeFunction1.apply(100));
        assertEquals(16, compositeFunction2.apply(2));
        assertEquals(256, compositeFunction3.apply(2));
    }
}
