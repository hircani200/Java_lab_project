import static org.junit.jupiter.api.Assertions.assertEquals;

import functions.UnitFunction;
import org.junit.jupiter.api.Test;

public class UnitFunctionTest {
    @Test
    public void testUnitFunction() {
        UnitFunction unitFunction = new UnitFunction();
        assertEquals(unitFunction.getConstant(), unitFunction.apply(10), 0.001);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(-3), 0.001);
        assertEquals(unitFunction.getConstant(), unitFunction.apply(0), 0.001);
    }

}