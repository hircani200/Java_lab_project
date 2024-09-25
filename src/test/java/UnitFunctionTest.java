import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.UnitFunction;
import org.junit.jupiter.api.Test;

public class UnitFunctionTest {
    @Test
    public void testUnitFunction() {
        UnitFunction unitFunction = new UnitFunction();

        assertEquals(unitFunction.getConstant(), unitFunction.apply(10), 1e-9);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(-3), 1e-9);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(0), 1e-9);
    }

}