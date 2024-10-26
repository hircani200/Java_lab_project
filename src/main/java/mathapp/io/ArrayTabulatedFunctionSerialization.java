package mathapp.io;

import mathapp.functions.ArrayTabulatedFunction;
import mathapp.functions.TabulatedFunction;
import mathapp.functions.factory.ArrayTabulatedFunctionFactory;
import mathapp.operations.TabulatedDifferentialOperator;
import mathapp.functions.factory.TabulatedFunctionFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArrayTabulatedFunctionSerialization {

    public static void main(String[] args) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("output/serialized_array_functions.bin"))) {
            double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
            double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};

            TabulatedFunction function1 = new ArrayTabulatedFunction(xValues, yValues);
            TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(factory);

            TabulatedFunction function2 = operator.derive(function1);
            TabulatedFunction function3 = operator.derive(function2);

            FunctionsIO.serialize(outputStream, function1);
            FunctionsIO.serialize(outputStream, function2);
            FunctionsIO.serialize(outputStream, function3);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("output/serialized_array_functions.bin"))) {
            TabulatedFunction deserializedFunction1 = FunctionsIO.deserialize(inputStream);
            TabulatedFunction deserializedFunction2 = FunctionsIO.deserialize(inputStream);
            TabulatedFunction deserializedFunction3 = FunctionsIO.deserialize(inputStream);

            System.out.println(deserializedFunction1.toString());
            System.out.println(deserializedFunction2.toString());
            System.out.println(deserializedFunction3.toString());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}