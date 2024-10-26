package mathapp.io;

import mathapp.functions.TabulatedFunction;
import mathapp.functions.factory.ArrayTabulatedFunctionFactory;
import mathapp.functions.factory.LinkedListTabulatedFunctionFactory;
import mathapp.operations.TabulatedDifferentialOperator;

import java.io.*;

import static mathapp.io.FunctionsIO.readTabulatedFunction;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try(BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("input/binary function.bin"))){
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(inputStream, new ArrayTabulatedFunctionFactory());
            System.out.println(function.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enter the size and values of the function: ");
        try{
            TabulatedFunction consoleFunction = FunctionsIO.readTabulatedFunction(new BufferedReader(new InputStreamReader(System.in)), new LinkedListTabulatedFunctionFactory());

            System.out.println(new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory()).derive(consoleFunction).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
