import static io.FunctionsIO.readTabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import io.FunctionsIO;
import org.junit.jupiter.api.Test;

import java.io.*;

class FunctionsIOTest {

    @Test
    void testWriteIntoFileAndReadFromFileTabulatedFunction() throws IOException {

        TabulatedFunction function = new ArrayTabulatedFunction(new double[] {-3, -1.5, 0, 1.5, 3}, new double[] {4, 0.25, 1, 6.25, 16});

        BufferedWriter outputStream = new BufferedWriter(new FileWriter("temp/temp.bin"));
        FunctionsIO.writeTabulatedFunction(outputStream, function);

        BufferedReader inputStream = new BufferedReader(new FileReader("temp/temp.bin"));
        TabulatedFunction fileFunction = readTabulatedFunction(inputStream, new ArrayTabulatedFunctionFactory());

        for (int i = 0; i < fileFunction.getCount(); i++) {
            assertEquals(function.getX(i), fileFunction.getX(i));
            assertEquals(function.getY(i), fileFunction.getY(i));
        }

    }
}
