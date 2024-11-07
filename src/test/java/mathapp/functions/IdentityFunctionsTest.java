package mathapp.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mathapp.functions.IdentityFunctions;
import org.junit.jupiter.api.Test;

public class IdentityFunctionsTest {
    @Test
    public void testIdentityFunctions(){
        IdentityFunctions identityFunction = new IdentityFunctions();

        assertEquals(1, identityFunction.apply(1));
        assertEquals(0.0000001, identityFunction.apply(0.0000001));
        assertEquals(3.1415926535897, identityFunction.apply(3.1415926535897));
    }
}
