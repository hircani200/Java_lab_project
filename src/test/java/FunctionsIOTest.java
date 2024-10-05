import static org.junit.jupiter.api.Assertions.*;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import java.nio.file.*;
import io.FunctionsIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Comparator;
import java.util.stream.Stream;

class FunctionsIOTest {

    private static final Path TEMP_DIR = Paths.get("temp");

    @BeforeEach
    public void setUp() throws IOException {
        Files.createDirectories(TEMP_DIR);
    }

    @AfterEach
    public void cleanUp() throws IOException {
        try (Stream<Path> files = Files.walk(TEMP_DIR)) {
            files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file -> {
                        if (!file.delete()) {
                            System.err.println("Failed to delete file: " + file.getPath());
                        }
                    });
        }
    }



    @Test
    public void testWriteAndReadTabulatedFunction() throws IOException {
        Path tempFile = TEMP_DIR.resolve("test_function.txt");

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            FunctionsIO.writeTabulatedFunction(writer, function);
        }

        TabulatedFunction readFunction;
        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            readFunction = FunctionsIO.readTabulatedFunction(reader, ArrayTabulatedFunction::new);
        }

        assertEquals(function.getCount(), readFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testSerializeAndDeserializeFunction() throws IOException, ClassNotFoundException {
        Path tempFile = TEMP_DIR.resolve("test_function.bin");

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.serialize(bos, function);
        }

        TabulatedFunction deserializedFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            deserializedFunction = FunctionsIO.deserialize(bis);
        }

        assertEquals(function.getCount(), deserializedFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), deserializedFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), deserializedFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testWriteAndReadBinaryFunction() throws IOException {
        Path tempFile = TEMP_DIR.resolve("test_function_binary.bin");

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        TabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(tempFile))) {
            FunctionsIO.writeTabulatedFunction(bos, function);
        }

        TabulatedFunction readFunction;
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(tempFile))) {
            readFunction = FunctionsIO.readTabulatedFunction(bis, ArrayTabulatedFunction::new);
        }

        assertEquals(function.getCount(), readFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9);
        }
    }

    @Test
    public void testJsonSerializeAndDeserializeFunction() throws IOException {
        Path tempFile = TEMP_DIR.resolve("test_function.txt");

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            FunctionsIO.serializeJson(writer, function);
        }

        TabulatedFunction readFunction;
        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            readFunction = FunctionsIO.deserializeJson(reader);
        }

        assertEquals(function.getCount(), readFunction.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(function.getX(i), readFunction.getX(i), 1e-9);
            assertEquals(function.getY(i), readFunction.getY(i), 1e-9);
        }
    }
}
